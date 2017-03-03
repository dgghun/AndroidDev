package dgarcia.msse655.regis.project.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

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
            " (id integer primary key autoincrement, reviewTitle text not null, reviewParagraph text, reviewImageID integer )";

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


    // *** CRUD ***********************************
    @Override
    public Review create(Review review) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase(); //Get database object
        ContentValues values = new ContentValues();     //Create object to hold ro values that go in table

        //Add values for row
        values.put("reviewTitle", review.getReviewTitle());
        values.put("reviewParagraph", review.getReviewParagraph());
        values.put("reviewImageID", review.getReviewImageID());

        //Insert row into db. Returns row ID of the new row,
        //or -1 if insert failed. Good idea to check returned value.
        long rowIdOfInsertedRecord = sqLiteDatabase.insert(TABLE_1, null, values);
        sqLiteDatabase.close(); //close db

        if (rowIdOfInsertedRecord == -1) return null;   // if insert failed
        return review;  // if insert good
    }

    @Override
    public List<Review> retrieveAllReviews() {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_1, //Table name
                //Column      0     1               2                   3
                new String[]{"id", "reviewTitle", "reviewParagraph", "reviewImageID"},
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
        review.setReviewTitle(cursor.getString(1));
        review.setReviewParagraph(cursor.getString(2));
        review.setReviewImageID(cursor.getInt(3));
        return review;
    }

    @Override
    public Review update(Review review) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues values = new ContentValues();

        //Add values for row to update
        values.put("reviewTitle", review.getReviewTitle());
        values.put("reviewParagraph", review.getReviewParagraph());
        values.put("reviewImageID", review.getReviewImageID());

        // Tell the update how to find the record to update by Id
        int numberOfRowsUpdated = sqLiteDatabase.update(TABLE_1, values,"id = ?", new String[]{String.valueOf(review.getReviewId())});
        sqLiteDatabase.close();

        //TODO-Implement an exception here.
        //check number of rows updated, if failed
        if (numberOfRowsUpdated < 1) return null;   //failed
        return review; // good
    }

    @Override
    public Review delete(Review review) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        int rowsDeleted = sqLiteDatabase.delete(TABLE_1,"id = ?",new String[]{String.valueOf(review.getReviewId())});
        sqLiteDatabase.close();

        //TODO-Implement an exception here
        // Check if deleted
        if(rowsDeleted == 0) return null; // failed
        return review;  // good
    }

    public int deleteAll(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(TABLE_1,null,null);
        return (int) DatabaseUtils.queryNumEntries(sqLiteDatabase, TABLE_1);
    }

}// END OF ReviewSvcSWLiteImpl
