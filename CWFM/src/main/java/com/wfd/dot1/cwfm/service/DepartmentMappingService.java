package com.wfd.dot1.cwfm.service;

import java.util.List;
import java.util.Map;

import com.wfd.dot1.cwfm.pojo.DeptMapping;

public interface DepartmentMappingService {

	void saveMappings(List<DeptMapping> mappings);
	
	List<DeptMapping> getAllMappings();

	List<DeptMapping> getAllTradeSkillMappings();

	void saveTradeSkillMappings(List<DeptMapping> mappings);
	
	
	
	 Map<String, List<DeptMapping>>  deleteDeptMappingIfExistsInGatePass(List<DeptMapping> mappings);

	Map<String, List<DeptMapping>> deleteTradeMappingIfExistsInGatePass(List<DeptMapping> mappings);
}
