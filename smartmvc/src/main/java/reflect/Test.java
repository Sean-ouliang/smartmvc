package reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * java注解的生存时间有三种：
 * a.保留到源代码里面，编译之后，就会被抹掉（@Override）
 * b.保留到字节码文件里面，但是，运行的时候不在了.（默认）
 * c.保留到运行时。
 * 
 * @Retention 是一个元源注解，作用是告诉JVM,该注解保留到什么时候。
 *   注：元注解指的是用来解释注解的注解
 * @author tarena
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
	public String value();
}
