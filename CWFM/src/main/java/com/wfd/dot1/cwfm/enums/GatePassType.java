package com.wfd.dot1.cwfm.enums;

import java.util.HashMap;
import java.util.Map;

public enum GatePassType {

	CREATE("1","CREATE"),
	RENEW("2","RENEW"),
	EXPAT("3","EXPAT"),
	BLOCK("4","BLOCK"),
	UNBLOCK("5","UNBLOCK"),
	BLACKLIST("6","BLACKLIST"),
	DEBLACKLIST("7","DEBLACKLIST"),
	LOSTORDAMAGE("8","LOST OR DAMAGE"),
	CANCEL("9","CANCEL"),
	BILLVERIFICATION("10","Bill Creation"),
	CONTRACTORRENEWAL("11","CONTRACTOR RENEWAL"),
	PROJECT("12","PROJECT GATEPASS"),
	CONTRACTOREGISTRATION("13","CONTRACTOR REGISTRATION");
	private String status;
	private String name;
	private GatePassType(String status, String name) {
		this.status = status;
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private static final Map<String, GatePassType> STATUS_LOOKUP = new HashMap<>();
    private static final Map<String, GatePassType> NAME_LOOKUP = new HashMap<>();

    static {
        for (GatePassType type : values()) {
            STATUS_LOOKUP.put(type.status, type);
            NAME_LOOKUP.put(type.name, type);
        }
    }

    public static GatePassType fromStatus(String status) {
        return STATUS_LOOKUP.get(status);
    }

    public static GatePassType fromName(String name) {
        return NAME_LOOKUP.get(name);
    }
}
