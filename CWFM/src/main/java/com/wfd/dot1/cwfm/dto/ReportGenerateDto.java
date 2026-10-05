package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class ReportGenerateDto {

	    private Long requestId;
	    private List<String> gatePassIds;

	    public ReportGenerateDto() {
	    }

	    public ReportGenerateDto(Long requestId, List<String> gatePassIds) {
	        this.requestId = requestId;
	        this.gatePassIds = gatePassIds;
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
}
