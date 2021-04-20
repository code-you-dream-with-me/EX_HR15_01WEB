package com.sist.ehr.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestUserServiceException extends RuntimeException {

	final Logger LOG = LoggerFactory.getLogger(TestUserServiceException.class);
	
	public TestUserServiceException() {}
	
	public TestUserServiceException(String msg) {
		super(msg);
		LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		LOG.debug(">TestUserServiceException>"+msg);
		LOG.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	}
	
	
	
}
