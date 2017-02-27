package dgarcia.msse655.regis.project.domain;


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
    private int reviewImageID;

    /**
     * Default constructor
     */
    public Review() {
        reviewTitle = new String();
        reviewParagraph = new String();
        reviewImageID = R.drawable.default_image;  // default image ID
    }

    /**
     * Constructor with attributes
     * @param reviewTitle
     * @param reviewParagraph
     */
    public Review(String reviewTitle, String reviewParagraph) {
        this.reviewTitle = reviewTitle;
        this.reviewParagraph = reviewParagraph;
        this.reviewImageID = R.drawable.default_image; // add argument later ->> reviewImageID
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;

        if (reviewImageID != review.reviewImageID) return false;
        if (reviewTitle != null ? !reviewTitle.equals(review.reviewTitle) : review.reviewTitle != null)
            return false;
        return reviewParagraph != null ? reviewParagraph.equals(review.reviewParagraph) : review.reviewParagraph == null;

    }

    @Override
    public int hashCode() {
        int result = reviewTitle != null ? reviewTitle.hashCode() : 0;
        result = 31 * result + (reviewParagraph != null ? reviewParagraph.hashCode() : 0);
        result = 31 * result + reviewImageID;
        return result;
    }
} // END OF Review Class
