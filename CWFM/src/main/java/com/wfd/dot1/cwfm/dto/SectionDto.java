package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class SectionDto {
    private Long sectionId;
    private String sectionName;
    private List<PageDto> pages;
    private String sectionIcon;
    public String getSectionIcon() {
        return sectionIcon;
    }

    public void setSectionIcon(String sectionIcon) {
        this.sectionIcon = sectionIcon;
    }
	public Long getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public List<PageDto> getPages() {
		return pages;
	}
	public void setPages(List<PageDto> pages) {
		this.pages = pages;
	}

    // Getters and Setters
}

