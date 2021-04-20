/**
 * 
 */
package com.sist.ehr.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sist.ehr.cmn.DTO;
import com.sist.ehr.code.dao.CodeDaoImpl;
import com.sist.ehr.code.domain.Code;
import com.sist.ehr.member.dao.UserDaoImpl;
import com.sist.ehr.member.domain.Level;
import com.sist.ehr.member.domain.User;
 
/**
 * @author sist
//	assertArrayEquals(a, b); 
//	- 배열 A와 B가 일치함을 확인한다.
//
//	assertEquals(a, b);
//	- 객체 A와 B가 일치함을 확인한다.
//
//	assertEquals(a, b, c);
//	- 객체 A와 B가 일치함을 확인한다.
//
//	- a: 예상값, b:결과값, c: 오차범위
//
//	​
//
//	assertSame(a, b); 
//	- 객체 A와 B가 같은 객임을 확인한다.
//
//	​
//	assertTrue(a); 
//	- 조건 A가 참인가를 확인한다.	
 */

//메소드 수행 순서: method ASCENDING ex)a~z
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class) //스프링 테스트 컨텍스트 프레임의 JUnit기능 확장
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
                                   "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"		
})
public class JTestUserDao {

	final static Logger LOG = Logger.getLogger(JTestUserDao.class);
	
	@Autowired
	ApplicationContext  context;//테스트 오브젝트가 만들어 지고 나면 스프링 테스트 컨텍스트에 의해 자동으로 주입된다.
	
	@Autowired
	private UserDaoImpl dao;
		
	User user01;
	User user02;
	User user03;
	
	
	@Autowired
	CodeDaoImpl code;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		LOG.debug("=======================");
		LOG.debug("=@Before=");
		LOG.debug("=======================");	
		
		LOG.debug("=context="+context);
		/*
사용자 레벨은 : BASIC,SILVER,GOLD
사용자가 처음 가입 하면 : BASIC
가입이후 50회 이상 로그인 하면 : SILVER
SILVER 레벨이면서 30번 이상 추천을 받으면 GOLD로 레벨 UP.
사용자 레벨의 변경 작업은 일정한 주기를 가지고 일괄처리.(트랜잭션관리)
		 */
		user01=new User("H_124_01","이상무","1234",Level.BASIC,1,0,"jamesol@naver.com","");
		user02=new User("H_124_02","이상무","1234",Level.SILVER,51,0,"jamesol@naver.com","");
		user03=new User("H_124_03","이상무","1234",Level.GOLD,70,31,"jamesol@naver.com","");
		
		//dao = context.getBean("userDao", UserDao.class);
		
	}

	
	@Test
	public void doUpdate() throws SQLException {
		//1. 기존데이터 삭제
		//2. 신규데이터 입력
		//3. 데이터 수정 + update
		//*  비교
		
		//1.
		dao.doDelete(user01);
		dao.doDelete(user02);
		dao.doDelete(user03);
		
		//2. 
		int flag = dao.doInsert(user01);
		assertThat(flag, is(1));
		
		flag += dao.doInsert(user02);
		assertThat(flag, is(2));
		
		//3.
		user01.setLogin(user01.getLogin()+99);
		user01.setName(user01.getName()+"_U");
		user01.setPasswd(user01.getPasswd()+"_U");
		user01.setRecommend(user01.getRecommend()+10);
		user01.setLevel(Level.SILVER);
		LOG.debug("user01:"+user01);
		
		flag = dao.doUpdate(user01);
		assertThat(flag, is(1));
		
		//3.1. 수정데이터 조회
		User changeUser = (User) dao.doSelectOne(user01);
		LOG.debug("changeUser:"+changeUser);
		//실패 case
		//user01.setLogin(88);
		checkUser(changeUser, user01);
		//changeUser:User [uId=H_124_01, name=이상무_U, passwd=1234_U, level=SILVER, login=100, recommend=10, toString()=com.sist.ehr.User@75b21c3b]
		//outVO     =User [uId=H_124_01, name=이상무_U, passwd=1234_U, level=SILVER, login=100, recommend=10, toString()=com.sist.ehr.User@75b21c3b]
		
		//3.2. update 하지않은 데이터 비교
		User unChangeUser = (User) dao.doSelectOne(user02);
		checkUser(unChangeUser, user02);
		
	}
	
	@Test
	public void getAll() throws ClassNotFoundException, SQLException{
		//데이터 3건 삭제
		dao.doDelete(user01);
		dao.doDelete(user02);
		dao.doDelete(user03);
		
		//데이터 추가
		int flag = 0;
		flag=dao.doInsert(user01);
		flag+=dao.doInsert(user02);
		flag+=dao.doInsert(user03);
		assertThat(flag, is(3));
		
		//데이터 전체 조회
		//검색용도
		User user09=new User();
		user09.setuId("H_124");
		
		List<User> list = dao.getAll(user09);
		assertThat(list.size(), is(3));
		
		
	}
	
	
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		LOG.debug("-----------------------");
		LOG.debug("=@BeforeClass=");
		LOG.debug("-----------------------");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		LOG.debug("-----------------------");
		LOG.debug("=@AfterClass=");
		LOG.debug("-----------------------");		
	}



	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		LOG.debug("=======================");
		LOG.debug("=@After=");
		LOG.debug("=======================");			
	}

	//@Test(expected=EmptyResultDataAccessException) :  EmptyResultDataAccessException이 발생하면 성공
	@Test(expected = EmptyResultDataAccessException.class)
	@Ignore
	public void getFailure() throws ClassNotFoundException, SQLException {
		LOG.debug("=======================");
		LOG.debug("=@getFailure=");
		LOG.debug("=======================");

		
		//삭제
		dao.doDelete(user01);
		dao.doDelete(user02);
		dao.doDelete(user03);
		
		User user04=new User();
		user04.setuId("unknown_id");
		//단건조회
		dao.doSelectOne(user04);
		
	}
	
	//milliseconds
	//@Test(timeout = 1000)
	@Test
	public void addAndGet() throws ClassNotFoundException, SQLException {
		LOG.debug("=======================");
		LOG.debug("=@addAndGet=");
		LOG.debug("=======================");

		
		//검색용도
		User user09=new User();
		user09.setuId("H_124");
		//삭제
		dao.doDelete(user01);
		dao.doDelete(user02);
		dao.doDelete(user03);
		
		//등록
		int flag = dao.doInsert(user01);
		assertThat(flag, is(dao.count(user09)));//flag(actual)과 is(예상) 비교
		
		
		flag += dao.doInsert(user02);
		assertThat(flag, is(dao.count(user09)));//flag(actual)과 is(예상) 비교
		

		flag += dao.doInsert(user03);
		assertThat(flag, is(dao.count(user09)));//flag(actual)과 is(예상) 비교
		
		
		//단건조회:user01  
		User vsUser01 =(User) dao.doSelectOne(user01);
		
		//단건조회:user02
		User vsUser02 =(User) dao.doSelectOne(user02);
		
		//단건조회:user03
		User vsUser03 =(User) dao.doSelectOne(user03);
		
		//비교 
		checkUser(vsUser01, user01);
		checkUser(vsUser02, user02);
		checkUser(vsUser03, user03);
	}
	
	private void checkUser(User vsUser, User user01) {
		//비교
		assertThat(vsUser.getuId(), is(user01.getuId()));
		assertThat(vsUser.getName(), is(user01.getName()));
		assertThat(vsUser.getPasswd(), is(user01.getPasswd()));	
		//컬럼추가:
		assertThat(vsUser.getLevel(),is(user01.getLevel()));
		assertThat(vsUser.getLogin(),is(user01.getLogin()));
		assertThat(vsUser.getRecommend(),is(user01.getRecommend()));
		
		
	}
	
	
	@Test
	@Ignore
	public void test02() {
		LOG.debug("=======================");
		LOG.debug("=@Test test02=");
		LOG.debug("=======================");
	}	

}
