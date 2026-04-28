package com.wfd.dot1.cwfm.dao;

import java.util.List;

import com.wfd.dot1.cwfm.dto.DashboardDTO;
import com.wfd.dot1.cwfm.dto.WorkOrderDTO;
import com.wfd.dot1.cwfm.pojo.PersonOrgLevel;

public interface DashboardDao {

    DashboardDTO getDashboardData(String userId, String roleName,
                                  List<PersonOrgLevel> peList,
                                  List<PersonOrgLevel> contList);

    List<WorkOrderDTO> getWorkmenByWO(long woId, String contractorId);
}