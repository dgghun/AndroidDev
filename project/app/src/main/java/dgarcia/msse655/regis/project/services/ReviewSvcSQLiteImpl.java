package dgarcia.msse655.regis.project.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dgarcia.msse655.regis.project.domain.Review;

import static android.R.attr.format;

/**
 * Created by David_Garcia on 3/1/2017.
 */

public class ReviewSvcSQLiteImpl extends SQLiteOpenHelper implements IReviewSvc{

    // Data members
    private static final String DBNAME  = "reviews.db";
    private static final int DBVERSION = 1;

    private final String TABLE_REVIEWS = "reviews";
    private final String DATE = "date";
    private final String TITLE = "reviewTitle";
    private final String PARAGRAPH = "reviewParagraph";
    private final String IMAGE_ID = "reviewImageID";

    private String createReviewsTable = "create table " + TABLE_REVIEWS +
            " (id integer primary key autoincrement, "+DATE+" text not null, "+TITLE+" text not null, "+PARAGRAPH+" text, "+IMAGE_ID+" integer )";

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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
        onCreate(sqLiteDatabase);
    }



    // *** CRUD ***********************************
    @Override
    public Review create(Review review) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(); //Get database object
        ContentValues values = new ContentValues();     //Create object to hold ro values that go in table

        //Add values for row
        values.put(DATE, review.getReviewDate());
        values.put(TITLE, review.getReviewTitle());
        values.put(PARAGRAPH, review.getReviewParagraph());
        values.put(IMAGE_ID, review.getReviewImageID());
        //values.put("id", System.currentTimeMillis());

        //Insert row into db. Returns row ID of the new row,
        //or -1 if insert failed. Good idea to check returned value.
        long rowIdOfInsertedRecord = sqLiteDatabase.insert(TABLE_REVIEWS, null, values);
        sqLiteDatabase.close(); //close db

        if (rowIdOfInsertedRecord == -1) return null;   // if insert failed
        else review.setReviewId(rowIdOfInsertedRecord); // else, set key ID and return it
        return review;  // if insert good
    }

    @Override
    public List<Review> retrieveAllReviews() {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_REVIEWS, //Table name
                //Column      0     1      2        3         4
                new String[]{"id", DATE, TITLE, PARAGRAPH, IMAGE_ID},
                null, null, null, null, null);

        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            Review review = getReview(cursor);  //helper method getReview()
            reviews.add(review);
            cursor.moveToNext();
        }

        return reviews;
    }

    // Helper method
    private Review getReview(Cursor cursor){
        Review review = new Review();

        //On getInt & getString the num passed in is index of column
        //name used in query. See string array in retrieveAllReviews()
        review.setReviewId(cursor.getInt(0));
        review.setReviewDate(cursor.getString(1));
        review.setReviewTitle(cursor.getString(2));
        review.setReviewParagraph(cursor.getString(3));
        review.setReviewImageID(cursor.getInt(4));
        return review;
    }

    @Override
    public Review update(Review review) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        //Add values for row to update
        values.put(DATE, review.getReviewDate());
        values.put(TITLE, review.getReviewTitle());
        values.put(PARAGRAPH, review.getReviewParagraph());
        values.put(IMAGE_ID, review.getReviewImageID());

        // Tell the update how to find the record to update by Id
        int numberOfRowsUpdated = sqLiteDatabase.update(TABLE_REVIEWS, values,"id = ?", new String[]{String.valueOf(review.getReviewId())});
        sqLiteDatabase.close();


        //check number of rows updated, if failed
        if (numberOfRowsUpdated < 1) return null;   //failed
        return review; // good
    }

    @Override
    public Review delete(Review review) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int rowsDeleted = sqLiteDatabase.delete(TABLE_REVIEWS,"id = ?",new String[]{String.valueOf(review.getReviewId())});
        sqLiteDatabase.close();

        //TODO-Implement an exception here
        // Check if deleted
        if(rowsDeleted == 0) return null; // failed
        return review;  // good
    }

    public int deleteAll(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        try{
            sqLiteDatabase.delete(TABLE_REVIEWS,null,null);
        }catch (Exception e){
            Log.e("deleteAll()",e.getMessage());
        }
        return (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_REVIEWS);
    }

    public int getNumOfRows(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_REVIEWS);
    }
}// END OF ReviewSvcSWLiteImpl
