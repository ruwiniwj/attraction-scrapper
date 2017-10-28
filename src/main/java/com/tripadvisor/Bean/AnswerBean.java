package com.tripadvisor.Bean;

import java.io.Serializable;

/**
 * AnswerBean provides information about the answer provided for a question
 * */
public class AnswerBean implements Serializable{
    private String answer;
    private int voteOnUsefulness;

    public AnswerBean() {
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getVoteOnUsefulness() {
        return voteOnUsefulness;
    }

    public void setVoteOnUsefulness(String voteOnUsefulness) {
        this.voteOnUsefulness = Integer.parseInt(voteOnUsefulness);
    }

    @Override
    public String toString() {
        return "answer : '" + answer + '\'' +
                " | voteOnUsefulness : " + voteOnUsefulness +
                "\n";
    }
}
