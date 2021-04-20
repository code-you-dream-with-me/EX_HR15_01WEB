package com.sist.ehr.member.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.tree.TreePath;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sist.ehr.cmn.DTO;
import com.sist.ehr.cmn.Search;
import com.sist.ehr.member.domain.Level;
import com.sist.ehr.member.domain.User;
//mybatis오류 확인 
//1. NAMESPACE+.+id 확인:.
//2. query오류 확인
//3. resultType 확인
@Repository
public class UserDaoImpl {
	final static Logger LOG = LoggerFactory.getLogger(UserDaoImpl.class);
	
	final String NAMESPACE = "com.sist.ehr.member";//com.sist.ehr.member.doDelete
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	

	
	public UserDaoImpl() {}
	

    /**
     * 다건조회
     * @param user
     * @return List<User>
     * @throws SQLException
     */
	public List<User> getAll(User user)throws SQLException{
		
		List<User> list=null;
		String statement = this.NAMESPACE+".getAll";	
		LOG.debug("=============================");
		LOG.debug("=statement=\n"+statement);
		LOG.debug("=param=\n"+user);
		LOG.debug("=============================");			
		
		
		list = sqlSessionTemplate.selectList(statement, user);
		for(User vo :list) {
			LOG.debug(vo.toString());
		}
		
		return list;
	}

	/**
	 * 등록 건수 조회
	 * @param user
	 * @return int
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public int count(User user)throws ClassNotFoundException,SQLException{
		int cnt = 0;
		
		//mybatis sql: NAMESPACE+.+id;
		String statement = this.NAMESPACE+".count";			
		cnt = sqlSessionTemplate.selectOne(statement, user);
		
		
		LOG.debug("=============================");
		LOG.debug("=99_cnt="+cnt);
		LOG.debug("=============================");			

		
		return cnt;
	}


	public int doDelete(DTO dto) throws SQLException {
		int flag = 0;
		User   user = (User)dto;
		
		//mybatis sql: NAMESPACE+.+id;
		String statement = this.NAMESPACE+".doDelete";
		
		LOG.debug("=============================");
		LOG.debug("=user="+user);
		LOG.debug("=statement="+statement);
		LOG.debug("=============================");		
		

		
		flag = this.sqlSessionTemplate.delete(statement, user);
		return flag;
	}


	
	public int doInsert(DTO dto) throws SQLException {
		int flag = 0;
		User user = (User) dto;
		//mybatis sql: NAMESPACE+.+id;
		String statement = this.NAMESPACE+".doInsert";		
		LOG.debug("=============================");
		LOG.debug("=user="+user);
		LOG.debug("=statement="+statement);
		LOG.debug("=============================");	
		flag = sqlSessionTemplate.insert(statement, user);
		
		return flag;
	}


	
	public DTO doSelectOne(DTO dto) throws SQLException {

		User inVO  = (User) dto;
		User outVO = null;

		String statement = this.NAMESPACE+".doSelectOne";		
		LOG.debug("=============================");
		LOG.debug("=user="+inVO);
		LOG.debug("=statement="+statement);
		LOG.debug("=============================");	
		
		outVO = this.sqlSessionTemplate.selectOne(statement, inVO);

		LOG.debug("=============================");
		LOG.debug("=1 outVO="+outVO);
		LOG.debug("=============================");
		
		if(null == outVO) {
			LOG.debug("=============================");
			LOG.debug("=null outVO ="+outVO);
			LOG.debug("=============================");			
			throw new EmptyResultDataAccessException("여기 EmptyResultDataAccessException",1);
		}
		
		return outVO;
	}


	
	public List<?> doRetrieve(DTO dto) throws SQLException {
		Search  param = (Search) dto;
		LOG.debug("=param=\n"+param);
		String statement = this.NAMESPACE+".doRetrieve";
		
		LOG.debug("=============================");	
		LOG.debug("=param=\n"+param);
		LOG.debug("=statement="+statement);
		LOG.debug("=============================");	
		
		List<User> list = sqlSessionTemplate.selectList(statement, param);
		for(User vo :list) {
			LOG.debug("vo:"+vo);
		}
		
		return list;
	}


	
	public int doUpdate(DTO dto) throws SQLException {
		int flag = 0;
		User user = (User) dto;
		
		LOG.debug("=user=\n"+user);
		String statement = this.NAMESPACE+".doUpdate";		
		LOG.debug("=============================");
		LOG.debug("=user="+user);
		LOG.debug("=statement="+statement);
		LOG.debug("=============================");	
		flag = sqlSessionTemplate.update(statement, user);

		return flag;
	}
}

























