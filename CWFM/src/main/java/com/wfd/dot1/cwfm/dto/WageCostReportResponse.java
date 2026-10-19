package com.wfd.dot1.cwfm.dto;

import java.util.List;

public class WageCostReportResponse {

    private boolean success;
    private String code;
    private String message;
    
    private int insertedRows;
    private List<String> invalidContractorGatePassNumbers;
    private List<String> duplicateGatePassNumbers;
    private boolean partialProcessing;

    public boolean isPartialProcessing() {
        return partialProcessing;
    }

    public void setPartialProcessing(boolean partialProcessing) {
        this.partialProcessing = partialProcessing;
    }
    public int getInsertedRows() {
		return insertedRows;
	}

	public void setInsertedRows(int insertedRows) {
		this.insertedRows = insertedRows;
	}

	public List<String> getInvalidContractorGatePassNumbers() {
		return invalidContractorGatePassNumbers;
	}

	public void setInvalidContractorGatePassNumbers(List<String> invalidContractorGatePassNumbers) {
		this.invalidContractorGatePassNumbers = invalidContractorGatePassNumbers;
	}

	public List<String> getDuplicateGatePassNumbers() {
		return duplicateGatePassNumbers;
	}

	public void setDuplicateGatePassNumbers(List<String> duplicateGatePassNumbers) {
		this.duplicateGatePassNumbers = duplicateGatePassNumbers;
	}

	public WageCostReportResponse() {
    }

    public WageCostReportResponse(
            boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}