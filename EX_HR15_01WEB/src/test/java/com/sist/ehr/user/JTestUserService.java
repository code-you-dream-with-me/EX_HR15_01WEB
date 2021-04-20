package com.sist.ehr.user;


import static com.sist.ehr.member.service.UserServiceImpl.MIN_LOGIN_COUNT_FOR_SILVER;
import static com.sist.ehr.member.service.UserServiceImpl.MIN_RECCOMEND_COUNT_FOR_GOLD;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.MailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;


import com.sist.ehr.member.dao.UserDaoImpl;
import com.sist.ehr.member.domain.Level;
import com.sist.ehr.member.domain.User;
import com.sist.ehr.member.service.UserService;
import com.sist.ehr.member.service.UserServiceImpl;



//메소드 수행 순서: method ASCENDING ex)a~z
//메소드 수행 순서: method ASCENDING ex)a~z
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class) //스프링 테스트 컨텍스트 프레임의 JUnit기능 확장
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml",
                                 "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"		
})
public class JTestUserService {
	final Logger LOG = Logger.getLogger(this.getClass());
	
	@Autowired
	ApplicationContext context;
	
	@Autowired
	UserService   userService;
	
	@Autowired
	UserDaoImpl      userDao;
	
	@Autowired
	DataSource    dataSource;
	
	@Autowired
	PlatformTransactionManager transactionManager;
	
	@Autowired
	@Qualifier("mailSenderImpl")
	private MailSender mailSender;
	
	private List<User> users;
	
	
	@Before
	public void setUp() throws Exception {
		LOG.debug("setUp()");
		// -사용자 레벨은 : BASIC,SILVER,GOLD
		// -사용자가 처음 가입 하면 : BASIC
		// -가입이후 50회 이상 로그인 하면 : SILVER
		// -SILVER 레벨이면서 30번 이상 추천을 받으면 GOLD로 레벨 UP.
		// -사용자 레벨의 변경 작업은 일정한 주기를 가지고 일괄처리.(트랜잭션관리)		
		users = Arrays.asList(
					 new User("H_124_01","이상무_01","1234",Level.BASIC,MIN_LOGIN_COUNT_FOR_SILVER-1,0,"jamesol@naver.com","")//BASIC
					,new User("H_124_02","이상무_02","1234",Level.BASIC,MIN_LOGIN_COUNT_FOR_SILVER,10,"jamesol@naver.com","")//BASIC->SILVER
					,new User("H_124_03","이상무_03","1234",Level.SILVER,MIN_LOGIN_COUNT_FOR_SILVER+1,MIN_RECCOMEND_COUNT_FOR_GOLD-1,"jamesol@naver.com","")//SILVER
					,new User("H_124_04","이상무_04","1234",Level.SILVER,MIN_LOGIN_COUNT_FOR_SILVER+1,MIN_RECCOMEND_COUNT_FOR_GOLD,"jamesol@naver.com","")//SILVER->GOLD
					,new User("H_124_05","이상무_05","1234",Level.GOLD,MIN_LOGIN_COUNT_FOR_SILVER+49,MIN_RECCOMEND_COUNT_FOR_GOLD+1,"jamesol@naver.com","")//GOLD
				);
		
	}

	@Test
	@Ignore
	public void add() throws SQLException {
		//* Level이 Null이면 :Level.BASIC처리
		//1. 기존데이터 삭제
		//2. Level이 Null + 데이터 등록
		//3. 조회 + 등급 ==Level.BASIC
		
		//1.
		for(User user :users) {
			userDao.doDelete(user);
		}
		
		//2.
		User userWithLevel = users.get(4);//Level.GOLD
		User userWithOutLevel = users.get(0);//Level.BASIC
		userWithOutLevel.setLevel(null);
		
		this.userService.add(userWithLevel);
		this.userService.add(userWithOutLevel);
		
		
		User userWithLevelRead = (User) userDao.doSelectOne(userWithLevel);
		User userWithoutLevelRead = (User) userDao.doSelectOne(userWithOutLevel);
		
		//Level.GOLD == Level.GOLD
		assertThat(userWithLevelRead.getLevel(), is(userWithLevel.getLevel()));
		//Level == null -> Level.BASIC
		assertThat(userWithoutLevelRead.getLevel(),is(Level.BASIC));
		
		
		
	}
	
	
	//users를 대상으로 등업
	/*
	H_124_01	이상무_01	1234	1	49	0
	H_124_02	이상무_02	1234	1	50	10
	H_124_03	이상무_03	1234	2	51	29
	H_124_04	이상무_04	1234	2	51	30
	H_124_05	이상무_05	1234	3	99	31

	H_124_01	이상무_01	1234	1	49	0
	H_124_02	이상무_02	1234	2	50	10  O
	H_124_03	이상무_03	1234	2	51	29
	H_124_04	이상무_04	1234	3	51	30  O -> 예외 발생
	H_124_05	이상무_05	1234	3	99	31
	*/	
	@Test
	public void upgradeLevels() throws SQLException, IllegalAccessException {
		//1. 데이터 삭제
		//2. 데이터 입력
		
		//1.
		for(User user :users) {
			userDao.doDelete(user);
		}
		
		int flag = 0;
		//2.
		for(User user :users) {
			flag+=userDao.doInsert(user);
		}
		
		assertThat(flag, is(5));
		
		//등업
		this.userService.upgradeLevels();

		//등업 로직 check
		checkLevel(users.get(0),Level.BASIC);
		checkLevel(users.get(1),Level.SILVER);
		checkLevel(users.get(2),Level.SILVER);
		checkLevel(users.get(3),Level.GOLD);
		checkLevel(users.get(4),Level.GOLD);
		
	}
	
	/**
	 * 등업한 Level과 예상 Level 비교
	 * @param user
	 * @param expectedLevel
	 * @throws SQLException
	 */
	private void checkLevel(User user, Level expectedLevel) throws SQLException {
		User userUpLevel =  (User) this.userDao.doSelectOne(user);//등업한 데이터 조회
		assertThat(userUpLevel.getLevel(), is(expectedLevel));
	}
	
	
	
	
	
	
	@After
	public void tearDown() throws Exception {
		LOG.debug("tearDown()");
	}

	@Test
	public void beans() {
		LOG.debug("context:"+context);
		LOG.debug("userService:"+userService);
		LOG.debug("userDao:"+userDao);
		LOG.debug("dataSource:"+dataSource);
		LOG.debug("transactionManager:"+transactionManager);
		
		
	    
		//assertThat(this.userService, is(java.lang.reflect.Proxy.class));
		
		//assertSame(userService, java.lang.reflect.Proxy.class);
		
		
		assertThat(this.transactionManager, is(notNullValue()));
		assertThat(this.dataSource, is(notNullValue()));
		assertThat(this.userDao, is(notNullValue()));
		assertThat(this.userService, is(notNullValue()));
		assertThat(this.context, is(notNullValue()));
	}

//	H_124_01	이상무_01	1234	1	49	0
//	H_124_02	이상무_02	1234	2	50	10  O -> rollback error
//	H_124_03	이상무_03	1234	2	51	29
//	H_124_04	이상무_04	1234	3	51	30  O -> 예외 발생
//	H_124_05	이상무_05	1234	3	99	31	
	
	//Transcation 테스트
	@Test
	@Ignore
	public void upgradeAllOrNothing() throws Exception {

		//1. 데이터 삭제
		//2. 데이터 입력 
		//3. 등업
		//1.
		for(User user :users) {
			userDao.doDelete(user);
		}	
		  
		int flag = 0;
		//2.
		for(User user :users) {
			flag+=userDao.doInsert(user);
		}
		
		assertThat(flag, is(5));
		
		try {
			//com.sist.ehr.TestUserServiceException: 트랜잭션 테스트:H_124_04
			userService.upgradeLevels();
			
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}catch(RuntimeException e) {
			LOG.debug("=======================");
			LOG.debug("=Exception="+e.getMessage());
			LOG.debug("=======================");

			//e.printStackTrace();
		}
		

		//checkLevel(users.get(1),Level.BASIC);//Transaction처리 되면 Rollback->
		//강제 예외 발생 코드를 제외 하였으므로, 정상 등업 된다.
		checkLevel(users.get(1),Level.SILVER);//Transaction처리 되면 Rollback->
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
