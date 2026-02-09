package com.wfd.dot1.cwfm.pojo;

import java.util.Objects;

public class Trade {

	private String tradeId;
	private String tradeName;
	private String unitId;
	private int status;
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
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
	        if (!(o instanceof Trade)) return false;
	        Trade trade = (Trade) o;
	        return Objects.equals(tradeId, trade.tradeId);
	    }

	    @Override
	    public int hashCode() {
	        return Objects.hash(tradeId);
	    }

	    // Optional: for logging/debugging
	    @Override
	    public String toString() {
	        return "Trade{" +
	                "tradeId='" + tradeId + '\'' +
	                ", tradeName='" + tradeName + '\'' +
	                ", unitId='" + unitId + '\'' +
	                ", status=" + status +
	                '}';
	    }
	}
