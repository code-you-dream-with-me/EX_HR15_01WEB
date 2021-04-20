package com.sist.ehr.user;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.sist.ehr.cmn.Message;
import com.sist.ehr.cmn.Search;
import com.sist.ehr.member.domain.Level;
import com.sist.ehr.member.domain.User;




//메소드 수행 순서: method ASCENDING ex)a~z
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class) // 스프링 테스트 컨텍스트 프레임의 JUnit기능 확장
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" })
public class JTestUserController {
	final Logger LOG = LoggerFactory.getLogger(JTestUserController.class);

	@Autowired
	WebApplicationContext webApplicationContext;

	// 브라우저 대신할 Mock
	MockMvc mockMvc;

	User user01;
	User user02; 
	User user03;

	//검색
	Search search01;
	
	@Before
	public void setUp() throws Exception {
		user01 = new User("H_124_01", "이상무", "1234", Level.BASIC, 1, 0, "jamesol@naver.com", "");
		user02 = new User("H_124_02", "이상무", "1234", Level.SILVER, 51, 0, "jamesol@naver.com", "");
		user03 = new User("H_124_03", "이상무", "1234", Level.GOLD, 70, 31, "jamesol@naver.com", "");
		
		
		search01=new Search("30", "j", 10, 1);
		
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@After
	public void tearDown() throws Exception {
		LOG.debug("tearDown()");
	}
    
	@Test
	public void view() throws Exception {
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/user/user_view.do");	
		
		ResultActions resultActions =mockMvc.perform(createMessage)
				.andExpect(status().isOk());
		
		String result = resultActions.andDo(print())
		        .andReturn()
		        .getResponse().getContentAsString();		
		LOG.debug("result:"+result);
	}
	
	@Test
	public void doRetrieve() throws Exception {
		// url호출, param전달
		MockHttpServletRequestBuilder createMessage = 
				MockMvcRequestBuilders.get("/user/do_retrieve.do")
				.param("searchDiv", search01.getSearchDiv())
				.param("searchWord", search01.getSearchWord())
				.param("pageSize", String.valueOf(search01.getPageSize()))
				.param("pageNum", String.valueOf(search01.getPageNum()))
				;		
		
		ResultActions resultActions =mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
		
		String result = resultActions.andDo(print())
		        .andReturn()
		        .getResponse().getContentAsString();	
		
		//gson -> List
		Gson gson=new Gson(); 
		
		List<User> list = gson.fromJson(result, new TypeToken<List<User>>() {}.getType());
		
		for(User vo :list) {
			LOG.debug("vo:"+vo);
		}
		
				
	}
	
	@Test
	@Ignore
	public void doUpdateTest()throws Exception {
		//1. 기존데이터 삭제
		//2. 신규데이터 입력
		//3. 데이터 수정 + update
		
		//1.
		doDelete(user01);
		doDelete(user02);
		doDelete(user03);
		
		//2.
		int flag = doInsert(user01); 
		assertThat(flag, is(1));//flag(actual)과 is(예상) 비교
		
		
		flag += doInsert(user02);
		assertThat(flag, is(2));//flag(actual)과 is(예상) 비교
		

		flag += doInsert(user03);
		assertThat(flag, is(3));//flag(actual)과 is(예상) 비교
				
		user01.setLogin(user01.getLogin()+99);
		user01.setName(user01.getName()+"_U");
		user01.setPasswd(user01.getPasswd()+"_U");
		user01.setRecommend(user01.getRecommend()+10);
		user01.setLevel(Level.SILVER);
		LOG.debug("user01:"+user01);
		
		flag = doUpdate(user01);
		assertThat(flag, is(1));
		
		//3.1. 수정데이터 조회
		User changeUser = (User) doSelectOne(user01);
		LOG.debug("changeUser:"+changeUser);
		//실패 case
		//user01.setLogin(88);
		checkUser(changeUser, user01);		
		
	}
	
	
	public int doUpdate(User user01) throws Exception {
		// url호출, param전달
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_update.do")
				.param("uId", user01.getuId())
				.param("name", user01.getName())
				.param("passwd", user01.getPasswd())
				.param("level", user01.getLevel() + "")
				.param("login", user01.getLogin() + "")
				.param("recommend", user01.getRecommend() + "")
				.param("email", user01.getEmail());
/*
 *  크롬 브라우저와 같이 주요 브라우저들이 사양을 준수하고 charset=UTF-8로 인코딩되는 특수한 문자들을 올바르게 
 *  해석해주므로 정확히는 스프링 5.2부터, 
 *  스프링 부트의 경우 2.2.0부터 응답에 이를 포함시키지 않게 되었습니다.		
 *  //MediaType.APPLICATION_JSON(좀더 확인 필요)
 */
		ResultActions resultActions =mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(status().isOk());
				
		String result = resultActions.andDo(print())
		        .andReturn()
		        .getResponse().getContentAsString();	
		LOG.debug("-------------------------------------");
		LOG.debug("result:"+result);
		LOG.debug("-------------------------------------");		
		
		Gson gson=new Gson();
		Message getMessage =gson.fromJson(result, Message.class);
		
		LOG.debug("-------------------------------------");
		LOG.debug("getMessage:"+getMessage);
		LOG.debug("-------------------------------------");	
		
		return Integer.parseInt(getMessage.getMsgId());		  
	}
	
	
	@Test
	@Ignore
	public void addAndGet()throws Exception {
		LOG.debug("=======================");
		LOG.debug("=@addAndGet=");
		LOG.debug("=======================");
		
		//삭제
		doDelete(user01);
		doDelete(user02);
		doDelete(user03);
		
		
		//등록
		int flag = doInsert(user01); 
		assertThat(flag, is(1));//flag(actual)과 is(예상) 비교
		
		
		flag += doInsert(user02);
		assertThat(flag, is(2));//flag(actual)과 is(예상) 비교
		

		flag += doInsert(user03);
		assertThat(flag, is(3));//flag(actual)과 is(예상) 비교
		
		//단건조회:user01  
		User vsUser01 =(User) doSelectOne(user01);
		
		//단건조회:user02
		User vsUser02 =(User) doSelectOne(user02);
		
		//단건조회:user03
		User vsUser03 =(User) doSelectOne(user03);
		
		//비교 
		checkUser(vsUser01, user01);
		checkUser(vsUser02, user02);
		checkUser(vsUser03, user03);				
		
	}
	

	public User doSelectOne(User user01) throws Exception {
		// url호출, param전달
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.get("/user/do_selectone.do")
				.param("uId", user01.getuId());		
		ResultActions resultActions =mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());	
		
		String result = resultActions.andDo(print())
		        .andReturn()
		        .getResponse().getContentAsString();	
		
		Gson gson=new Gson();
		User outVO =gson.fromJson(result, User.class);
		
		LOG.debug("-------------------------------------");
		LOG.debug("outVO:"+outVO);
		LOG.debug("-------------------------------------");	
		
		checkUser(user01,outVO);
		
		return outVO;
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
	

	public void doDelete(User user01) throws Exception {
		// url호출, param전달
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_delete.do")
				.param("uId", user01.getuId());
		
		ResultActions resultActions =mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
		
		String result = resultActions.andDo(print())
		        .andReturn()
		        .getResponse().getContentAsString();	
		LOG.debug("-------------------------------------");
		LOG.debug("result:"+result);
		LOG.debug("-------------------------------------");			
			
		Gson gson=new Gson();
		Message getMessage =gson.fromJson(result, Message.class);
		
		LOG.debug("-------------------------------------");
		LOG.debug("getMessage:"+getMessage);
		LOG.debug("-------------------------------------");	
		
		String resultMsg = "";
		
		resultMsg = user01.getuId()+"님\n삭제 되었습니다.";
		
		Message message=new Message();
		message.setMsgId("1");
		message.setMsgContents(resultMsg);
		
		
		//assertThat(getMessage.getMsgId(), is(message.getMsgId()));
		//assertThat(getMessage.getMsgContents(), is(message.getMsgContents()));
				
	}
	

	public int doInsert(User user01) throws Exception {
		// url호출, param전달
		MockHttpServletRequestBuilder createMessage = MockMvcRequestBuilders.post("/user/do_insert.do")
				.param("uId", user01.getuId()).param("name", user01.getName()).param("passwd", user01.getPasswd())
				.param("level", user01.getLevel() + "").param("login", user01.getLogin() + "")
				.param("recommend", user01.getRecommend() + "").param("email", user01.getEmail());
		
		ResultActions resultActions =mockMvc.perform(createMessage)
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());
		
		String result = resultActions.andDo(print())
		        .andReturn()
		        .getResponse().getContentAsString();	
		LOG.debug("-------------------------------------");
		LOG.debug("result:"+result);
		LOG.debug("-------------------------------------");		
		
		Gson gson=new Gson();
		Message getMessage =gson.fromJson(result, Message.class);
		
		LOG.debug("-------------------------------------");
		LOG.debug("getMessage:"+getMessage);
		LOG.debug("-------------------------------------");	
		
		String resultMsg = "";
		
		resultMsg = user01.getName()+"님\n등록 성공.";
		
		Message message=new Message();
		message.setMsgId("1");
		message.setMsgContents(resultMsg);
		
		
		assertThat(getMessage.getMsgId(), is(message.getMsgId()));
		assertThat(getMessage.getMsgContents(), is(message.getMsgContents()));
		
		
		return Integer.parseInt(getMessage.getMsgId());
	}

	@Test
	@Ignore
	public void beans() {
		LOG.debug("webApplicationContext:" + webApplicationContext);
		LOG.debug("mockMvc:" + mockMvc);
		assertThat(webApplicationContext, is(notNullValue()));
		assertThat(mockMvc, is(notNullValue()));
	}
}
