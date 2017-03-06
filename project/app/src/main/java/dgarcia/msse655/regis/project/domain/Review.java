package dgarcia.msse655.regis.project.domain;


import java.util.Date;

import dgarcia.msse655.regis.project.R;

/**
 * Created by david on 2/25/2017.
 * This class contains all the attributes needed for a Review.
 * This class is also Serializable.
 */

public class Review implements java.io.Serializable{

    private static final long serialVersionUID = 1L;    //serializable ID
    private String reviewTitle;
    private String reviewParagraph;
    private String reviewDate;
    private long reviewId;
    private int reviewImageID;


    public String getDateTime(){
        long mills = System.currentTimeMillis();
        Date date = new Date(mills);
        return date.toString();
    }

    /**
     * Default constructor
     */
    public Review() {
        reviewId = -1;
        reviewDate = getDateTime();
        reviewTitle = "";
        reviewParagraph = "";
        reviewImageID = R.drawable.default_image;  // default image ID
    }

    /**
     * Constructor with attributes
     * @param reviewTitle
     * @param reviewParagraph
     */
    public Review(int reviewID, String reviewTitle, String reviewParagraph) {
        this.reviewId = reviewID;
        this.reviewDate = getDateTime();
        this.reviewTitle = reviewTitle;
        this.reviewParagraph = reviewParagraph;
        this.reviewImageID = R.drawable.default_image; // add argument later ->> reviewImageID
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }

    public long getReviewId() {
        return reviewId;
    }

    public void setReviewId(long reviewId) {
        this.reviewId = reviewId;
    }

    public int getReviewImageID() {
        return reviewImageID;
    }

    public void setReviewImageID(int reviewImageID) {
        this.reviewImageID = reviewImageID;
    }

    public String getReviewParagraph() {
        return reviewParagraph;
    }

    public void setReviewParagraph(String reviewParagraph) {
        this.reviewParagraph = reviewParagraph;
    }

    public String getReviewTitle() {
        return reviewTitle;
    }

    public void setReviewTitle(String reviewTitle) {
        this.reviewTitle = reviewTitle;
    }

    @Override
    public String toString() {
        return "Review{" +
                "reviewTitle='" + reviewTitle + '\'' +
                ", reviewParagraph='" + reviewParagraph + '\'' +
                ", reviewDate='" + reviewDate + '\'' +
                ", reviewId=" + reviewId +
                ", reviewImageID=" + reviewImageID +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (reviewId != review.reviewId) return false;
        if (reviewImageID != review.reviewImageID) return false;
        if (reviewTitle != null ? !reviewTitle.equals(review.reviewTitle) : review.reviewTitle != null)
            return false;
        if (reviewParagraph != null ? !reviewParagraph.equals(review.reviewParagraph) : review.reviewParagraph != null)
            return false;
        return reviewDate != null ? reviewDate.equals(review.reviewDate) : review.reviewDate == null;

    }

    @Override
    public int hashCode() {
        int result = reviewTitle != null ? reviewTitle.hashCode() : 0;
        result = 31 * result + (reviewParagraph != null ? reviewParagraph.hashCode() : 0);
        result = 31 * result + (reviewDate != null ? reviewDate.hashCode() : 0);
        result = 31 * result + (int) (reviewId ^ (reviewId >>> 32));
        result = 31 * result + reviewImageID;
        return result;
    }
} // END OF Review Class
