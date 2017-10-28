package com.tripadvisor.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * AttractionBean contains information about an
 * */
public class AttractionBean implements Serializable{

    private String attractionName;
    private float overallRating;
    private int reviewsCount;
    private String popularity;
    private List<String> attractionType;
    private String address;
    private String telephoneNumber;
    private String website;
    private String overview;
//    todo make an opening hour dto
    private HashMap<String, String > openHours;
    private String suggestedDuration;
    private List<ReviewBean> reviews;
    private List<QuestionAndAnswersBean> questionsAndAnswers;

    public AttractionBean() {
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public float getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(String overallRating) {
        this.overallRating = Float.parseFloat(overallRating);
    }

    public int getReviewsCount() {
        return reviewsCount;
    }

    public void setReviewsCount(int reviewsCount) {
        this.reviewsCount = reviewsCount;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public List<String> getAttractionType() {
        return attractionType;
    }

    public void setAttractionType(List<String> attractionType) {
        this.attractionType = attractionType;
    }

    public void addAttractionType(String attractionType) {
        this.attractionType.add(attractionType);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public HashMap<String, String> getOpenHours() {
        return openHours;
    }

    public void setOpenHours(HashMap<String, String> openHours) {
        this.openHours = openHours;
    }

    public void addOpenHours(String day, String time) {
        this.openHours.put(day,time);
    }

    public String getSuggestedDuration() {
        return suggestedDuration;
    }

    public void setSuggestedDuration(String suggestedDuration) {
        this.suggestedDuration = suggestedDuration;
    }

    public List<ReviewBean> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewBean> reviews) {
        this.reviews = reviews;
    }

    public void addReview(ReviewBean review) {
        this.reviews.add(review);
    }

    public List<QuestionAndAnswersBean> getQuestionsAndAnswers() {
        return questionsAndAnswers;
    }

    public void setQuestionsAndAnswers(List<QuestionAndAnswersBean> questionsAndAnswers) {
        this.questionsAndAnswers = questionsAndAnswers;
    }

    public void addQuestionsAndAnswer(QuestionAndAnswersBean questionsAndAnswer) {
        this.questionsAndAnswers.add(questionsAndAnswer);
    }

    @Override
    public String toString() {
        return "AttractionBean{" +
                "attractionName='" + attractionName + '\'' +
                ",\n overallRating=" + overallRating +
                ",\n reviewsCount=" + reviewsCount +
                ",\n popularity='" + popularity + '\'' +
                ",\n attractionType=" + attractionType +
                ",\n address='" + address + '\'' +
                ",\n telephoneNumber='" + telephoneNumber + '\'' +
                ",\n website='" + website + '\'' +
                ",\n overview='" + overview + '\'' +
                ",\n openHours=" + openHours +
                ",\n suggestedDuration='" + suggestedDuration + '\'' +
                ",\n reviews=" + reviews +
                ",\n questionsAndAnswers=" + questionsAndAnswers +
                "\n}";
    }
}
