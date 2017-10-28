package com.tripadvisor.bean;

import java.io.Serializable;

/**
 * QuestionBean contains information about the question asked
 * */
public class QuestionBean implements Serializable{
    private String question;
    private String dateAsked;

    public QuestionBean() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDateAsked() {
        return dateAsked;
    }

    public void setDateAsked(String dateAsked) {
        this.dateAsked = dateAsked;
    }

    @Override
    public String toString() {
        return "dateAsked='" + dateAsked + '\'' +
                " | question='" + question + '\'' +
                "\n";
    }
}
