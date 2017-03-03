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
        //String time = Long.toString(System.currentTimeMillis()/1000);  // time stamp for Review object


        Review review = new Review();

        for(int i = 0; i < 3; i++){
            review.setReviewTitle("TIME: "+ getDateTime());
            reviewSvcSQLite.create(review);
            SystemClock.sleep(1000);
        }


        List<Review> reviewList = reviewSvcSQLite.retrieveAllReviews();

        int rows = 0;
        for(Review r : reviewList){
            Log.e("TEST", "WriteAndReadReviewObject ReviewID:" + r.getReviewId() + " " + r.getReviewTitle());
            rows++;
        }
        Log.e("TEST", "WriteAndReadReviewObject Row Count Before Delete All: " + rows);

        rows = reviewSvcSQLite.deleteAll();
        Log.e("TEST", "WriteAndReadReviewObject - Deleted all rows. Current rows: " + rows);
    }




}// END OF reviewSvcDQLiteImpl_TEST
