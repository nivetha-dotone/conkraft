package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class WorkmanFullHistoryDTO {
	 private List<WorkmenSummaryDto> summary;
	    private List<CurrentEmploymentDto> currentEmployment;
	    private List<PreviousEmploymentDto> previousEmployment;
	    private List<ActionAuditDto> auditTrail;
	    private List<ComplianceAuditDto> complianceAuditTrail;
		public List<ComplianceAuditDto> getComplianceAuditTrail() {
			return complianceAuditTrail;
		}
		public void setComplianceAuditTrail(List<ComplianceAuditDto> complianceAuditTrail) {
			this.complianceAuditTrail = complianceAuditTrail;
		}
		public List<WorkmenSummaryDto> getSummary() {
			return summary;
		}
		public void setSummary(List<WorkmenSummaryDto> summary) {
			this.summary = summary;
		}
		public List<CurrentEmploymentDto> getCurrentEmployment() {
			return currentEmployment;
		}
		public void setCurrentEmployment(List<CurrentEmploymentDto> currentEmployment) {
			this.currentEmployment = currentEmployment;
		}
		public List<PreviousEmploymentDto> getPreviousEmployment() {
			return previousEmployment;
		}
		public void setPreviousEmployment(List<PreviousEmploymentDto> previousEmployment) {
			this.previousEmployment = previousEmployment;
		}
		public List<ActionAuditDto> getAuditTrail() {
			return auditTrail;
		}
		public void setAuditTrail(List<ActionAuditDto> auditTrail) {
			this.auditTrail = auditTrail;
		}

	    
}


