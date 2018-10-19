package demo;

import base.annotation.RequestMapping;

/**
 * ������:
 *   ������ҵ���߼���
 * @author tarena
 *
 */
public class HelloController {
	
	@RequestMapping("/hello.do")
	public String hello(){
		System.out.println("HelloController��hello����...");
		/*
		 * ���ص�����ͼ������һ���ַ�����
		 * DispatcherServlet�Ὣ����ͼ��
		 * ӳ���������jsp(��/WEB-INF/��+��ͼ��+��.jsp��)��
		 */
		return "hello";
	}
}
