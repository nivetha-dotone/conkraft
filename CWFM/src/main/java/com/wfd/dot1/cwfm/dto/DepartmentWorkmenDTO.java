package com.wfd.dot1.cwfm.dto;

public class DepartmentWorkmenDTO {

    private String contractorName;
    private String departmentName;
    private int workmenCount;

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public int getWorkmenCount() {
        return workmenCount;
    }

    public void setWorkmenCount(int workmenCount) {
        this.workmenCount = workmenCount;
    }
}