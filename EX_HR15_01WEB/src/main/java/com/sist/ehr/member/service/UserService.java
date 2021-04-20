package com.sist.ehr.member.service;

import java.sql.SQLException;
import java.util.List;

import com.sist.ehr.cmn.DTO;

public interface UserService {

	/**
	 * 목록조회
	 * @param dto
	 * @return List<?>
	 * @throws SQLException
	 */
	List<?> doRetrieve(DTO dto)throws SQLException;
	
	/**
	 * 단건조회
	 * @param DTO
	 * @return DTO
	 * @throws SQLException
	 */
	DTO doSelectOne(DTO dto) throws SQLException;
	
	/**
	 * 수정
	 * @param DTO
	 * @return int(1:성공,0:실패)
	 * @throws SQLException
	 */
	int  doUpdate(DTO dto)throws SQLException;
	
	/**
	 * 삭제
	 * @param DTO
	 * @return int(1:성공,0:실패)
	 * @throws SQLException
	 */
	int doDelete(DTO dto) throws  SQLException;
	
	/**
	 * 최초 회원 가입시 등급 : BASIC
	 * Level이 Null이면 Basic 처리
	 * @param dto
	 * @return
	 * @throws SQLException
	 */
	int add(DTO dto) throws SQLException;

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
	void upgradeLevels() throws SQLException, IllegalAccessException;

}