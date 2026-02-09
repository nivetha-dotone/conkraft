package com.wfd.dot1.cwfm.dto;

import java.util.List;

import com.wfd.dot1.cwfm.pojo.Contractor;
import com.wfd.dot1.cwfm.pojo.PrincipalEmployer;
import com.wfd.dot1.cwfm.pojo.Workorder;

public class WorkorderResponseDto {
	
	 private List<Workorder> woList;
	    private Contractor contractor;
	    private PrincipalEmployer principalEmployer;
		public List<Workorder> getWoList() {
			return woList;
		}
		public void setWoList(List<Workorder> woList) {
			this.woList = woList;
		}
		public Contractor getContractor() {
			return contractor;
		}
		public void setContractor(Contractor contractor) {
			this.contractor = contractor;
		}
		public PrincipalEmployer getPrincipalEmployer() {
			return principalEmployer;
		}
		public void setPrincipalEmployer(PrincipalEmployer principalEmployer) {
			this.principalEmployer = principalEmployer;
		}

}
