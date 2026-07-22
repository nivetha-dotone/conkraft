package com.wfd.dot1.cwfm.dto;


import java.io.Serializable;

public class SuggestedQuestion implements Serializable{

    private static final long serialVersionUID = 1L;

    private String question;

    public SuggestedQuestion() {

    }

    public SuggestedQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}