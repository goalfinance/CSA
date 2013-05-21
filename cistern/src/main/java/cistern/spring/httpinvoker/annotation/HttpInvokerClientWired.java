/**
 * 
 */
package cistern.spring.httpinvoker.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * @project: cistern
 * @description: HttpInvoker�ͻ����Զ�ע��ע����
 * @author: panqr
 * @create_time: 2011-1-27
 *
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface HttpInvokerClientWired {
	/**
	 * ָ��Զ��HttpInvoker Service�ķ����ַ
	 * @return
	 */
	public String remoteServiceUrl() default "";

}
