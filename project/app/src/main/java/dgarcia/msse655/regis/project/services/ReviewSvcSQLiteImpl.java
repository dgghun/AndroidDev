package dgarcia.msse655.regis.project.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import dgarcia.msse655.regis.project.domain.Review;

/**
 * Created by David_Garcia on 3/1/2017.
 */

public class ReviewSvcSQLiteImpl extends SQLiteOpenHelper implements IReviewSvc{

    // Data members
    private static final String DBNAME  = "reviews.db";
    private static final int DBVERSION = 1;

    private final String TABLE_1 = "reviews";

    private String createReviewsTable = "create table " + TABLE_1 +
            " (id integer primary key autoincrement, reviewTitle text not null, reviewParagraph text, reviewImageID text )";

    //Constructor SQLiteOpenHelper
    public ReviewSvcSQLiteImpl(Context context){
        super(context, DBNAME, null, DBVERSION);
    }

    // Abstract method over ridden, creates database
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(createReviewsTable);
    }

    // Abstract method over ridden, used to update database schema
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1){
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_1);
        onCreate(sqLiteDatabase);
    }

    @Override
    public Review create(Review review) {
        //Get database object

        return review;
    }

    @Override
    public List<Review> retrieveAllReviews() {
        return null;
    }

    @Override
    public Review update(Review review) {
        return null;
    }

    @Override
    public Review delete(Review review) {
        return null;
    }

}// END OF ReviewSvcSWLiteImpl
