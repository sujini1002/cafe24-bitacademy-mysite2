package com.cafe24.mysite.exception;

import java.io.EOFException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(Exception.class)
	public void handleException(HttpServletRequest request,HttpServletResponse response,Exception e) throws Exception{ 
		//어플리케이션 외적인 것이므로 기술침투까진 아니다?/ 예외를 처리해야 하므로 Exception이 필요하다.
		
		//1.로깅
		e.printStackTrace();
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
//		Logger.error(errors.toString()); 파일로도 남기고 화면에도 출력할 수 있다.
		System.out.println(errors.toString());
		
		//2.안내페이지 가기+정상적 종료(response)
		request.setAttribute("uri", request.getRequestURI());
		request.setAttribute("exception", errors.toString());
		request.getRequestDispatcher("/WEB-INF/views/error/exception.jsp").forward(request, response);
	}
	
}
