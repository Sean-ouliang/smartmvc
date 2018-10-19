package demo;

import javax.servlet.http.HttpServletRequest;

import base.annotation.RequestMapping;

public class LoginController {
	
	@RequestMapping("/toLogin.do")
	public String toLogin(){
		System.out.println("LoginController的toLogin方法...");
		return "login";
	}
	
	@RequestMapping("/login.do")
	public String login(HttpServletRequest request){
		System.out.println("LoginController的login方法");
		String username = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		System.out.println(username+" "+pwd);
		if("Sean".equals(username)&&"test".equals(pwd)){
			//登录成功
			return "redirect:list.do";			
		}else{
			//登录失败
			request.setAttribute("login_failed", "用户名或密码错误");
			return "login";
		}
	}
	
	@RequestMapping("/list.do")
	public String list(){
		System.out.println("LoginController的list方...");
		return "listUser";
	}
}
