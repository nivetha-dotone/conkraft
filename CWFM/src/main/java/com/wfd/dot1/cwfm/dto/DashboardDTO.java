package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class DashboardDTO {

    private int activeWorkmen;
    private int activeWO;
    private int activeLL;
    private int activeWC;
    private int activeESIC;
    private int pendingRequests;

    private String roleName;

    private List<PlantWorkmenDTO> plantWorkmenList;
    private List<PlantWorkmenDTO> plantContrWorkmenList;
    private List<ExpiryDTO> expiryList;
    private List<WorkOrderDTO> activeWOList;

    private List<ContractorWorkmenDTO> contractorWorkmenList;
    private List<DepartmentWorkmenDTO> contractorDeptWorkmenList;
    private List<PvcTypeDTO> pvcTypeList;
    private List<AckExpiryDTO> ackExpiryList;
    private List<BusinessTypePEDTO> businessTypePEList;
    private List<PEContractorDTO> peContractorList;

    public int getActiveWorkmen() {
        return activeWorkmen;
    }

    public void setActiveWorkmen(int activeWorkmen) {
        this.activeWorkmen = activeWorkmen;
    }

    public int getActiveWO() {
        return activeWO;
    }

    public void setActiveWO(int activeWO) {
        this.activeWO = activeWO;
    }

    public int getActiveLL() {
        return activeLL;
    }

    public void setActiveLL(int activeLL) {
        this.activeLL = activeLL;
    }

    public int getActiveWC() {
        return activeWC;
    }

    public void setActiveWC(int activeWC) {
        this.activeWC = activeWC;
    }

    public int getActiveESIC() {
        return activeESIC;
    }

    public void setActiveESIC(int activeESIC) {
        this.activeESIC = activeESIC;
    }

    public int getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(int pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<PlantWorkmenDTO> getPlantWorkmenList() {
        return plantWorkmenList;
    }

    public void setPlantWorkmenList(List<PlantWorkmenDTO> plantWorkmenList) {
        this.plantWorkmenList = plantWorkmenList;
    }

    public List<PlantWorkmenDTO> getPlantContrWorkmenList() {
        return plantContrWorkmenList;
    }

    public void setPlantContrWorkmenList(List<PlantWorkmenDTO> plantContrWorkmenList) {
        this.plantContrWorkmenList = plantContrWorkmenList;
    }

    public List<ExpiryDTO> getExpiryList() {
        return expiryList;
    }

    public void setExpiryList(List<ExpiryDTO> expiryList) {
        this.expiryList = expiryList;
    }

    public List<WorkOrderDTO> getActiveWOList() {
        return activeWOList;
    }

    public void setActiveWOList(List<WorkOrderDTO> activeWOList) {
        this.activeWOList = activeWOList;
    }

    public List<ContractorWorkmenDTO> getContractorWorkmenList() {
        return contractorWorkmenList;
    }

    public void setContractorWorkmenList(List<ContractorWorkmenDTO> contractorWorkmenList) {
        this.contractorWorkmenList = contractorWorkmenList;
    }

    public List<DepartmentWorkmenDTO> getContractorDeptWorkmenList() {
        return contractorDeptWorkmenList;
    }

    public void setContractorDeptWorkmenList(List<DepartmentWorkmenDTO> contractorDeptWorkmenList) {
        this.contractorDeptWorkmenList = contractorDeptWorkmenList;
    }

    public List<PvcTypeDTO> getPvcTypeList() {
        return pvcTypeList;
    }

    public void setPvcTypeList(List<PvcTypeDTO> pvcTypeList) {
        this.pvcTypeList = pvcTypeList;
    }

    public List<AckExpiryDTO> getAckExpiryList() {
        return ackExpiryList;
    }

    public void setAckExpiryList(List<AckExpiryDTO> ackExpiryList) {
        this.ackExpiryList = ackExpiryList;
    }

    public List<BusinessTypePEDTO> getBusinessTypePEList() {
        return businessTypePEList;
    }

    public void setBusinessTypePEList(List<BusinessTypePEDTO> businessTypePEList) {
        this.businessTypePEList = businessTypePEList;
    }

    public List<PEContractorDTO> getPeContractorList() {
        return peContractorList;
    }

    public void setPeContractorList(List<PEContractorDTO> peContractorList) {
        this.peContractorList = peContractorList;
    }
    
    public int getExpiredworkorders() {
		return expiredworkorders;
	}

	public void setExpiredworkorders(int expiredworkorders) {
		this.expiredworkorders = expiredworkorders;
	}

	private int expiredworkorders;
	private int expiredLicensess;

	public int getExpiredLicensess() {
		return expiredLicensess;
	}

	public void setExpiredLicensess(int expiredLicensess) {
		this.expiredLicensess = expiredLicensess;
	}
	private int expiredgatepasses;

	public int getExpiredgatepasses() {
		return expiredgatepasses;
	}

	public void setExpiredgatepasses(int expiredgatepasses) {
		this.expiredgatepasses = expiredgatepasses;
	}
	private int blackliestedGP;

	public int getBlackliestedGP() {
		return blackliestedGP;
	}

	public void setBlackliestedGP(int blackliestedGP) {
		this.blackliestedGP = blackliestedGP;
	}
	private int pendingBills;

	public int getPendingBills() {
		return pendingBills;
	}

	public void setPendingBills(int pendingBills) {
		this.pendingBills = pendingBills;
	}
	
	public List<WorkorderAlertsDTO> getWorkorderalertsList() {
		return workorderalertsList;
	}

	public void setWorkorderalertsList(List<WorkorderAlertsDTO> workorderalertsList) {
		this.workorderalertsList = workorderalertsList;
	}

	private List<WorkordersDTO> workorderList;
	private List<WorkorderAlertsDTO> workorderalertsList;

	public List<WorkordersDTO> getWorkorderList() {
		return workorderList;
	}

	public void setWorkorderList(List<WorkordersDTO> workorderList) {
		this.workorderList = workorderList;
	}
	private List<LicensesDTO> LicenseList;
	
	private List<LLLicensesDTO> LLLicenseList;

	public List<LicensesDTO> getLicenseList() {
		return LicenseList;
	}

	public void setLicenseList(List<LicensesDTO> licenseList) {
		LicenseList = licenseList;
	}
	private List<WCDTO> WCList;

	public List<WCDTO> getWCList() {
		return WCList;
	}

	public void setWCList(List<WCDTO> wCList) {
		WCList = wCList;
	}
	private List<ESICDTO> ESICList;

	public List<ESICDTO> getESICList() {
		return ESICList;
	}

	public void setESICList(List<ESICDTO> eSICList) {
		ESICList = eSICList;
	}
	private List<BillStatusDTO> billList;
	public List<BillStatusDTO> getBillList() {
		return billList; 
		}
	public void setBillList(List<BillStatusDTO> billList) { 
		this.billList = billList;
		}

	public List<LLLicensesDTO> getLLLicenseList() {
		return LLLicenseList;
	}

	public void setLLLicenseList(List<LLLicensesDTO> lLLicenseList) {
		LLLicenseList = lLLicenseList;
	}
	private List<GatepassExpiryDTO> gatepassExpiryList;

	public List<GatepassExpiryDTO> getGatepassExpiryList() {
		return gatepassExpiryList;
	}

	public void setGatepassExpiryList(List<GatepassExpiryDTO> gatepassExpiryList) {
		this.gatepassExpiryList = gatepassExpiryList;
	}
	public List<WorkmensReachedRetiredAgeDTO> getWorkmensReachedRetiredAgeList() {
		return WorkmensReachedRetiredAgeList;
	}

	public void setWorkmensReachedRetiredAgeList(List<WorkmensReachedRetiredAgeDTO> workmensReachedRetiredAgeList) {
		WorkmensReachedRetiredAgeList = workmensReachedRetiredAgeList;
	}

	private List<WorkmensReachedRetiredAgeDTO> WorkmensReachedRetiredAgeList;
	
}