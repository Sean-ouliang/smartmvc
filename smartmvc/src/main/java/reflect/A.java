package reflect;

public class A {
	public void f1(){
		System.out.println("A��f1����...");
	}
	
	public String f2(){
		System.out.println("A��f2����...");
		return "hello f2";
	}

	public void hello(String name){
		System.out.println("A��hello����...");
		System.out.println("hello:"+name);
	}
}
