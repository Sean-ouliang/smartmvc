package base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Retention��ע�������:  ������������·���봦�����Ķ�Ӧ��ϵ��
 * @author tarena
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
	public String value();
}
