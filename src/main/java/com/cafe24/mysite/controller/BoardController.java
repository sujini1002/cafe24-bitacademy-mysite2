package com.cafe24.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.cafe24.mysite.service.BoardService;
import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping({"","/list"})
	public String list(Model model) {
		List<BoardVo> list = boardService.getList();
		model.addAttribute("list", list);
		return "board/list";
	}
	
	//write페이지 가기
	@RequestMapping(value="/write",method = RequestMethod.GET)
	public String write(HttpSession session) {
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		
		return "board/write";
	}
	
	//답글
	@RequestMapping(value="/write/{no}",method = RequestMethod.GET)
	public String write(HttpSession session,@PathVariable(value="no")long no,Model model) {
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		if(authUser == null) {
			return "redirect:/user/login";
		}
		System.out.println("답글"+no);
		model.addAttribute("boardVo", boardService.getTitle(no));
		return "board/write";
	}
	
	//작성
	@RequestMapping(value="/write",method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo boardVo) {
		if(boardVo.getNo()==null) {
			boardService.write(boardVo);
		}else {
			boardService.reply(boardVo);
		}
		
		return "redirect:/board/view/"+boardService.lastID();
	}
	//view페이지
	@RequestMapping(value="/view/{no}", method = RequestMethod.GET)
	public String view(Model model,@PathVariable(value="no")long no) {
		System.out.println("view==="+no);
		model.addAttribute("boardVo", boardService.getView(no));
		return "board/view";
	}
	
	
}
