package reflect;

public class C {
	public void f1(){
		System.out.println("c��f1����...");
	}
	
	/**
	 * value���Բ�д��Ĭ�Ͼ���value
	 */
	@Test("hehe")
	public void foo(){
		System.out.println("c��foo����...");
	}
	
	@Test(value="haha")
	public void bala(){
		System.out.println("c��bala����...");
	}
}
