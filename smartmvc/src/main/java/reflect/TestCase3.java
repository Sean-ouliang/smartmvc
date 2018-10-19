package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestCase3 {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		 * ͨ������̨��ȡ������Ȼ������java���� ������ʵ���������ҵ����䷽��
		 */

		// ͨ������̨��ȡ����
		Scanner scanner = new Scanner(System.in);
		String className = scanner.nextLine();
		System.out.println("className:" + className);

		// ����java���䣬�����ļ���
		Class clazz = Class.forName(className);

		// ����java���䣬������ʵ����
		Object obj = clazz.newInstance();
		System.out.println("obj:" + obj);

		// ����java���䣬��øö�������з���
		Method[] mathods = clazz.getDeclaredMethods();
		for (Method mh : mathods) {
			// ����java���䣬��ü��ڷ���ǰ��ע�⣨@Test��
			Test test = mh.getDeclaredAnnotation(Test.class);
			System.out.println("@Test:" + test);
			//�������ǰ����@Testע�⣬��ִ�и÷���
			if(test != null){
				//��ȡע�������
				String v1 = test.value();
				System.out.println("v1:"+v1);
				
				//ִ�д���@Testע��ķ���
				mh.invoke(obj);
			}
		}
	}

}
