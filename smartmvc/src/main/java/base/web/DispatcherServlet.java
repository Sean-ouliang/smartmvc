package base.web;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import base.common.Headler;
import base.common.HeadlerMapping;

/**
 * ������:
 *   ���������������Ȼ������HandlerMapping�����õ���
 * ��Ӧ��Controller(������)������
 *   ���ݴ��������صĴ���������ͼ�������ö�Ӧ��jsp
 * @author tarena
 *
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HeadlerMapping headlerMapping;
	
	@Override
	/**
	 * ��init�������棬ͨ����ȡsmartmvc.xml�ļ�
	 * ��ô���������������demo.HelloController��,
	 * Ȼ�󽫴�����ʵ����������������������ʵ����
	 * ��HandlerMapping(��ӳ�䴦����)������
	 * ע��
	 *   HeaderMapping����������·���봦�����Ķ�Ӧ��ϵ��
	 */
	public void init() throws ServletException {
		/*
		 * ����dom4j����smartmvc.xml
		 */
		SAXReader reader = new SAXReader();
		//��ȡ�����ļ���λ�ü��ļ���
		String fileName = getServletConfig().getInitParameter("configLocation");
		//������������(ClassLoader)�ķ������һ����������
		//��������ָ����smartmvc�������ļ�
		InputStream inStream = getClass().getClassLoader()
				.getResourceAsStream(fileName);
		try {
			//����smartmvc�������ļ�������
			Document doc = reader.read(inStream);
			//��ø�Ԫ��
			Element root = doc.getRootElement();
			//��ø�Ԫ�������������Ԫ��
			List<Element> beanEle = root.elements("bean");
			//����������Ŵ�����ʵ�����ļ���
			List beans = new ArrayList();
			//����������Ԫ��
			for(Element b:beanEle){
				//��ȡclass����ֵ(������������)
				String className = b.attributeValue("class");
				System.out.println("className:"+className);
				//��������ʵ����
				Object bean = Class.forName(className).newInstance();
				//Ϊ�˷��㴦����������ʵ������ӵ�List��������
				beans.add(bean);
			}
			System.out.println("beans:"+beans);
			
			//��������ʵ������HeadlerMapping������
			headlerMapping = new HeadlerMapping();
			headlerMapping.process(beans);
			
		} catch (Exception e) {
			e.printStackTrace();
			//���쳣�׸�����������
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		/*
		 * ���������Դ·����Ȼ���ȡ���е�һ����
		 * �������·��(path)��������������
		 * HeadlerMapping��getHeadler����(������·��
		 * ��Ϊ����)���Headler����
		 * ͨ��Headler����Ϳ��Ե��ô������ķ����ˡ�
		 */
		String uri = request.getRequestURI();
		System.out.println("uri:"+uri);
		//���Ӧ����
		String contextPath = request.getContextPath();
		System.out.println("contextPath:"+contextPath);
		//��ȡ������Դ·����һ����
		String path = uri.substring(contextPath.length());
		System.out.println("path:"+path);
		
		//����HeadlerMapping�ķ������Headler����
		Headler headler = headlerMapping.getHeadler(path);
		System.out.println("headler:"+headler);
		
		//����Headler���������ô������ķ���
		Method mh = headler.getMh();
		Object obj = headler.getObj();
		System.out.println("mh:"+mh);
		System.out.println("obj:"+obj);
		Object returnVal = null;
		try {
			/*
			 * ����java��������������ķ�����
			 * ����������ķ������в�������
			 * �ò�������������������
			 * ע��
			 *   Ŀǰ�汾ֻ֧����������
			 *   (request,response)
			 */
			//��÷����Ĳ�������
			Class[] types = mh.getParameterTypes();
			if(types.length>0){
				Object[] params = new Object[types.length];
				//���в���
				for(int i = 0; i < types.length; i ++){
					if(types[i]==HttpServletRequest.class){
						params[i] = request;
					}
					if(types[i]==HttpServletResponse.class){
						params[i] = response;
					}
				}
				returnVal = mh.invoke(obj, params);
			}else{
				//��������
				returnVal = mh.invoke(obj);
			}
			System.out.println("returnVal:"+returnVal);
			//�����ͼ��
			String viewName = returnVal.toString();
			/*
			 * �����ͼ�����ԡ�redirect:����ͷ�����ض��򣬷���ת��
			 */
			if(viewName.startsWith("redirect:")){
				//����ͼ���е�redirect:����������
				//�������ض����ַ
				String redirectPath = contextPath+"/"+viewName.substring("redirect:".length());
				//�ض���
				response.sendRedirect(redirectPath);
			}else{
				/*
				 * ����ͼ��ӳ���������jsp
				 * jsp��ַ="/WEB-INF/"+��ͼ��+".jsp"
				 */
				//ӳ���������jsp
				String jspPath = "/WEB-INF/"+viewName+".jsp";
				request.getRequestDispatcher(jspPath).forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
