ALTER TABLE COM_CODE
	DROP
		PRIMARY KEY
		CASCADE
		KEEP INDEX;

DROP INDEX PK_COM_CODE;

/* 공토코드 */
DROP TABLE COM_CODE 
	CASCADE CONSTRAINTS;

/* 공토코드 */
CREATE TABLE COM_CODE (
	MST_CODE VARCHAR2(30 BYTE) NOT NULL, /* 마스터코드 */
	DET_CODE VARCHAR2(5 BYTE) NOT NULL, /* 상세코드 */
	MST_NM VARCHAR2(200 CHAR) NOT NULL, /* 마스터명 */
	DET_NM VARCHAR2(200 CHAR) NOT NULL, /* 상세명 */
	SEQ NUMBER(3,0) DEFAULT 0 NOT NULL, /* 순서 */
	USE_YN CHAR(1 BYTE) DEFAULT '0' NOT NULL, /* 사용여부 */
	REG_DT DATE DEFAULT SYSDATE, /* 등록일 */
	REG_ID VARCHAR2(20 BYTE), /* 등록자 */
	MOD_DT DATE DEFAULT SYSDATE, /* 수정일 */
	MOD_ID VARCHAR2(20 BYTE) /* 수정자 */
);

COMMENT ON TABLE COM_CODE IS '공토코드';

COMMENT ON COLUMN COM_CODE.MST_CODE IS '마스터코드';

COMMENT ON COLUMN COM_CODE.DET_CODE IS '상세코드';

COMMENT ON COLUMN COM_CODE.MST_NM IS '마스터명';

COMMENT ON COLUMN COM_CODE.DET_NM IS '상세명';

COMMENT ON COLUMN COM_CODE.SEQ IS '순서';

COMMENT ON COLUMN COM_CODE.USE_YN IS '사용여부';

COMMENT ON COLUMN COM_CODE.REG_DT IS '등록일';

COMMENT ON COLUMN COM_CODE.REG_ID IS '등록자';

COMMENT ON COLUMN COM_CODE.MOD_DT IS '수정일';

COMMENT ON COLUMN COM_CODE.MOD_ID IS '수정자';

CREATE UNIQUE INDEX PK_COM_CODE
	ON COM_CODE (
		MST_CODE ASC,
		DET_CODE ASC
	);

ALTER TABLE COM_CODE
	ADD
		CONSTRAINT PK_COM_CODE
		PRIMARY KEY (
			MST_CODE,
			DET_CODE
		);


--검색조건 : MEMBER_SEARCH
--'MEMBER_SEARCH','10' ,'회원검색','이름',1,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN'
--'MEMBER_SEARCH','20' ,'회원검색','아이디',2,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN'
--'MEMBER_SEARCH','30' ,'회원검색','이메일',1,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN'
INSERT INTO com_code (mst_code,det_code,mst_nm,det_nm,seq,use_yn,reg_dt,reg_id,mod_dt,mod_id ) VALUES ('MEMBER_SEARCH','10' ,'회원검색','이름',1,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN' );
INSERT INTO com_code (mst_code,det_code,mst_nm,det_nm,seq,use_yn,reg_dt,reg_id,mod_dt,mod_id ) VALUES ('MEMBER_SEARCH','20' ,'회원검색','아이디',2,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN' );
INSERT INTO com_code (mst_code,det_code,mst_nm,det_nm,seq,use_yn,reg_dt,reg_id,mod_dt,mod_id ) VALUES ('MEMBER_SEARCH','30' ,'회원검색','이메일',1,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN' );
commit
--페이사이즈: 
--'COM_PAGE_SIZE','10' ,'페이지 사이즈','10',1,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN'
--'COM_PAGE_SIZE','20' ,'페이지 사이즈','20',2,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN'
--'COM_PAGE_SIZE','30' ,'페이지 사이즈','30',3,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN'
--'COM_PAGE_SIZE','50' ,'페이지 사이즈','50',4,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN'
--'COM_PAGE_SIZE','100','페이지 사이즈','100',5,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN'


INSERT INTO com_code (mst_code,det_code,mst_nm,det_nm,seq,use_yn,reg_dt,reg_id,mod_dt,mod_id ) VALUES (  'COM_PAGE_SIZE','10','페이지 사이즈','10',1,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN' );        
INSERT INTO com_code (mst_code,det_code,mst_nm,det_nm,seq,use_yn,reg_dt,reg_id,mod_dt,mod_id ) VALUES (  'COM_PAGE_SIZE','20' ,'페이지 사이즈','20',2,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN' );   
INSERT INTO com_code (mst_code,det_code,mst_nm,det_nm,seq,use_yn,reg_dt,reg_id,mod_dt,mod_id ) VALUES (  'COM_PAGE_SIZE','30' ,'페이지 사이즈','30',3,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN' );   
INSERT INTO com_code (mst_code,det_code,mst_nm,det_nm,seq,use_yn,reg_dt,reg_id,mod_dt,mod_id ) VALUES (  'COM_PAGE_SIZE','50' ,'페이지 사이즈','50',4,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN' );   
INSERT INTO com_code (mst_code,det_code,mst_nm,det_nm,seq,use_yn,reg_dt,reg_id,mod_dt,mod_id ) VALUES (  'COM_PAGE_SIZE','100','페이지 사이즈','100',5,'1',SYSDATE,'ADMIN',SYSDATE,'ADMIN' );   
commit