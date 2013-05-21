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
 * @description: HttpInvoker客户端自动注入注释类
 * @author: panqr
 * @create_time: 2011-1-27
 *
 */
@Target(ElementType.METHOD)
@Retention(RUNTIME)
public @interface HttpInvokerClientWired {
	/**
	 * 指定远端HttpInvoker Service的服务地址
	 * @return
	 */
	public String remoteServiceUrl() default "";

}
