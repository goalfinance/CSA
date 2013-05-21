/**
 * 
 */
package cistern.spring.httpinvoker.utils;

import java.io.Serializable;

/**
 * @project: cistern
 * @description: HttpInvoker对外暴露信息Bean
 * @author: panqr
 * @create_time: 2011-2-9
 *
 */
public class HttpInvokerExposedInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String relativeUrl;
	
	private String serviceInterfaceName;
	
	private String serviceName;

	public HttpInvokerExposedInfo() {
		super();
	}

	public void setRelativeUrl(String relativeUrl) {
		this.relativeUrl = relativeUrl;
	}

	public String getRelativeUrl() {
		return relativeUrl;
	}

	public void setServiceInterfaceName(String serviceInterfaceName) {
		this.serviceInterfaceName = serviceInterfaceName;
	}

	public String getServiceInterfaceName() {
		return serviceInterfaceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceName() {
		return serviceName;
	}

	@Override
	public String toString() {
		return "HttpInvokerExposedInfo [relativeUrl=" + relativeUrl
				+ ", serviceInterfaceName=" + serviceInterfaceName
				+ ", serviceName=" + serviceName + "]";
	}
}
