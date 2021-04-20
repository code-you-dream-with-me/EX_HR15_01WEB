package com.sist.ehr.code;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sist.ehr.cmn.DTO;
import com.sist.ehr.code.dao.CodeDaoImpl;
import com.sist.ehr.code.domain.Code;
import com.sist.ehr.user.JTestUserDao;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class) //스프링 테스트 컨텍스트 프레임의 JUnit기능 확장
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
                                   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"		
})
public class JTestCodeImpl {
	final static Logger LOG = Logger.getLogger(JTestCodeImpl.class);
	
	@Autowired
	ApplicationContext  context;//테스트 오브젝트가 만들어 지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 주입된다.
	
	@Autowired
	CodeDaoImpl  codeDaoImpl;
	
	@Test
	public void getCode() throws Exception{
        Map<String,Object>  codeMap =new HashMap<String,Object>();
		List codeList = new ArrayList<String>();
		codeList.add("COM_PAGE_SIZE");
		codeList.add("MEMBER_SEARCH");
		codeMap.put("codeList",codeList);

		List<Code> list = (List<Code>) codeDaoImpl.getCodeRetrieve(codeMap);
		
		for(Code vo :list) {
			LOG.debug("vo:"+vo);
		}
	}
	
	@Before
	public void setUp() throws Exception {
		LOG.debug("=======================");
		LOG.debug("=@Before=");
		LOG.debug("=======================");			
	}

	@After
	public void tearDown() throws Exception {
		LOG.debug("=======================");
		LOG.debug("=@after=");
		LOG.debug("=======================");			
	}

	@Test
	public void bean() {
		LOG.debug("context:"+context);
		LOG.debug("codeDaoImpl:"+codeDaoImpl);
		assertThat(context, is(notNullValue()));
		assertThat(codeDaoImpl, is(notNullValue()));
	}

}
