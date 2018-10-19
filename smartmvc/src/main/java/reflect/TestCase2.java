package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Scanner;

public class TestCase2 {
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Scanner scan = new Scanner(System.in);	
		String className = scan.nextLine();
		Class cls = Class.forName(className);
		Object obj = cls.newInstance();
		Method[] methods = cls.getDeclaredMethods();
		for(Method m:methods){
			System.out.println("mName:"+m.getName());
			boolean names = m.getName().startsWith("test");
			if(names){
				Object returnVal = m.invoke(obj);
				System.out.println("returnVal:"+returnVal);
			}
		}
	}
}
