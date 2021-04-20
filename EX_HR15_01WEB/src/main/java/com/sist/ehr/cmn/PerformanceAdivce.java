package com.sist.ehr.cmn;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

public class PerformanceAdivce {

	final Logger LOG = LoggerFactory.getLogger(this.getClass());

	public Object performanceLog(ProceedingJoinPoint pjp) throws Throwable {
		Signature  signature  =pjp.getSignature();
		//LOG.debug("signature.getName():"+signature.getName());
		
		// Before 수행전에 시간
		StopWatch  stopWatch=new StopWatch();
		stopWatch.start();
		// 메소드
		Object obj = pjp.proceed();
		// After 수행후 시간
		stopWatch.stop();
		
		LOG.debug(signature.getName()+": 메소드 수행 시간!"+stopWatch.getTotalTimeMillis()+"(ms)초");
		return obj;

	}

}
