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
 * @description: Http Invoker 方式调用注释类
 * @author: panqr
 * @create_time: 2011-1-26
 *
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface HttpInvokerService {
	/**
	 * 接口对外暴露的url相对路径
	 * @return
	 */
	public String[] exportedRelativeUrl() default "";
	
	/**
	 * 对应的服务接口类型
	 * @return
	 */
	public Class<?>[] serviceInterfaceClass() default UnknownClass.class;
}
