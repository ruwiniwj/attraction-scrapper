package com.tripadvisor.bean;

import java.util.List;

/**
 * QuestionAndAnswersBean contains information about questions asked and the answers provided
 * */
public class QuestionAndAnswersBean {
    private QuestionBean question;
    private List<AnswerBean> answers;

    public QuestionAndAnswersBean() {
    }

    public QuestionBean getQuestion() {
        return question;
    }

    public void setQuestion(QuestionBean question) {
        this.question = question;
    }

    public List<AnswerBean> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerBean> answers) {
        this.answers = answers;
    }

    public void addAnswer(AnswerBean answer) {
        this.answers.add(answer);
    }

    @Override
    public String toString() {
        return "question : " + question + "," +
                "\n answers : " + answers +
                "\n";
    }
}
