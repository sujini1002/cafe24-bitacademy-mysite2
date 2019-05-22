package com.cafe24.mysite.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	public Boolean insert(BoardVo boardVo) {
		int count = sqlSession.insert("board.insert", boardVo);
		return 1==count;
	}
	public Boolean updataByOrderNo(BoardVo boardVo) {
		int count = sqlSession.update("board.updateByOrderNo", boardVo);
		return 1==count;
	}
	public Long lastID() {
		return sqlSession.selectOne("board.lastID");
	}
	public BoardVo getView(long no) {
		return sqlSession.selectOne("board.getView", no);
	}
	public BoardVo getTitle(long no) {
		return sqlSession.selectOne("board.getTitle", no);
	}
	public Boolean reply(BoardVo boardVo) {
		int count = sqlSession.insert("board.reply", boardVo);
		return 1==count;
	}
	public List<BoardVo> getList(){
		return sqlSession.selectList("board.getList");
	}
	public Boolean update(BoardVo boardVo) {
		int count = sqlSession.update("board.update", boardVo);
		return 1==count;
	}
	public Boolean delete(long no) {
		int count = sqlSession.delete("board.delete",no);
		return count==1;
	}
	public Long getUser(long no) {
		return sqlSession.selectOne("board.getUser", no);
	}
	public Boolean updateByHit(long no) {
		int count = sqlSession.update("board.updateByHit",no);
		return count == 1;
	}
}
