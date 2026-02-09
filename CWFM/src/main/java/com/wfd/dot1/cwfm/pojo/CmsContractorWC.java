package com.wfd.dot1.cwfm.pojo;

public class CmsContractorWC {

	private String wcId;
	
	private String wcCode;
	
	private String contractorId;
	
	private String unitId;
	
	private int wcTotal;
	
	 private Integer natureOfId;
	    private String wcFromDtm;
	    private String wcToDtm;
	    private Integer deleteSw;
	    public Integer getNatureOfId() {
			return natureOfId;
		}

		public void setNatureOfId(Integer natureOfId) {
			this.natureOfId = natureOfId;
		}

		public String getWcFromDtm() {
			return wcFromDtm;
		}

		public void setWcFromDtm(String wcFromDtm) {
			this.wcFromDtm = wcFromDtm;
		}

		public String getWcToDtm() {
			return wcToDtm;
		}

		public void setWcToDtm(String wcToDtm) {
			this.wcToDtm = wcToDtm;
		}

		public Integer getDeleteSw() {
			return deleteSw;
		}

		public void setDeleteSw(Integer deleteSw) {
			this.deleteSw = deleteSw;
		}

		public String getLicenceType() {
			return licenceType;
		}

		public void setLicenceType(String licenceType) {
			this.licenceType = licenceType;
		}

		public String getIsVerified() {
			return isVerified;
		}

		public void setIsVerified(String isVerified) {
			this.isVerified = isVerified;
		}

		public String getAttachmentNm() {
			return attachmentNm;
		}

		public void setAttachmentNm(String attachmentNm) {
			this.attachmentNm = attachmentNm;
		}

		public int getExtendToSubcontractor() {
			return extendToSubcontractor;
		}

		public void setExtendToSubcontractor(int extendToSubcontractor) {
			this.extendToSubcontractor = extendToSubcontractor;
		}

		public String getCreatedDtm() {
			return createdDtm;
		}

		public void setCreatedDtm(String createdDtm) {
			this.createdDtm = createdDtm;
		}

		public String getUpStringdDtm() {
			return upStringdDtm;
		}

		public void setUpStringdDtm(String upStringdDtm) {
			this.upStringdDtm = upStringdDtm;
		}

		private String licenceType;
	    private String isVerified;
	    private String attachmentNm;
	    private int extendToSubcontractor;
	    private String createdDtm;
	    private String upStringdDtm;

	public String getWcId() {
		return wcId;
	}

	public void setWcId(String wcId) {
		this.wcId = wcId;
	}

	public String getWcCode() {
		return wcCode;
	}

	public void setWcCode(String wcCode) {
		this.wcCode = wcCode;
	}

	public String getContractorId() {
		return contractorId;
	}

	public void setContractorId(String contractorId) {
		this.contractorId = contractorId;
	}

	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	public int getWcTotal() {
		return wcTotal;
	}

	public void setWcTotal(int wcTotal) {
		this.wcTotal = wcTotal;
	}
	private int activeWorkmenCount;
	
	public int getActiveWorkmenCount() {
		return activeWorkmenCount;
	}

	public void setActiveWorkmenCount(int activeCount) {
		this.activeWorkmenCount = activeCount;
	}
	
}
