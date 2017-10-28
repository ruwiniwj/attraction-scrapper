package com.tripadvisor.bean;

import java.io.Serializable;

/**
 * ReviewBean is used to store user reviews
 * */
public class ReviewBean implements Serializable{
    private String title;
    private String dateReviewed;
    private String review;
    private int rating;

    public ReviewBean() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateReviewed() {
        return dateReviewed;
    }

    public void setDateReviewed(String dateReviewed) {
        this.dateReviewed = dateReviewed;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + " | Date reviewed: " + dateReviewed  +
                " | Title: " + title + " | Review: " + review + "\n";
    }
}
