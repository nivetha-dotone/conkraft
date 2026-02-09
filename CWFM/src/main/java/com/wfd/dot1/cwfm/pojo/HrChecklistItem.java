package com.wfd.dot1.cwfm.pojo;


	
	public class HrChecklistItem {
		private int id;
	    public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		private String checkpointName;
	    private boolean licenseRequired;
	    private boolean validUptoRequired;

	    // Getters and Setters
	    public String getCheckpointName() { return checkpointName; }
	    public void setCheckpointName(String checkpointName) { this.checkpointName = checkpointName; }

	    public boolean isLicenseRequired() { return licenseRequired; }
	    public void setLicenseRequired(boolean licenseRequired) { this.licenseRequired = licenseRequired; }

	    public boolean isValidUptoRequired() { return validUptoRequired; }
	    public void setValidUptoRequired(boolean validUptoRequired) { this.validUptoRequired = validUptoRequired; }
	}

    
