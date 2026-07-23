package com.wfd.dot1.cwfm.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wfd.dot1.cwfm.dao.DashboardDao;
import com.wfd.dot1.cwfm.dto.DashboardDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.pojo.MasterUser;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private DashboardDao dao;

    @Autowired
    private CommonService commonService;

    @Override
    public DashboardDTO getDashboard(MasterUser user) {

        List<PersonOrgLevel> orgLevel = commonService.getPersonOrgLevelDetails(user.getUserAccount());

        Map<String, List<PersonOrgLevel>> groupedByLevelDef = orgLevel.stream()
                .collect(Collectors.groupingBy(PersonOrgLevel::getLevelDef));

        List<PersonOrgLevel> contList = groupedByLevelDef.getOrDefault("Contractor", new ArrayList<>());
        List<PersonOrgLevel> peList = groupedByLevelDef.getOrDefault("Principal Employer", new ArrayList<>());

        String userId = String.valueOf(user.getUserId());
        String roleName = user.getRoleName();

        DashboardDTO dto = dao.getDashboardData(userId, roleName, peList, contList);
        dto.setRoleName(roleName);

        return dto;
    }

    @Override
    public List<WorkOrderDTO> getWorkmenByWO(long woId, String contractorId) {
        return dao.getWorkmenByWO(woId, contractorId);
    }
    @Override
    public List<String> getAllowedPagesForRole(String roleId){
        return dao.getAllowedPagesForRole(roleId);
    }
}