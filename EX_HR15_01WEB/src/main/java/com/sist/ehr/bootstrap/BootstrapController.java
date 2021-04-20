package com.sist.ehr.bootstrap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BootstrapController {

	final Logger  LOG = LoggerFactory.getLogger(BootstrapController.class);
	
	@RequestMapping(value = "bootstrap/boot01.do")
	public String boot01() {
		LOG.debug("====================");
		LOG.debug("=boot01()=");
		LOG.debug("====================");
		
		return "bootstrap/boot01";   
	}

	@RequestMapping(value = "bootstrap/bootform.do")
	public String bootform() {
		LOG.debug("====================");
		LOG.debug("=boot01()=");
		LOG.debug("====================");
		
		return "bootstrap/bootform";
	}	
	
	@RequestMapping(value = "bootstrap/bootlist.do")
	public String bootlist() {
		LOG.debug("====================");
		LOG.debug("=boot01()=");
		LOG.debug("====================");
		
		return "bootstrap/bootlist";
	}	
}
