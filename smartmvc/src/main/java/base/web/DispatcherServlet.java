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
 * 控制器:
 *   负责接收所有请求，然后依据HandlerMapping的配置调用
 * 对应的Controller(处理器)来处理。
 *   依据处理器返回的处理结果（视图名）调用对应的jsp
 * @author tarena
 *
 */
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HeadlerMapping headlerMapping;
	
	@Override
	/**
	 * 在init方法里面，通过读取smartmvc.xml文件
	 * 获得处理器类名（比如demo.HelloController）,
	 * 然后将处理器实例化。接下来，将处理器实例交
	 * 给HandlerMapping(即映射处理器)来处理。
	 * 注：
	 *   HeaderMapping负责建立请求路径与处理器的对应关系。
	 */
	public void init() throws ServletException {
		/*
		 * 利用dom4j解析smartmvc.xml
		 */
		SAXReader reader = new SAXReader();
		//读取配置文件的位置及文件名
		String fileName = getServletConfig().getInitParameter("configLocation");
		//调用来加载器(ClassLoader)的方法获得一个输入流，
		//该输入流指向了smartmvc的配置文件
		InputStream inStream = getClass().getClassLoader()
				.getResourceAsStream(fileName);
		try {
			//解析smartmvc的配置文件的内容
			Document doc = reader.read(inStream);
			//获得跟元素
			Element root = doc.getRootElement();
			//获得根元素下面的所有子元素
			List<Element> beanEle = root.elements("bean");
			//创建用来存放处理器实例化的集合
			List beans = new ArrayList();
			//遍历所有子元素
			for(Element b:beanEle){
				//读取class属性值(即处理器类名)
				String className = b.attributeValue("class");
				System.out.println("className:"+className);
				//将处理器实例化
				Object bean = Class.forName(className).newInstance();
				//为了方便处理，将处理器实例化添加到List集合里面
				beans.add(bean);
			}
			System.out.println("beans:"+beans);
			
			//将处理器实例交给HeadlerMapping来处理
			headlerMapping = new HeadlerMapping();
			headlerMapping.process(beans);
			
		} catch (Exception e) {
			e.printStackTrace();
			//将异常抛给容器来处理
			throw new ServletException(e);
		}
	}
	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		/*
		 * 获得请求资源路径，然后截取其中的一部分
		 * 获得请求路径(path)，接下来，调用
		 * HeadlerMapping的getHeadler方法(以请求路径
		 * 作为参数)获得Headler对象。
		 * 通过Headler对象就可以调用处理器的方法了。
		 */
		String uri = request.getRequestURI();
		System.out.println("uri:"+uri);
		//获得应用名
		String contextPath = request.getContextPath();
		System.out.println("contextPath:"+contextPath);
		//截取请求资源路径的一部分
		String path = uri.substring(contextPath.length());
		System.out.println("path:"+path);
		
		//调用HeadlerMapping的方法获得Headler对象
		Headler headler = headlerMapping.getHeadler(path);
		System.out.println("headler:"+headler);
		
		//利用Headler对象来调用处理器的方法
		Method mh = headler.getMh();
		Object obj = headler.getObj();
		System.out.println("mh:"+mh);
		System.out.println("obj:"+obj);
		Object returnVal = null;
		try {
			/*
			 * 利用java反射分析处理器的方法，
			 * 如果处理器的方法带有参数，则将
			 * 该参数传给处理器方法。
			 * 注：
			 *   目前版本只支持两个参数
			 *   (request,response)
			 */
			//获得方法的参数类型
			Class[] types = mh.getParameterTypes();
			if(types.length>0){
				Object[] params = new Object[types.length];
				//带有参数
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
				//不带参数
				returnVal = mh.invoke(obj);
			}
			System.out.println("returnVal:"+returnVal);
			//获得视图名
			String viewName = returnVal.toString();
			/*
			 * 如果视图名是以“redirect:”开头，则重定向，否则转发
			 */
			if(viewName.startsWith("redirect:")){
				//将视图名中的redirect:除掉，生成
				//真正的重定向地址
				String redirectPath = contextPath+"/"+viewName.substring("redirect:".length());
				//重定向
				response.sendRedirect(redirectPath);
			}else{
				/*
				 * 将视图名映射成真正的jsp
				 * jsp地址="/WEB-INF/"+视图名+".jsp"
				 */
				//映射成真正的jsp
				String jspPath = "/WEB-INF/"+viewName+".jsp";
				request.getRequestDispatcher(jspPath).forward(request, response);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

}
