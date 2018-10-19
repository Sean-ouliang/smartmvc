package base.common;

import java.lang.reflect.Method;

/**
 * 为了方便利用java反射去调用方法而设计的一个类。
 * method.invoke(obj)
 * @author tarena
 *
 */
public class Headler {
	private Method mh;
	private Object obj;
	
	public Headler(Method mh, Object obj) {
		this.mh = mh;
		this.obj = obj;
	}
	
	public Method getMh() {
		return mh;
	}
	public void setMh(Method mh) {
		this.mh = mh;
	}
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
