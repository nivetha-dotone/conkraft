package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class SaveMappingRequest {
	  private Long sectionId;
	    private List<Long> pageIds;
		public Long getSectionId() {
			return sectionId;
		}
		public void setSectionId(Long sectionId) {
			this.sectionId = sectionId;
		}
		public List<Long> getPageIds() {
			return pageIds;
		}
		public void setPageIds(List<Long> pageIds) {
			this.pageIds = pageIds;
		}
}
