package dgarcia.msse655.regis.project.services;
import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.util.Log;
import java.util.List;

import dgarcia.msse655.regis.project.R;
import dgarcia.msse655.regis.project.domain.Review;

import static org.hamcrest.Matchers.is;

/**
 * Created by david on 2/26/2017.
 */

public class ReviewSvcSioImpl_TEST extends ApplicationTestCase<Application> { //ApplicationTestCase gives some methods like getContext

    public final String REVIEW_TITLE_1 =  "REVIEW 1 TITLE TEST";
    public final String REVIEW_PARAGRAPH_1 =  "REVIEW 1 PARAGRAPH TEST. Some review about some topic.";
    public final String REVIEW_TITLE_2 =  "REVIEW 2 TITLE TEST";
    public final String REVIEW_PARAGRAPH_2 =  "REVIEW 2 PARAGRAPH TEST. Some review about some topic.";

    Review review_1, review_2;

    public ReviewSvcSioImpl_TEST(){
        super(Application.class);
    }

    public void testReviewObjects(){

        review_1 = new Review(REVIEW_TITLE_1, REVIEW_PARAGRAPH_1);  //constructor with parameters
        review_2 = new Review();                                    //default constructor
        review_2.setReviewTitle(REVIEW_TITLE_2);                    //set argument
        review_2.setReviewParagraph(REVIEW_PARAGRAPH_2);            //set argument

        assertEquals(review_1.getReviewTitle().equals(REVIEW_TITLE_1), true);
        assertEquals(review_1.getReviewParagraph().equals(REVIEW_PARAGRAPH_1), true);
        assertEquals(review_1.getReviewImageID() == R.drawable.default_image, true);
        assertEquals(review_1.getReviewTitle().equals(review_2.getReviewTitle()), false);
        assertEquals(review_1.getReviewParagraph().equals(review_2.getReviewParagraph()), false);

    }


    public void testWriteObjectAndReadBack(){
        ReviewSvcSioImpl reviewSvcSio = new ReviewSvcSioImpl(this.getContext()); //Pass context from the test


        review_1 = new Review(REVIEW_TITLE_1, REVIEW_PARAGRAPH_1);
        review_2 = new Review(REVIEW_TITLE_2, REVIEW_PARAGRAPH_2);

        reviewSvcSio.create(review_1);
        reviewSvcSio.create(review_2);

        // get new instance of reviewSvcSioImpl to retrieve all reviews and log them
        ReviewSvcSioImpl reviewSvcSio2ndInstance = new ReviewSvcSioImpl(this.getContext());
        List<Review> reviewList = reviewSvcSio2ndInstance.retrieveAllReviews();

        for(Review r : reviewList){
            Log.e("ReviewSvcSioImpl_TEST", "testWriteObjectAndReadBack -" + r.getReviewTitle() + " " + r.getReviewParagraph());
        }

    }

    public void testUpdate_Delete(){
        review_1 = new Review(REVIEW_TITLE_1, "NEW paragraph for " + REVIEW_TITLE_1);
        review_2 = new Review(REVIEW_TITLE_2, REVIEW_PARAGRAPH_2);

        ReviewSvcSioImpl reviewSvcSio = new ReviewSvcSioImpl(this.getContext());


        reviewSvcSio.update(review_1);  // checks update method
        reviewSvcSio.delete(review_2);  // checks delete method

        ReviewSvcSioImpl reviewSvcSio2ndInstance = new ReviewSvcSioImpl(this.getContext());
        List<Review> reviewList = reviewSvcSio2ndInstance.retrieveAllReviews();

        for(Review r : reviewList){
            Log.e("ReviewSvcSioImpl_TEST", "testUpdate_Delete - " + r.getReviewTitle() + " " + r.getReviewParagraph());
        }

        reviewSvcSio.deleteAll();   // checks delete all method
        assertEquals(reviewSvcSio.isEmpty(), true); // asserts all has been deleted
    }
}
