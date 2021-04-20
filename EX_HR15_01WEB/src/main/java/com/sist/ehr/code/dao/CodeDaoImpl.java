package com.sist.ehr.code.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sist.ehr.cmn.DTO;
import com.sist.ehr.code.domain.Code;
import com.sist.ehr.member.dao.UserDaoImpl;

@Repository
public class CodeDaoImpl {
	final static Logger LOG = LoggerFactory.getLogger(CodeDaoImpl.class);
	
	final String NAMESPACE = "com.sist.ehr.code";//com.sist.ehr.code.doDelete
		
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	
	public List<?> getCodeRetrieve(Map<String,Object> code) throws SQLException {
		//Code param = (Code) dto;
		String statement = NAMESPACE+""+".doRetrieve";
		
		
		Map<String,Object>  codeMap=code;
/*		
SELECT mst_code as mstCode, det_code as detCode, mst_nm as mstNm, det_nm as detNm, seq, use_yn as useYn 
FROM com_code 
WHERE use_yn = '1' 
AND mst_code IN ( ? , ? ) ORDER BY mst_code,seq 
COM_PAGE_SIZE(String), MEMBER_SEARCH(String)
		 */
		
		return sqlSessionTemplate.selectList(statement, codeMap);
	}
	
}
