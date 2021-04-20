package com.sist.ehr.member.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.sist.ehr.cmn.DTO;
import com.sist.ehr.member.dao.UserDaoImpl;
import com.sist.ehr.member.domain.Level;
import com.sist.ehr.member.domain.User;

@Service
public class UserServiceImpl implements UserService {

	final Logger LOG = LoggerFactory.getLogger(this.getClass());
	
	public final static int MIN_LOGIN_COUNT_FOR_SILVER   = 50;//SILVER로 등업 하기 위한 최소 로그인 Count 
	public final static int MIN_RECCOMEND_COUNT_FOR_GOLD = 30;//GOLD로 등업 하기 위한 최소 추천 Count
	
	@Autowired
	private UserDaoImpl userDao;
	
	@Autowired
	@Qualifier("mailSenderImpl")
	private MailSender mailSender;
	
	
	public UserServiceImpl() {}
	

	/**
	 * 최초 회원 가입시 등급 : BASIC
	 * Level이 Null이면 Basic 처리
	 * @param dto
	 * @return
	 * @throws SQLException
	 */
	@Override
	public int add(DTO dto) throws SQLException {
		int flag = 0;
		User user = (User) dto;
		
		if(null == user.getLevel()) {
			user.setLevel(Level.BASIC);
		}
		
		flag = this.userDao.doInsert(user);
		
		return flag;
	}


	//회원 데이터를 모두 조회 한다.
	//회원 데이터를 1건 조회후 등업 대상인지 확인 한다.
	// -사용자 레벨은 : BASIC,SILVER,GOLD
	// -사용자가 처음 가입 하면 : BASIC
	// -가입이후 50회 이상 로그인 하면 : SILVER
	// -SILVER 레벨이면서 30번 이상 추천을 받으면 GOLD로 레벨 UP.
	// -사용자 레벨의 변경 작업은 일정한 주기를 가지고 일괄처리.(트랜잭션관리)
	/*
	 * 코드 개선
	 * 1. 코드에 중복된 부분이 없는가?
	 * 2. 코드가 무엇을 하는 것인지 이해하기 불편하지 않은가?
	 * 3. 코드가 자신이 있어야 할 자리에 있는가?
	 * 4. 변화에 쉽게 대응할 수 있게 작성되어 있는가?
	 */
	//등업 대상이면 : 해당 레벨로 등업 한다.
	@Override
	public void upgradeLevels() throws SQLException, IllegalAccessException {

		User userSearch=new User();
		userSearch.setuId("H_124");
		List<User> users = userDao.getAll(userSearch);		
		for( User user:users) {
			//1. upgrade대상 선정
			if(canUpgradeLevel(user)==true) {
				upgradeLevel(user);
			}
		}	
	}
	
	//등업처리
	protected void upgradeLevel(User user) throws SQLException {		
		LOG.debug("upgradeLevel:"+user.getuId());
//		if("H_124_04".equals(user.getuId())) {
//			//사용자 정의 예외 발생!->TestUserServiceException
//			 throw new TestUserServiceException("트랜잭션 테스트:"+user.getuId());
//			 
//		}		
		
		
		user.upgradeLevel();
		this.userDao.doUpdate(user);
		
		//email전송
		sendUpgradeEmail(user);
	}
	
	//email전송
	private void sendUpgradeEmail(User user) {
		
		/*
		POP 서버명 : pop.naver.com
		SMTP 서버명 : smtp.naver.com  
		POP포트 : 995, 보안연결(SSL) 필요
		SMTP 포트 : 465, 보안 연결(SSL) 
		필요아이디 : jamesol
		비밀번호 : 네이버 로그인 비밀번호		
		*/
		String from = "jamesol@naver.com";//from
		String title= user.getName()+"님 등업(https://cafe.naver.com/kndjang)";//제목
		String contents = user.getuId()+"님 등급이 <br/>"+user.getLevel().name()+"로 등업 되었습니다.";//내용
		String recAddr  = user.getEmail();//받는사람
		SimpleMailMessage simpleMessage=new SimpleMailMessage();
		
		simpleMessage.setFrom(from);
		simpleMessage.setTo(recAddr);
		simpleMessage.setSubject(title);
		//simpleMessage.setSentDate(new Date());
		simpleMessage.setText(contents);
		
		
		mailSender.send(simpleMessage);
		
		
	}
	
	
	
	//업그레이드 가능 확인 메소드
	private boolean canUpgradeLevel(User user) throws IllegalAccessException {
		Level currLevel = user.getLevel();
		//레벨별로 구분해서 조건 판단
		switch(currLevel) {
			case BASIC:   return (user.getLogin()>=MIN_LOGIN_COUNT_FOR_SILVER);
			case SILVER:  return (user.getRecommend() >=MIN_RECCOMEND_COUNT_FOR_GOLD);
			case GOLD:	  return false;
			default: throw new IllegalAccessException("Unkown Level");
		}
	}


	@Override
	public List<?> doRetrieve(DTO dto) throws SQLException {
		return this.userDao.doRetrieve(dto);
	}


	@Override
	public DTO doSelectOne(DTO dto) throws SQLException {
		return this.userDao.doSelectOne(dto);
	}


	@Override
	public int doUpdate(DTO dto) throws SQLException {
		return this.userDao.doUpdate(dto);
	}


	@Override
	public int doDelete(DTO dto) throws SQLException {
		
		return this.userDao.doDelete(dto);
	}
	
	
	
	
	
}
