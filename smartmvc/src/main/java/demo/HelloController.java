package demo;

import base.annotation.RequestMapping;

/**
 * 处理器:
 *   负责处理业务逻辑。
 * @author tarena
 *
 */
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello(){
		System.out.println("HelloController的hello方法...");
		/*
		 * 返回的是视图名，是一个字符串。
		 * DispatcherServlet会将该视图名
		 * 映射成真正的jsp(“/WEB-INF/”+视图名+“.jsp”)。
		 */
		return "hello";
	}
}
