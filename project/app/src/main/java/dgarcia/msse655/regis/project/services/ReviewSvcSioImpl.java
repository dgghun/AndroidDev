package dgarcia.msse655.regis.project.services;
import android.content.Context;
import android.util.Log;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import dgarcia.msse655.regis.project.domain.Review;

/**
 * Created by david on 2/26/2017.
 */

public class ReviewSvcSioImpl implements IReviewSvc {

    private final String filename = "Reviews.sio";
    private List<Review> reviews;
    private Context appContext;

    /**
     * Constructor
     * Notice we pass the Context in the constructor
     * @param context
     */
    public ReviewSvcSioImpl (Context context){

        reviews = new ArrayList<>();    //null; Originally null, can switch List type maybe???
        // Store context passed in, needed to open files
        appContext = context;
        readFile();
    }

    /**
     * Read serialized file.
     */
    private void readFile(){
        try {
            ObjectInputStream ois = new ObjectInputStream(appContext.openFileInput(filename));
            reviews = (List<Review>) ois.readObject();
            ois.close();
        } catch (Exception e){
            String TAG = "ReviewSvcSioImpl";
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * Write serialized file
     */
    private void writeFile(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(appContext.openFileOutput(filename, Context.MODE_PRIVATE));
            oos.writeObject(reviews);
            oos.flush();
            oos.close();
        } catch (Exception e) {
            String TAG = "ReviewSvcSioImpl";
            Log.e(TAG, e.getMessage());
        }
    }

  /*  private int findReview(Review review){
        int index = reviews.indexOf(review);
        return index;
    }*/

    @Override
    public Review create(Review review) {
        reviews.add(review);
        writeFile();
        return review;
    }

    @Override
    public List<Review> retrieveAllReviews() {
        return reviews;
    }

    @Override
    public Review update(Review review) {   // updates review paragraph

        for(Review reviewO : reviews){
            if(reviewO.getReviewTitle().equals(review.getReviewTitle())){
                reviewO.setReviewParagraph(review.getReviewParagraph());
                writeFile();
                return reviewO;
            }
        }

        return review;
    }

    @Override
    public Review delete(Review review) {

        for(Review reviewO : reviews){
            if(reviewO.getReviewTitle().equals(review.getReviewTitle())){
                Log.e("DELETE_REVIEW", "- found at index " + reviews.indexOf(reviewO) + " - " + reviewO.getReviewTitle() + "\n");
                reviews.remove(reviews.indexOf(reviewO));
                writeFile();
                return review;
            }
        }
        return review;
    }

    public void deleteAll(){
        reviews.clear();
        writeFile();
    }

    public boolean isEmpty(){
        return reviews.isEmpty();
    }
}
