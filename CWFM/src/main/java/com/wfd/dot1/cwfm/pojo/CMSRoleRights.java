package com.wfd.dot1.cwfm.pojo;

import java.time.LocalDateTime;

public class CMSRoleRights {

	    private Long roleRightId;

	    private Long roleId;

	    private Long pageId;
	    public Long getPageId() {
	        return pageId;
	    }

	    public void setPageId(Long pageId) {
	        this.pageId = pageId;
	    }
	    private CmsGeneralMaster role;

	    public CmsGeneralMaster getRole() {
	        return role;
	    }

	    public void setRole(CmsGeneralMaster role) {
	        this.role = role;
	    }
	    private CmsGeneralMaster page;

	    private Integer addRights;

	    private Integer editRights;

	    private Integer deleteRights;

	    private Integer importRights;

	    private Integer exportRights;

	    private Integer viewRights;

	    private Integer listRights;

	    private Integer enabledFlag;

	    private Integer deletedFlag;

	    private Long createdBy;

	    private LocalDateTime creationDate;

	    private Long lastUpdatedBy;

	    private LocalDateTime lastUpdatedDate;


	    public Long getRoleRightId() { return roleRightId; }
	    public void setRoleRightId(Long roleRightId) { this.roleRightId = roleRightId; }
	    public Long getRoleId() { return roleId; }
	    public void setRoleId(Long roleId) { this.roleId = roleId; }
	    public CmsGeneralMaster getPage() { return page; }
	    public void setPage(CmsGeneralMaster page) { this.page = page; }

	    // Rights getters and setters
	    public Integer getAddRights() { return addRights; }
	    public void setAddRights(Integer addRights) { this.addRights = addRights; }
	    public Integer getEditRights() { return editRights; }
	    public void setEditRights(Integer editRights) { this.editRights = editRights; }
	    public Integer getDeleteRights() { return deleteRights; }
	    public void setDeleteRights(Integer deleteRights) { this.deleteRights = deleteRights; }
	    public Integer getImportRights() { return importRights; }
	    public void setImportRights(Integer importRights) { this.importRights = importRights; }
	    public Integer getExportRights() { return exportRights; }
	    public void setExportRights(Integer exportRights) { this.exportRights = exportRights; }
	    public Integer getViewRights() { return viewRights; }
	    public void setViewRights(Integer viewRights) { this.viewRights = viewRights; }
	    public Integer getListRights() { return listRights; }
	    public void setListRights(Integer listRights) { this.listRights = listRights; }
	    public Integer getEnabledFlag() { return enabledFlag; }
	    public void setEnabledFlag(Integer enabledFlag) { this.enabledFlag = enabledFlag; }

	    public Integer getDeletedFlag() { return deletedFlag; }
	    public void setDeletedFlag(Integer deletedFlag) { this.deletedFlag = deletedFlag; }

	    public Long getCreatedBy() { return createdBy; }
	    public void setCreatedBy(Long createdBy) { this.createdBy = createdBy; }
	    public LocalDateTime getCreationDate() { return creationDate; }
	    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
	    public Long getLastUpdatedBy() { return lastUpdatedBy; }
	    public void setLastUpdatedBy(Long lastUpdatedBy) { this.lastUpdatedBy = lastUpdatedBy; }
	    public LocalDateTime getLastUpdatedDate() { return lastUpdatedDate; }
	    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
	

}
