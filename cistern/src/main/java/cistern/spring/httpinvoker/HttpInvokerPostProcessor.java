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
 * @description: 处理http invoker annotation的Spring container后处理器，期望在Spring
 *               container正常初始化一个bean时执行
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
	 * 此Spring container后处理器的执行优先级，确保被最后执行
	 */
	private int startOrder = Ordered.LOWEST_PRECEDENCE - 2;

	/**
	 * 占位符前缀
	 */
	private String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;

	/**
	 * 占位符后缀
	 */
	private String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;

	/**
	 * Http Invoker处理映射器
	 */
	private SimpleUrlHandlerMapping httpInvokerHandleMapping;
	
	/**
	 * 应用上下文
	 */
	private ApplicationContext ctx;

	/**
	 * 对外暴露名后缀
	 */
	private String suffix = HttpInvokerCommonUtil.DEFAULT_EXPOSE_NAME_SUFFIX;

	/**
	 * 只负责执行Http Invoker客户端注入
	 */
	private Boolean onlyClientWired = true;

	/**
	 * Http Invoker客户端访问服务端的Url地址信息
	 */
	private Map<String, String> clientUrlsInfo = new HashMap<String, String>();

	/**
	 * http Invoker服务端Url地址信息
	 */
	private Map<String, String> exportedServiceUrlsInfo = new HashMap<String, String>();

	/**
	 * 禁止暴露的类型黑名单
	 */
	private List<String> blackList4Exported = new ArrayList<String>();

	/**
	 * 根据配置生成Http Invoker Client实际访问服务端的地址
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
	 * 根据配置生成Http Invoker 对外暴露的地址
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
	 * 获取给定字符串中所有的占位符
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
	 * 获取占位符名称
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
	 * 根据注释织入Http Invoker Client
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
		 * 获取bean的信息
		 */
		try {
			bInfo = java.beans.Introspector.getBeanInfo(beanClazz);
		} catch (IntrospectionException e) {
			throw new FatalBeanException(
					"autowiring Http Invoker Client to bean=[" + beanName
							+ "] meets error.", e);
		}

		/**
		 * 遍历bean的属性描述器，看是否有注入Http Invoker Client的要求
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
			 * 如果该属性有注入Http Invoker Client的要求，但是属性值不为空，说明可能是用过XML方式注入了http
			 * invoker Client，跳过
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
			 * 替换其中的占位符
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
	 * 注册Http Invoker 服务
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
		 * 在黑名单中的类型不能被暴露
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
		 * 获取HttpInvokerService注释信息
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
			 * 判断注释定义的暴露接口是否被bean实现，只要有一个接口没实现，就认为是bean没事实现暴露接口
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
			 * 替换占位符后的真实的url
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
			 * 生成Http Invoker Exporter
			 */
			HttpInvokerServiceExporter hisExporter = new HttpInvokerServiceExporter();
			hisExporter.setServiceInterface(serviceInterfaceClasses[i]);
			/**
			 * 获取代理对象
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
		int beginPos = string.indexOf('<');
		while (beginPos != -1) {
			int endPos = string.indexOf('>', beginPos);
			if (endPos != -1) {
				char[] target = new char[endPos - beginPos + 1];
				string.getChars(beginPos, endPos + 1, target, 0);
				placeHolders.add(String.valueOf(target));
			}
			beginPos = string.indexOf('<', endPos);
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
