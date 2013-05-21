/**
 * 
 */
package cistern.spring.httpinvoker.utils;

import java.util.List;

/**
 * @project: cistern
 * @description: HttpInvoker管理类
 * @author: panqr
 * @create_time: 2011-2-9
 *
 */
public interface IHttpInvokersAdminService {
	
	/**
	 * 获取对外暴露的HIS信息
	 * @return
	 */
	public List<HttpInvokerExposedInfo> getExposedHISInfo();

}
