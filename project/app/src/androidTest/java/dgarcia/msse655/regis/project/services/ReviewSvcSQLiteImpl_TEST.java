package dgarcia.msse655.regis.project.services;

import android.app.Application;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.SystemClock;
import android.test.ApplicationTestCase;
import android.util.Log;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.xml.datatype.Duration;

import dalvik.annotation.TestTargetClass;
import dgarcia.msse655.regis.project.domain.Review;

// Using JUnit 4 scheme
//
public class ReviewSvcSQLiteImpl_TEST extends ApplicationTestCase<Application> {

    public ReviewSvcSQLiteImpl_TEST(){
        super(Application.class);
    }

    //Helper time stamp method
    public String getDateTime(){
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        return date.toString();
    }

    @Test
    public void testReadWriteReviewObject() {
        //Pass context from test
        ReviewSvcSQLiteImpl reviewSvcSQLite = new ReviewSvcSQLiteImpl(this.getContext());
        Review review = new Review();

        // Create 3 reviews with a time stamp as the title, store in db
        for(int i = 0; i < 3; i++){
            review.setReviewTitle("TIME: "+ getDateTime());
            reviewSvcSQLite.create(review);
            SystemClock.sleep(1000);    //wait for seconds time stamp
        }

        List<Review> reviewList = reviewSvcSQLite.retrieveAllReviews(); // get all reviews in db

        int rows = 0; //count how many rows there are
        for(Review r : reviewList){
            Log.e("TEST", "WriteAndReadReviewObject ReviewID:" + r.getReviewId() + " " + r.getReviewTitle());
            rows++;
        }
        Log.e("TEST", "WriteAndReadReviewObject Row Count Before Delete All: " + rows);

        rows = reviewSvcSQLite.deleteAll(); // delete all rows (Reviews)
        Log.e("TEST", "WriteAndReadReviewObject - Deleted all rows. Current rows: " + rows);
    }

    //TODO-Need to finish this
    @Test
    public void testUpdateAndDelete(){
        ReviewSvcSQLiteImpl reviewSvcSQLite = new ReviewSvcSQLiteImpl(this.getContext());


    }




}// END OF reviewSvcDQLiteImpl_TEST
