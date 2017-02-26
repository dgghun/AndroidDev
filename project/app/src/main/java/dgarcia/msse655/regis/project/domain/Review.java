package dgarcia.msse655.regis.project.domain;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.widget.ImageView;


import dgarcia.msse655.regis.project.R;

/**
 * Created by david on 2/25/2017.
 */

public class Review implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    private String reviewTitle;
    private String reviewParagraph;
    private int reviewImageID;

    /**
     * Default constructor
     */
    public Review() {
        reviewTitle = new String();
        reviewParagraph = new String();
        reviewImageID = R.drawable.happy_face;  // default image ID
    }

    public Review(String reviewTitle, String reviewParagraph) {
        this.reviewTitle = reviewTitle;
        this.reviewParagraph = reviewParagraph;
        this.reviewImageID = R.drawable.happy_face; // add argument later ->> reviewImageID
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
} // END OF Review Class
