package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class ReportGenerateDto {

	    private Long requestId;
	    private List<String> gatePassIds;

	    public ReportGenerateDto() {
	    	
	    }

	    public ReportGenerateDto(Long requestId, Long reportParamId,List<String> gatePassIds) {
	        this.requestId = requestId;
	        this.gatePassIds = gatePassIds;
	        this.reportParamId=reportParamId;
	    }

	    public Long getRequestId() {
	        return requestId;
	    }

	    public void setRequestId(Long requestId) {
	        this.requestId = requestId;
	    }

	    public List<String> getGatePassIds() {
	        return gatePassIds;
	    }

	    public void setGatePassIds(List<String> gatePassIds) {
	        this.gatePassIds = gatePassIds;
	    }
	    
	    private String reportType;
	    private String fromDate;
	    private String toDate;
	    private String reportStatus;
	    private String reportId;
	    private Long reportParamId;
	    public String getReportType() {
			return reportType;
		}

		public void setReportType(String reportType) {
			this.reportType = reportType;
		}

		public String getFromDate() {
			return fromDate;
		}

		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}

		public String getToDate() {
			return toDate;
		}

		public void setToDate(String toDate) {
			this.toDate = toDate;
		}

		public String getReportStatus() {
			return reportStatus;
		}

		public void setReportStatus(String reportStatus) {
			this.reportStatus = reportStatus;
		}

		public ReportGenerateDto(
	            Long requestId,
	            String reportType,
	            String fromDate,
	            String toDate,
	            String reportStatus) {

	        this.requestId = requestId;
	        this.reportType = reportType;
	        this.fromDate = fromDate;
	        this.toDate = toDate;
	        this.reportStatus = reportStatus;
	    }

		public String getReportId() {
			return reportId;
		}

		public void setReportId(String reportId) {
			this.reportId = reportId;
		}

		public Long getReportParamId() {
			return reportParamId;
		}

		public void setReportParamId(Long reportParamId) {
			this.reportParamId = reportParamId;
		}
		 private String outPutFileName;

		public String getOutPutFileName() {
			return outPutFileName;
		}

		public void setOutPutFileName(String outPutFileName) {
			this.outPutFileName = outPutFileName;
		}

}
