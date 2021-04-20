package com.sist.ehr.code.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface CodeService {

	public List<?> getCodeRetrieve(Map<String,Object> code) 
			throws SQLException;
}
