package base.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import base.annotation.RequestMapping;

/**
 * 映射处理器：
 *   负责提供请求路径与处理器的对应关系。
 * @author tarena
 *
 */
public class HeadlerMapping {
	/*
	 * headlerMap用于存放请求路径与处理器的对应关系
	 * 注：
	 *   key是请求路径
	 *   value是处理器及处理器方法对象的一个封装。
	 */
	private Map<String,Headler> headlerMap = new HashMap<String,Headler>();
	
	/**
	 * 依据请求路径，返回处理器实例及处理器方法对象的
	 * 封装(即Headler对象)。
	 * @param path
	 * @return
	 */
	public Headler getHeadler(String path){
		return headlerMap.get(path);
	}

	/**
	 * 用于建立请求路径与处理器的对应关系：
	 * 该方法遍历list集合(集合中存放的是处理器实例，
	 * 比如HelloController),对于每个处理器实例，利用
	 * java反射机制读取加在前面的@RequestMapping注解中
	 * 的路径信息，然后以路径信息作为key，以处理器实例及
	 * 处理器方法对象作为value，将这个对应关系添加到了
	 * headlerMap里面。
	 * 
	 * @param beans
	 */
	public void process(List beans) {
		for(Object bean : beans){
			//获得Class对象
			Class clazz = bean.getClass();
			//获得所有方法
			Method[] methods = clazz.getDeclaredMethods();
			for(Method mh : methods){
				//获得方法前面的@RequestMapping注解
				RequestMapping rm = mh.getDeclaredAnnotation(RequestMapping.class);
				//获得@RequestMapping的属性值（即请求路径）
				String path = rm.value();
				/*
				 * 以请求路径作为key，以处理器实例及
				 * 处理方法对象作为value，建立请求路径
				 * 与处理器的对应关系
				 */
				headlerMap.put(path, new Headler(mh,bean));
			}
		}
		System.out.println("headlerMap:"+headlerMap);
	}
	
}
