package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestCase3 {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		 * 通过控制台读取类名，然后利用java反射 将该类实例化，并且调用其方法
		 */

		// 通过控制台读取类名
		Scanner scanner = new Scanner(System.in);
		String className = scanner.nextLine();
		System.out.println("className:" + className);

		// 利用java反射，完成类的加载
		Class clazz = Class.forName(className);

		// 利用java反射，完成类的实例化
		Object obj = clazz.newInstance();
		System.out.println("obj:" + obj);

		// 利用java反射，获得该对象的所有方法
		Method[] mathods = clazz.getDeclaredMethods();
		for (Method mh : mathods) {
			// 利用java反射，获得加在方法前的注解（@Test）
			Test test = mh.getDeclaredAnnotation(Test.class);
			System.out.println("@Test:" + test);
			//如果方法前带有@Test注解，则执行该方法
			if(test != null){
				//读取注解的属性
				String v1 = test.value();
				System.out.println("v1:"+v1);
				
				//执行带有@Test注解的方法
				mh.invoke(obj);
			}
		}
	}

}
