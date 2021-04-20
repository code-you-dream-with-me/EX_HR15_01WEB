package com.sist.ehr.member.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.sist.ehr.cmn.DTO;
import com.sist.ehr.cmn.Message;
import com.sist.ehr.cmn.Search;
import com.sist.ehr.cmn.StringUtil;
import com.sist.ehr.code.domain.Code;
import com.sist.ehr.code.service.CodeService;
import com.sist.ehr.member.domain.User;
import com.sist.ehr.member.service.UserService;

@Controller
public class UserController {

	final Logger  LOG = LoggerFactory.getLogger(UserController.class);
	final String  VIEW_NAME = "user/user_mng";
	@Autowired
	UserService  userService;
	
	
	@Autowired
	CodeService  codeService;
	
	public UserController() {
		
	}
	
	@RequestMapping(value="user/user_view.do",method = RequestMethod.GET)
	public String view(Model model) throws SQLException {
		
		List codeListParam = new ArrayList<String>();
		codeListParam.add("MEMBER_SEARCH");
		codeListParam.add("COM_PAGE_SIZE");
		List<Code> codeList = getCodePageRetrieve(codeListParam);
		
		
		List<Code> comPageSizeList = new ArrayList<Code>();
		List<Code> memberSearchList = new ArrayList<Code>();
		
		
		for(Code vo  :codeList) {
			
			if(vo.getMstCode().equals("COM_PAGE_SIZE")) {
				comPageSizeList.add(vo);
			}
			
			if(vo.getMstCode().equals("MEMBER_SEARCH")) {
				memberSearchList.add(vo);
			}			
		}
		//LOG.debug(comPageSizeList.toString());   
		//페이지 사이즈 코드
        model.addAttribute("COM_PAGE_SIZE", comPageSizeList);
        model.addAttribute("MEMBER_SEARCH", memberSearchList);
		
		return VIEW_NAME;
	}

	
	//View(jsp)에서 사용할 데이터를 설정하는 용도로도 사용 가능
	
	private List<?> getCodePageRetrieve(List<String> codeList) throws SQLException {
		Map<String,Object>  codeMap =new HashMap<String,Object>();
		codeMap.put("codeList",codeList);
		
		return codeService.getCodeRetrieve(codeMap);
	}
	
	
	//1. 브라우저에서 "user/do_retrieve.do" call
	//2. @ModelAttribute("member_search") 설정된 getCodeRetrieve()가 수행
	//3. public String doRetrieve(Search search) 메소드 수행
	//View(jsp)에서 사용할 데이터를 설정하는 용도로도 사용 가능
//	@ModelAttribute("member_search")
//	public List<?> getCodeRetrieve() throws SQLException {
//		Map<String,Object>  codeMap =new HashMap<String,Object>();
//		List codeList = new ArrayList<String>();
//		codeList.add("MEMBER_SEARCH");
//		codeMap.put("codeList",codeList);
//		
//		return codeService.getCodeRetrieve(codeMap);
//	}
	
	/**
	 * 회원 목록 조회
	 * @param dto
	 * @return JSON(User)
	 * @throws RuntimeException
	 * @throws SQLException 
	 */     
	@RequestMapping(value = "user/do_retrieve.do",method = RequestMethod.GET
			,produces = "application/json;charset=UTF-8")
	@ResponseBody		
	public String doRetrieve(Search search) throws SQLException {
		LOG.debug("====================================");
		LOG.debug("param:"+search);
		LOG.debug("====================================");		
		
		//NVL처리 
		//검색구분
		search.setSearchDiv(StringUtil.nvl(search.getSearchDiv(),""));
		//검색어
		search.setSearchWord(StringUtil.nvl(search.getSearchWord(),""));
		
		//페이지 넘
		if(search.getPageNum()==0) {
			search.setPageNum(1);
		}
		
		//페이지 사이즈
		if(search.getPageSize()==0) {
			search.setPageSize(10);
		}
		   
		LOG.debug("====================================");
		LOG.debug("param_init:"+search);
		LOG.debug("====================================");			
		
		
		List<User> list = (List<User>) this.userService.doRetrieve(search);
		
		
		for(User vo:list) {
			LOG.debug(vo.toString());
		}
		
		//List to Json
		Gson gson=new Gson();
		
		String jsonList = gson.toJson(list);
		LOG.debug("====================================");
		LOG.debug("jsonList:"+jsonList);
		LOG.debug("====================================");				
		
		
		return jsonList;
	}
	
	/**
	 * 회원 단건 조회
	 * @param dto
	 * @return JSON(User)
	 * @throws RuntimeException
	 * @throws SQLException 
	 */     
	@RequestMapping(value = "user/do_selectone.do",method = RequestMethod.GET
			,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String doSelectOne(User user) throws SQLException {
		LOG.debug("====================================");
		LOG.debug("param:"+user);
		LOG.debug("====================================");
		
		User  outVO = (User) this.userService.doSelectOne(user);
		LOG.debug("====================================");
		LOG.debug("outVO:"+outVO);
		LOG.debug("====================================");		
		
		
		Gson  gson=new Gson();
		String jsonStr = gson.toJson(outVO);
		LOG.debug("====================================");
		LOG.debug("jsonStr:"+jsonStr);
		LOG.debug("====================================");		
		return jsonStr;
	}
	
	/**
	 * 회원 삭제
	 * @param dto
	 * @return JSON(1:성공,0:실패)
	 * @throws RuntimeException
	 * @throws SQLException 
	 */     
	@RequestMapping(value = "user/do_delete.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody	
	public String doDelete(User user) throws SQLException {
		LOG.debug("====================================");
		LOG.debug("param:"+user);
		LOG.debug("====================================");
		int flag = this.userService.doDelete(user);
		String resultMsg = "";
		if(1==flag) {
			resultMsg = user.getuId()+"님\n삭제 되었습니다.";
		}else {
			resultMsg = "삭제 실패.";
		}     
		
		Message message=new Message();
		message.setMsgId(flag+"");
		message.setMsgContents(resultMsg);
		
		Gson  gson=new Gson();
		String jsonStr = gson.toJson(message);
		LOG.debug("====================================");
		LOG.debug("jsonStr:"+jsonStr);
		LOG.debug("====================================");		
		return jsonStr;
	}
	
	
	/**
	 * 회원 수정
	 * @param dto
	 * @return JSON(1:성공,0:실패)
	 * @throws RuntimeException
	 * @throws SQLException 
	 */     
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "user/do_update.do",method = RequestMethod.POST
			,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody       
	public String doUpdate(User user) throws SQLException {
		LOG.debug("====================================");
		LOG.debug("param:"+user);
		LOG.debug("====================================");		
		
		int flag = this.userService.doUpdate(user);
		String resultMsg = "";
		if(1 == flag) {
			resultMsg = user.getName()+"님\n수정 성공.";
		}else {
			resultMsg = user.getName()+"님\n수정 실패.";
		}
		
		Message message=new Message();
		message.setMsgId(flag+"");
		message.setMsgContents(resultMsg);
		
		Gson  gson=new Gson();
		String jsonStr = gson.toJson(message);
		LOG.debug("====================================");
		LOG.debug("jsonStr:"+jsonStr);
		LOG.debug("====================================");		
		return jsonStr;
	}
	
	
	/**
	 * 회원 등록
	 * @param dto
	 * @return JSON(1:성공,0:실패)
	 * @throws RuntimeException
	 * @throws SQLException 
	 */     
	@RequestMapping(value = "user/do_insert.do",method = RequestMethod.POST
			,produces = "application/json;charset=UTF-8")
	@ResponseBody       
	public String doInsert(User user) throws SQLException {
		  
		LOG.debug("====================================");
		LOG.debug("param:"+user);
		LOG.debug("====================================");
		
		int flag = userService.add(user);
		String resultMsg = "";
		if(1==flag) {
			resultMsg = user.getName()+"님\n등록 성공.";
		}else {
			resultMsg = "등록 실패.";
		}       
		
		Message message=new Message();
		message.setMsgId(flag+"");
		message.setMsgContents(resultMsg);
		
		Gson  gson=new Gson();
		String jsonStr = gson.toJson(message);
		LOG.debug("====================================");
		LOG.debug("jsonStr:"+jsonStr);
		LOG.debug("====================================");		
		return jsonStr;
	}
}

