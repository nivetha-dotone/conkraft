package com.wfd.dot1.cwfm.dto;


import java.io.Serializable;

import com.wfd.dot1.cwfm.pojo.MasterUser;

public class ChatRequest implements Serializable{

    private static final long serialVersionUID = 1L;

    private String question;

    private String sessionId;

    private String userId;

    private String principalEmployerId;
    
    private MasterUser user;
    
    private String languageCode;
    private String languageName;

    public String getLanguageCode() {
		return languageCode;
	}

	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	public String getLanguageName() {
		return languageName;
	}

	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}

	public MasterUser getUser() {
		return user;
	}

	public void setUser(MasterUser user) {
		this.user = user;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrincipalEmployerId() {
        return principalEmployerId;
    }

    public void setPrincipalEmployerId(String principalEmployerId) {
        this.principalEmployerId = principalEmployerId;
    }

}