package reflect;

public class A {
	public void f1(){
		System.out.println("A的f1方法...");
	}
	
	public String f2(){
		System.out.println("A的f2方法...");
		return "hello f2";
	}

	public void hello(String name){
		System.out.println("A的hello方法...");
		System.out.println("hello:"+name);
	}
}
