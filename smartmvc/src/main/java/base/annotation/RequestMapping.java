package base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Retention该注解的作用:  用来配置请求路径与处理器的对应关系。
 * @author tarena
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	public String value();
}
