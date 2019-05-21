package com.cafe24.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe24.mysite.repository.BoardDao;
import com.cafe24.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public Boolean write(BoardVo boardVo) {
		return boardDao.insert(boardVo);
	}

	public Long lastID() {
		return boardDao.lastID();
	}

	public BoardVo getView(long no) {
		return boardDao.getView(no);
	}
	
	public BoardVo getTitle(long no) {
		return boardDao.getTitle(no);
	}

	public Boolean reply(BoardVo boardVo) {
		//정렬 순서 update
		boardDao.updataByOrderNo(boardVo);
		//답글작성
		return boardDao.reply(boardVo);
	}
	
	public List<BoardVo> getList(){
		return boardDao.getList();
	}
	
}
