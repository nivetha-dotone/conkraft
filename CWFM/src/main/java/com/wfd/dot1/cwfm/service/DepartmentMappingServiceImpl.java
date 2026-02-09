package com.wfd.dot1.cwfm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.DepartmentMappingDao;
import com.wfd.dot1.cwfm.pojo.DeptMapping;

@Service
public class DepartmentMappingServiceImpl implements DepartmentMappingService {

	@Autowired
	DepartmentMappingDao deptMapDao;

	@Override
	public void saveMappings(List<DeptMapping> mappings) {
	    for (DeptMapping mapping : mappings) {

	    	 int subDeptId = mapping.getSubDepartmentId(); // primitive int

	         // Case 1: Principal Employer + Department (no sub-department)
	      //   if (subDeptId == 0) {
	       //     boolean exists = deptMapDao.existsMapping(
	       //             mapping.getPrincipalEmployerId(),
	       //             mapping.getDepartmentId()
	       //     );
	       //     if (exists) {
	       //         String principalEmployerName = deptMapDao.getPrincipalEmployerNameById(mapping.getPrincipalEmployerId());
	       //         String departmentName = deptMapDao.getDepartmentNameById(mapping.getDepartmentId());

	       //         throw new RuntimeException("Mapping already exists for Principal Employer: "
	         //               + principalEmployerName + " and Department: " + departmentName);
	        //    }
	      //  }

	        // Case 2: Principal Employer + Department + SubDepartment
	       // else {
	            boolean trioExists = deptMapDao.trioexistsMapping(
	                    mapping.getPrincipalEmployerId(),
	                    mapping.getDepartmentId(),
	                    mapping.getSubDepartmentId()
	            );
	            if (trioExists) {
	                String principalEmployerName = deptMapDao.getPrincipalEmployerNameById(mapping.getPrincipalEmployerId());
	                String departmentName = deptMapDao.getDepartmentNameById(mapping.getDepartmentId());
	                String subDepartmentName = deptMapDao.getSubDepartmentNameById(mapping.getSubDepartmentId());

	                throw new RuntimeException("Mapping already exists for Principal Employer: "
	                        + principalEmployerName + ", Department: " + departmentName
	                        + " and SubDepartment: " + subDepartmentName);
	            }
	        }
	    //}

	    // ✅ Save only if no duplicates were found
	    deptMapDao.save(mappings);
	}
	
	 @Override
	    public List<DeptMapping> getAllMappings() {
	        return deptMapDao.findAll();
	    }
	 
	 @Override
	    public List<DeptMapping> getAllTradeSkillMappings() {
	        return deptMapDao.findAllTradeSkillMappings();
	    }
	 @Override
	    public void saveTradeSkillMappings(List<DeptMapping> mappings) {
	        for (DeptMapping mapping : mappings) {
	            boolean exists = deptMapDao.existsTradeSkillMapping(mapping.getPrincipalEmployerId(), mapping.getTradeId(),mapping.getSkillId());
	            if (exists) {
	            	 // fetch names from DAO
	                String principalEmployerName = deptMapDao.getPrincipalEmployerNameById(mapping.getPrincipalEmployerId());
	                String tradeName = deptMapDao.getTradeNameById(mapping.getTradeId());
	                String skillName = deptMapDao.getTradeNameById(mapping.getSkillId());
	                
	                throw new RuntimeException("Mapping already exists for Principal Employer: " 
	                        + principalEmployerName + " , Trade: " + tradeName+ " and Skill: "+skillName);
	             }
	        }
	        // If no duplicates, save all
	        deptMapDao.saveTradeSkillMappings(mappings);
	    }

	    @Override
	    public Map<String, List<DeptMapping>> deleteDeptMappingIfExistsInGatePass(List<DeptMapping> mappings) {
	        
	        List<DeptMapping> deleted = new ArrayList<>();
	        List<DeptMapping> skipped = new ArrayList<>();

	        for (DeptMapping mapping : mappings) {
	            boolean dependencyExists = deptMapDao.DeptexistsInGatePass(mapping);

	            if (!dependencyExists) {
	                // ✅ Safe to delete if no dependency exists
	                deptMapDao.deleteDeptMapping(mapping);
	                deleted.add(mapping);
	            } else {
	                // ❌ Dependency found → skip deletion
	            	 skipped.add(mapping);
	            }
	        }
	        Map<String, List<DeptMapping>> result = new HashMap<>();
	        result.put("deleted", deleted);
	        result.put("skipped", skipped);
	        return result;
	    }
	    
	    @Override
	    public  Map<String, List<DeptMapping>>  deleteTradeMappingIfExistsInGatePass(List<DeptMapping> mappings) {
	       
	        List<DeptMapping> deleted = new ArrayList<>();
	        List<DeptMapping> skipped = new ArrayList<>();

	        for (DeptMapping mapping : mappings) {
	            boolean dependencyExists = deptMapDao.TradeexistsInGatePass(mapping);

	            if (!dependencyExists) {
	                // ✅ Safe to delete if no dependency exists
	                deptMapDao.deleteTradeMapping(mapping);
	                deleted.add(mapping);
	            } else {
	                // ❌ Dependency found → skip deletion
	            	 skipped.add(mapping);
	            }
	        }

	        Map<String, List<DeptMapping>> result = new HashMap<>();
	        result.put("deleted", deleted);
	        result.put("skipped", skipped);
	        return result;
	    }
}
