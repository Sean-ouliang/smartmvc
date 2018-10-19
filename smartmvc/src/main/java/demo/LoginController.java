package demo;

import javax.servlet.http.HttpServletRequest;

import base.annotation.RequestMapping;

public class LoginController {
	
	@RequestMapping("/toLogin.do")
	public String toLogin(){
		System.out.println("LoginController��toLogin����...");
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request){
		System.out.println("LoginController��login����");
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		System.out.println(username+" "+pwd);
		if("Sean".equals(username)&&"test".equals(pwd)){
			//��¼�ɹ�
			return "redirect:list.do";			
		}else{
			//��¼ʧ��
			request.setAttribute("login_failed", "�û������������");
			return "login";
		}
	}
	
	@RequestMapping("/list.do")
	public String list(){
		System.out.println("LoginController��list��...");
		return "listUser";
	}
}
