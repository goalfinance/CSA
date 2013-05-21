/**
 * 
 */
package cistern.spring.httpinvoker;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionValidationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.remoting.httpinvoker.HttpInvokerProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import cistern.spring.httpinvoker.annotation.HttpInvokerClientWired;
import cistern.spring.httpinvoker.annotation.HttpInvokerService;
import cistern.spring.httpinvoker.annotation.UnknownClass;
import cistern.spring.httpinvoker.utils.HttpInvokerCommonUtil;
import cistern.utils.StringUtil;

/**
 * @project: cistern
 * @description: ����http invoker annotation��Spring container��������������Spring
 *               container������ʼ��һ��beanʱִ��
 * @author: panqr
 * @create_time: 2011-1-26
 * 
 */
public class HttpInvokerPostProcessor implements BeanPostProcessor,
		PriorityOrdered, ApplicationListener {

	private Log logger = LogFactory.getLog(this.getClass());

	private static final String DEFAULT_PLACEHOLDER_PREFIX = "<";

	private static final String DEFAULT_PLACEHOLDER_SUFFIX = ">";

	/**
	 * ��Spring container��������ִ�����ȼ���ȷ�������ִ��
	 */
	private int startOrder = Ordered.LOWEST_PRECEDENCE - 2;

	/**
	 * ռλ��ǰ׺
	 */
	private String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;

	/**
	 * ռλ����׺
	 */
	private String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;

	/**
	 * Http Invoker����ӳ����
	 */
	private SimpleUrlHandlerMapping httpInvokerHandleMapping;
	
	/**
	 * Ӧ��������
	 */
	private ApplicationContext ctx;

	/**
	 * ���Ⱪ¶����׺
	 */
	private String suffix = HttpInvokerCommonUtil.DEFAULT_EXPOSE_NAME_SUFFIX;

	/**
	 * ֻ����ִ��Http Invoker�ͻ���ע��
	 */
	private Boolean onlyClientWired = true;

	/**
	 * Http Invoker�ͻ��˷��ʷ���˵�Url��ַ��Ϣ
	 */
	private Map<String, String> clientUrlsInfo = new HashMap<String, String>();

	/**
	 * http Invoker�����Url��ַ��Ϣ
	 */
	private Map<String, String> exportedServiceUrlsInfo = new HashMap<String, String>();

	/**
	 * ��ֹ��¶�����ͺ�����
	 */
	private List<String> blackList4Exported = new ArrayList<String>();

	/**
	 * ������������Http Invoker Clientʵ�ʷ��ʷ���˵ĵ�ַ
	 * 
	 * @param url
	 * @return
	 */
	private String getHicAccessUrl(String url) {
		List<String> placeHolders = getPlaceholders(url);
		for (String placeHolder : placeHolders) {
			String value = clientUrlsInfo.get(getPlaceholerName(placeHolder));
			if (StringUtil.isBlank(value) == false) {
				url = url.replaceAll(placeHolder, value);
			}
		}
		return url;
	}

	/**
	 * ������������Http Invoker ���Ⱪ¶�ĵ�ַ
	 * 
	 * @param url
	 * @return
	 */
	private String getHisAccessUrl(String url) {
		List<String> placeHolders = getPlaceholders(url);
		for (String placeHolder : placeHolders) {
			String value = exportedServiceUrlsInfo
					.get(getPlaceholerName(placeHolder));
			if (StringUtil.isBlank(value) == false) {
				url = url.replaceAll(placeHolder, value);
			}
		}
		return url;
	}

	/**
	 * ��ȡ�����ַ��������е�ռλ��
	 * 
	 * @param string
	 * @return
	 */
	private List<String> getPlaceholders(String string) {
		List<String> placeHolders = new ArrayList<String>();
		int beginPos = string.indexOf(placeholderPrefix);
		while (beginPos != -1) {
			int endPos = string.indexOf(placeholderSuffix, beginPos);
			if (endPos != -1) {
				char[] target = new char[endPos - beginPos + 1];
				string.getChars(beginPos, endPos + 1, target, 0);
				placeHolders.add(String.valueOf(target));
			}
			beginPos = string.indexOf(placeholderPrefix, endPos);
		}

		return placeHolders;
	}

	/**
	 * ��ȡռλ������
	 * 
	 * @param placeHolder
	 * @return
	 */
	private String getPlaceholerName(String placeHolder) {
		return placeHolder
				.trim()
				.substring(placeholderPrefix.length(),
						placeHolder.length() - placeholderSuffix.length())
				.trim();
	}

	/**
	 * ����ע��֯��Http Invoker Client
	 * 
	 * @param bean
	 * @param beanName
	 * @return
	 * @throws FatalBeanException
	 */
	private Object wireHic(Object bean, String beanName) throws BeansException {
		Class<?> beanClazz = bean.getClass();
		BeanInfo bInfo = null;

		/**
		 * ��ȡbean����Ϣ
		 */
		try {
			bInfo = java.beans.Introspector.getBeanInfo(beanClazz);
		} catch (IntrospectionException e) {
			throw new FatalBeanException(
					"autowiring Http Invoker Client to bean=[" + beanName
							+ "] meets error.", e);
		}

		/**
		 * ����bean�����������������Ƿ���ע��Http Invoker Client��Ҫ��
		 */
		for (PropertyDescriptor pd : bInfo.getPropertyDescriptors()) {
			Method m = pd.getWriteMethod();
			if (m == null) {
				continue;
			}

			if (m.isAnnotationPresent(HttpInvokerClientWired.class) == false) {
				continue;
			}
			/**
			 * �����������ע��Http Invoker Client��Ҫ�󣬵�������ֵ��Ϊ�գ�˵���������ù�XML��ʽע����http
			 * invoker Client������
			 */
			Method rm = pd.getReadMethod();
			if (rm != null) {
				try {
					if (rm.invoke(bean) != null) {
						continue;
					}
				} catch (Exception e) {
					throw new FatalBeanException(e.getMessage(), e);
				}
			}

			Class<?> hisClass = pd.getPropertyType();

			HttpInvokerClientWired hicWiredAnno = m
					.getAnnotation(HttpInvokerClientWired.class);

			String remoteServiceUrl = hicWiredAnno.remoteServiceUrl();
			/**
			 * �滻���е�ռλ��
			 */
			remoteServiceUrl = getHicAccessUrl(remoteServiceUrl);
			if (StringUtil.isBlank(remoteServiceUrl)) {
				String errmsg = "Error when wiring a Http Invoker Client to bean[beanName='"
						+ beanName
						+ "', propertyName='"
						+ pd.getName()
						+ "'], cause it's announced remoteServiceUrl is nulll or empty";
				throw new BeanDefinitionValidationException(errmsg);
			}

			try {
				HttpInvokerProxyFactoryBean hicFactoryBean = new HttpInvokerProxyFactoryBean();
				hicFactoryBean.setServiceUrl(remoteServiceUrl);
				hicFactoryBean.setServiceInterface(hisClass);
				hicFactoryBean.afterPropertiesSet();

				m.invoke(bean, hicFactoryBean.getObject());
			} catch (Exception e) {
				throw new FatalBeanException(e.getMessage(), e);
			}
		}
		return bean;
	}

	/**
	 * ע��Http Invoker ����
	 * 
	 * @param bean
	 * @param beanName
	 * @throws BeansException
	 */
	@SuppressWarnings("unchecked")
	private Object registerHttpInvokerService(Object bean, String beanName)
			throws BeansException {
		if (logger.isDebugEnabled() == true) {
			logger.info("Exposing the bean=[" + beanName
					+ "] to be a Http Invoker");
		}

		Class<?> beanClazz = bean.getClass();
		if (beanClazz.isAnnotationPresent(HttpInvokerService.class) == false) {
			logger.info("Bean=[" + beanName
					+ "] is not announced as a HttpInvokerService");
			return bean;
		}

		/**
		 * �ں������е����Ͳ��ܱ���¶
		 */
		boolean isInBlackList = false;
		for (String classInBlackList : this.getBlackList4Exported()) {
			if (classInBlackList.equals(bean.getClass().getName())) {
				isInBlackList = true;
			}
		}
		if (isInBlackList == true) {
			logger.info("Bean=[" + beanName
					+ "] is in HIS exposing blacklist, so I can't expose it");
			return bean;

		}

		/**
		 * ��ȡHttpInvokerServiceע����Ϣ
		 */
		HttpInvokerService beanHisAnno = beanClazz
				.getAnnotation(HttpInvokerService.class);
		Class<?>[] serviceInterfaceClasses = beanHisAnno
				.serviceInterfaceClass();
		String[] exportRelativeUrls = beanHisAnno.exportedRelativeUrl();
		if (serviceInterfaceClasses[0].equals(UnknownClass.class)
				|| serviceInterfaceClasses.length == 0) {
			if (beanClazz.getInterfaces().length == 1) {
				serviceInterfaceClasses = new Class<?>[] { beanClazz
						.getInterfaces()[0] };
			} else {
				String errmsg = "Error when exposing bean["
						+ beanName
						+ "] to be a Http Invoker, cause it has no interface or more than one, please assign one to expose it";
				logger.error(errmsg);
				throw new BeanDefinitionValidationException(errmsg);
			}
		} else {

			boolean isReallyImplement = true;

			/**
			 * �ж�ע�Ͷ���ı�¶�ӿ��Ƿ�beanʵ�֣�ֻҪ��һ���ӿ�ûʵ�֣�����Ϊ��beanû��ʵ�ֱ�¶�ӿ�
			 */
			Class<?>[] beanAllImpledInterfaces = beanClazz.getInterfaces();
			for (int i = 0; i < serviceInterfaceClasses.length; i++) {
				boolean isMatched = false;
				for (int j = 0; j < beanAllImpledInterfaces.length; j++) {
					if (serviceInterfaceClasses[i]
							.equals(beanAllImpledInterfaces[j]) == true) {
						isMatched = true;
						break;
					}
				}
				if (isMatched == false) {
					isReallyImplement = false;
					break;
				}
			}

			if (isReallyImplement == false) {
				String errmsg = "Error when exposing bean["
						+ beanName
						+ "] to be a Http Invoker, cause it doesnt implement the interface it wants to expose";
				throw new BeanDefinitionValidationException(errmsg);
			}
		}

		if (serviceInterfaceClasses.length != exportRelativeUrls.length) {
			String errmsg = "Error when exposing bean["
					+ beanName
					+ "] to be a Http Invoker, cause it's exportRelativeUrls and serviceInterfaces are not paired";
			throw new BeanDefinitionValidationException(errmsg);
		}

		for (int i = 0; i < serviceInterfaceClasses.length; i++) {
			/**
			 * �滻ռλ�������ʵ��url
			 */
			String exportRelativeUrl = this
					.getHisAccessUrl(exportRelativeUrls[i]);
			if (exportRelativeUrl.endsWith(suffix) == false) {
				String errmsg = "Error when exposing bean["
						+ beanName
						+ "] to be a Http Invoker, cause it's exposing name is not end with ["
						+ suffix + "]";
				throw new BeanDefinitionValidationException(errmsg);
			}

			/**
			 * ����Http Invoker Exporter
			 */
			HttpInvokerServiceExporter hisExporter = new HttpInvokerServiceExporter();
			hisExporter.setServiceInterface(serviceInterfaceClasses[i]);
			/**
			 * ��ȡ�������
			 */
			Object proxiedBean = this.ctx.getBean(beanName);
			hisExporter.setService(proxiedBean);
			hisExporter.prepare();

			Map<String, Object> urlMap = (Map<String, Object>) httpInvokerHandleMapping
					.getUrlMap();
			urlMap.put(exportRelativeUrl, hisExporter);

			httpInvokerHandleMapping.initApplicationContext();
		}

		return bean;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.core.Ordered#getOrder()
	 */
	public int getOrder() {
		return startOrder;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationListener#onApplicationEvent(org
	 * .springframework.context.ApplicationEvent)
	 */
	public void onApplicationEvent(ApplicationEvent paramApplicationEvent) {
		if (paramApplicationEvent instanceof ContextRefreshedEvent) {
			this.ctx = ((ContextRefreshedEvent) paramApplicationEvent).getApplicationContext();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessBeforeInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessBeforeInitialization(Object bean, String beanName)
			throws BeansException {
		if (onlyClientWired == true) {
			logger.info("Only Http Invoker Client initialization");
			return bean;
		}
		if (httpInvokerHandleMapping == null) {
			String errmsg = "Error when exposing beans, cause there is no  defined httpInvokerHandleMapping";
			throw new FatalBeanException(errmsg);
		}

		return registerHttpInvokerService(bean, beanName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.beans.factory.config.BeanPostProcessor#
	 * postProcessAfterInitialization(java.lang.Object, java.lang.String)
	 */
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (logger.isDebugEnabled() == true) {
			logger.info("preparing to autowired Http Invoker Client to the bean=["
					+ beanName + "], if it has the demand");
		}

		return wireHic(bean, beanName);
	}

	public SimpleUrlHandlerMapping getHttpInvokerHandleMapping() {
		return httpInvokerHandleMapping;
	}

	public void setHttpInvokerHandleMapping(
			SimpleUrlHandlerMapping httpInvokerHandleMapping) {
		this.httpInvokerHandleMapping = httpInvokerHandleMapping;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Boolean getOnlyClientWired() {
		return onlyClientWired;
	}

	public void setOnlyClientWired(Boolean onlyClientWired) {
		this.onlyClientWired = onlyClientWired;
	}

	public Map<String, String> getClientUrlsInfo() {
		return clientUrlsInfo;
	}

	public void setClientUrlsInfo(Map<String, String> clientUrlsInfo) {
		this.clientUrlsInfo = clientUrlsInfo;
	}

	public void setPlaceholderPrefix(String placeholderPrefix) {
		this.placeholderPrefix = placeholderPrefix;
	}

	public String getPlaceholderPrefix() {
		return placeholderPrefix;
	}

	public void setPlaceholderSuffix(String placeholderSuffix) {
		this.placeholderSuffix = placeholderSuffix;
	}

	public String getPlaceholderSuffix() {
		return placeholderSuffix;
	}

	public static final void main(String[] args) throws Exception {
		String string = "<test1>dfsdfdsfsdfer34dsfsw4,.kl<test4>df34,[sdffsb<tes3>dfsrewv";
		List<String> placeHolders = new ArrayList<String>();
		int beginPos = string.indexOf("<");
		while (beginPos != -1) {
			int endPos = string.indexOf(">", beginPos);
			if (endPos != -1) {
				char[] target = new char[endPos - beginPos + 1];
				string.getChars(beginPos, endPos + 1, target, 0);
				placeHolders.add(String.valueOf(target));
			}
			beginPos = string.indexOf("<", endPos);
		}

		for (String a : placeHolders) {
			if ("<test1>".equals(a)) {
				string = string.replaceAll("<test1>", "test1");
			}

			if ("<test4>".equals(a)) {
				string = string.replaceAll("<test4>", "test4");
			}

			if ("<tes3>".equals(a)) {
				string = string.replaceAll("<tes3>", "tes3");
			}
			System.out.println(a);
			System.out.println(a.trim().substring("{".length(),
					a.length() - "}".length()));
			System.out.println(string);
		}
	}

	public void setExportedServiceUrlsInfo(
			Map<String, String> exportedServiceUrlsInfo) {
		this.exportedServiceUrlsInfo = exportedServiceUrlsInfo;
	}

	public Map<String, String> getExportedServiceUrlsInfo() {
		return exportedServiceUrlsInfo;
	}

	public void setBlackList4Exported(List<String> blackList4Exported) {
		this.blackList4Exported = blackList4Exported;
	}

	public List<String> getBlackList4Exported() {
		return blackList4Exported;
	}
}
