/**
 * 
 */
package cistern.spring.httpinvoker.utils;

import java.util.List;

/**
 * @project: cistern
 * @description: HttpInvoker������
 * @author: panqr
 * @create_time: 2011-2-9
 *
 */
public interface IHttpInvokersAdminService {
	
	/**
	 * ��ȡ���Ⱪ¶��HIS��Ϣ
	 * @return
	 */
	public List<HttpInvokerExposedInfo> getExposedHISInfo();

}
