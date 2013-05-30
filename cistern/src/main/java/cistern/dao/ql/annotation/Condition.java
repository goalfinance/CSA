package cistern.dao.ql.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface Condition {
	String selectClause() default "";
	
	String fromClause() default "";
	
	String whereClause() default "";
	
	Class<?> entity() default UnknownEntity.class;
}
