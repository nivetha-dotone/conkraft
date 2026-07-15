package com.wfd.dot1.cwfm.dto;

public class BillStatusDTO {

	 private int approvedCount;
	    private int rejectedCount;
	    private int pendingCount;
	    private int totalCount;
	    private int approvedPercent;
	    private int rejectedPercent;
	    private int pendingPercent;
		public int getApprovedCount() {
			return approvedCount;
		}
		public void setApprovedCount(int approvedCount) {
			this.approvedCount = approvedCount;
		}
		public int getRejectedCount() {
			return rejectedCount;
		}
		public void setRejectedCount(int rejectedCount) {
			this.rejectedCount = rejectedCount;
		}
		public int getPendingCount() {
			return pendingCount;
		}
		public void setPendingCount(int pendingCount) {
			this.pendingCount = pendingCount;
		}
		public int getTotalCount() {
			return totalCount;
		}
		public void setTotalCount(int totalCount) {
			this.totalCount = totalCount;
		}
		public int getApprovedPercent() {
			return approvedPercent;
		}
		public void setApprovedPercent(int approvedPercent) {
			this.approvedPercent = approvedPercent;
		}
		public int getRejectedPercent() {
			return rejectedPercent;
		}
		public void setRejectedPercent(int rejectedPercent) {
			this.rejectedPercent = rejectedPercent;
		}
		public int getPendingPercent() {
			return pendingPercent;
		}
		public void setPendingPercent(int pendingPercent) {
			this.pendingPercent = pendingPercent;
		}
	    
}
