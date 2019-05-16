package com.cafe24.mysite.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cafe24.mysite.service.UserService;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/join",method = RequestMethod.GET)
	public String join() {
		return "user/join";
	}
	
	@RequestMapping(value="/join",method = RequestMethod.POST)
	public String join(@ModelAttribute UserVo userVo) {
		userService.join(userVo);
		return "redirect:/user/joinsuccess";//dispatcher가 컨텍스트 패스를 붙이고 다시 리다이렉트를 보낸다.
	}
	
	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		
		return "user/joinsuccess";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.GET)
	public String login() {
		
		return "user/login";
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	public String login(@RequestParam(value="email",required = true,defaultValue = "")String email,
						@RequestParam(value="password",required = true,defaultValue = "")String password,
						HttpSession session,
						Model model) {
		
		UserVo authUser = userService.getUser(new UserVo(email,password));
		if(authUser == null) {
			model.addAttribute("result", "fail");
			return "/user/login";
		}
		
		//session 처리(지금은 HttpSession을 사용(기술침투)하지만 나중엔 제거 예정-interceptor)
		session.setAttribute("authUser", authUser);
		
		
		return "redirect:/";
	}
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	public String logout(HttpSession session) {
		
		session.removeAttribute("authUser");
		return "redirect:/";
	}
	@RequestMapping(value="/update",method = RequestMethod.GET)
	public String update(Model model,HttpSession session) {
		UserVo authUserVo = (UserVo)session.getAttribute("authUser");
		if(authUserVo == null) {
			return "redirect:/";
		}
		model.addAttribute("userVo", userService.getUser(authUserVo.getNo()));
		return "user/update";
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public String update(@ModelAttribute UserVo updateUserVo,
						HttpSession session,
						Model model) {
		
		boolean result = userService.update(updateUserVo);
		if(result) {
			session.setAttribute("authUser", updateUserVo);
		}
		return "user/updatesuccess";
	}
}
