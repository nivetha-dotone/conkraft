package com.wfd.dot1.cwfm.pojo;

import java.util.Objects;

public class Zone {

	private String zoneId;
	private String zoneName;
	private String unitId;
	private int status;
	
	
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneName() {
		return zoneName;
	}
	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	 @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (!(o instanceof Zone)) return false;
	        Zone zone = (Zone) o;
	        return Objects.equals(zoneId, zone.zoneId);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(zoneId);
	    }

	    // Optional: for logging/debugging
	    @Override
	    public String toString() {
	        return "Zone{" +
	                "zoneId='" + zoneId + '\'' +
	                ", zoneName='" + zoneName + '\'' +
	                ", unitId='" + unitId + '\'' +
	                ", status=" + status +
	                '}';
	    }
	}
