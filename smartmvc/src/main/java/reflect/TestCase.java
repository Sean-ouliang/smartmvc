package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestCase {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		/*
		 * 通过控制台读取类名，然后利用java反射
		 * 将该类实例化，并且调用其方法
		 */
		
		//通过控制台读取类名
		Scanner scanner = new Scanner(System.in);
		String className = scanner.nextLine();
		System.out.println("className:"+className);

		//利用java反射，完成类的加载
		Class clazz = Class.forName(className);
		
		//利用java反射，完成类的实例化
		Object obj = clazz.newInstance();
		System.out.println("obj:"+obj);
		
		//利用java反射，获得该对象的所有方法
		Method[] mathods = clazz.getDeclaredMethods();
		for(Method mh : mathods){
			//获得方法名
			String mName = mh.getName();
			System.out.println("mName:"+mName);
			//调用方法
			Object returnVal =  null;
			if("hello".equals(mName)){
				//该方法带有参数
				Object[] params = new Object[] {"sally"};
				returnVal = mh.invoke(obj, params);
			}else{
				//该方法不带参数
				returnVal = mh.invoke(obj);
			}
			System.out.println("returnVal:"+returnVal);
		}
	}

}
