package com.wfd.dot1.cwfm.dto;



import java.io.Serializable;
import java.util.List;

public class ChatResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;

    private String responseType;

    /*
     * TEXT
     * CARD
     * TABLE
     * LIST
     * CHART
     */
    private String response;

    // Dynamic Data
    private Object data;

    private List<SuggestedQuestion> suggestions;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<SuggestedQuestion> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<SuggestedQuestion> suggestions) {
        this.suggestions = suggestions;
    }

}