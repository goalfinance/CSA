/**
 * 
 */
package cistern.spring.httpinvoker.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * @project: cistern
 * @description: Http Invoker ��ʽ����ע����
 * @author: panqr
 * @create_time: 2011-1-26
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface HttpInvokerService {
	/**
	 * �ӿڶ��Ⱪ¶��url���·��
	 * @return
	 */
	public String[] exportedRelativeUrl() default "";
	
	/**
	 * ��Ӧ�ķ���ӿ�����
	 * @return
	 */
	public Class<?>[] serviceInterfaceClass() default UnknownClass.class;
}
