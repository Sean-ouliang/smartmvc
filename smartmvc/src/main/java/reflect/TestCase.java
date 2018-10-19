package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestCase {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		 * ͨ������̨��ȡ������Ȼ������java����
		 * ������ʵ���������ҵ����䷽��
		 */
		
		//ͨ������̨��ȡ����
		Scanner scanner = new Scanner(System.in);
		String className = scanner.nextLine();
		System.out.println("className:"+className);

		//����java���䣬�����ļ���
		Class clazz = Class.forName(className);
		
		//����java���䣬������ʵ����
		Object obj = clazz.newInstance();
		System.out.println("obj:"+obj);
		
		//����java���䣬��øö�������з���
		Method[] mathods = clazz.getDeclaredMethods();
		for(Method mh : mathods){
			//��÷�����
			String mName = mh.getName();
			System.out.println("mName:"+mName);
			//���÷���
			Object returnVal =  null;
			if("hello".equals(mName)){
				//�÷������в���
				Object[] params = new Object[] {"sally"};
				returnVal = mh.invoke(obj, params);
			}else{
				//�÷�����������
				returnVal = mh.invoke(obj);
			}
			System.out.println("returnVal:"+returnVal);
		}
	}

}
