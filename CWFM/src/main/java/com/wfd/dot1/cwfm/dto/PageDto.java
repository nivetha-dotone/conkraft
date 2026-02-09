package com.wfd.dot1.cwfm.dto;

public class PageDto {
    private Long pageId;
    private String pageName;
    private String pageUrl;
    private boolean accessibleForRole;
	public Long getPageId() {
		return pageId;
	}
	public void setPageId(Long pageId) {
		this.pageId = pageId;
	}
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getPageUrl() {
		return pageUrl;
	}
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	 public boolean isAccessibleForRole() {
	        return accessibleForRole;
	    }

	    public void setAccessibleForRole(boolean accessibleForRole) {
	        this.accessibleForRole = accessibleForRole;
	    }

    // Getters and Setters
}
