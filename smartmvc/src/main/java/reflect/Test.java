package reflect;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * javaע�������ʱ�������֣�
 * a.������Դ�������棬����֮�󣬾ͻᱻĨ����@Override��
 * b.�������ֽ����ļ����棬���ǣ����е�ʱ������.��Ĭ�ϣ�
 * c.����������ʱ��
 * 
 * @Retention ��һ��ԪԴע�⣬�����Ǹ���JVM,��ע�Ᵽ����ʲôʱ��
 *   ע��Ԫע��ָ������������ע���ע��
 * @author tarena
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Test {
	public String value();
}
