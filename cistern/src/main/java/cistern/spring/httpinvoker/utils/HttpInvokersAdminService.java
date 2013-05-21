/**
 * 
 */
package cistern.spring.httpinvoker.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.OrderComparator;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import cistern.spring.httpinvoker.annotation.HttpInvokerService;

/**
 * @project: cistern
 * @description: HttpInvokers管理接口实现类
 * @author: panqr
 * @create_time: 2011-2-9
 * 
 */
@HttpInvokerService(exportedRelativeUrl="<hisAdmin>",
		serviceInterfaceClass=IHttpInvokersAdminService.class)
public class HttpInvokersAdminService implements IHttpInvokersAdminService,
		ApplicationContextAware {

	/**
	 * Spring容器应用上下文
	 */
	private ApplicationContext applicationContext;

	/**
	 * Http Invoker对外暴露名后缀
	 */
	private String suffix = HttpInvokerCommonUtil.DEFAULT_EXPOSE_NAME_SUFFIX;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	public void setApplicationContext(ApplicationContext ac)
			throws BeansException {
		applicationContext = ac;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cistern.spring.httpinvoker.utils.IHttpInvokersAdminService#getExposedHISInfo
	 * ()
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<HttpInvokerExposedInfo> getExposedHISInfo() {
		Map<String, AbstractUrlHandlerMapping> matchingBeans = BeanFactoryUtils
				.beansOfTypeIncludingAncestors(applicationContext,
						AbstractUrlHandlerMapping.class, true, false);

		List<AbstractUrlHandlerMapping> handlerMappings = new ArrayList<AbstractUrlHandlerMapping>();
		if (!(matchingBeans.isEmpty())) {
			handlerMappings = new ArrayList<AbstractUrlHandlerMapping>(
					matchingBeans.values());
			Collections.sort(handlerMappings, new OrderComparator());
		}

		List<HttpInvokerExposedInfo> httpInvokerExposedInfos = new ArrayList<HttpInvokerExposedInfo>();
		for (AbstractUrlHandlerMapping hm : handlerMappings) {
			Map urlHandlerMapping = hm.getHandlerMap();
			if (urlHandlerMapping != null) {
				for (Object key : urlHandlerMapping.keySet()) {
					if (((String) key).endsWith(suffix) == true) {
						Object handler = urlHandlerMapping.get(key);
						if (handler instanceof HttpInvokerServiceExporter) {
							HttpInvokerExposedInfo httpInvokerExposedInfo = new HttpInvokerExposedInfo();
							httpInvokerExposedInfo.setRelativeUrl((String) key);
							String serviceName = ((HttpInvokerServiceExporter) handler)
									.getService().getClass().getName();
							String serviceInterfaceName = ((HttpInvokerServiceExporter) handler)
									.getServiceInterface().getName();
							httpInvokerExposedInfo
									.setServiceInterfaceName(serviceInterfaceName);
							httpInvokerExposedInfo.setServiceName(serviceName);
							httpInvokerExposedInfos.add(httpInvokerExposedInfo);
						}
					}
				}
			}
		}
		return httpInvokerExposedInfos;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getSuffix() {
		return suffix;
	}

}
