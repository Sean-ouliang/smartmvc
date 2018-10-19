package reflect;

public class C {
	public void f1(){
		System.out.println("c的f1方法...");
	}
	
	/**
	 * value可以不写，默认就是value
	 */
	@Test("hehe")
	public void foo(){
		System.out.println("c的foo方法...");
	}
	
	@Test(value="haha")
	public void bala(){
		System.out.println("c的bala方法...");
	}
}
