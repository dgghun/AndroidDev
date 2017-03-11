package dgarcia.msse655.regis.project;

import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dgarcia.msse655.regis.project.domain.Review;
import dgarcia.msse655.regis.project.services.ReviewSvcSQLiteImpl;

import static android.widget.Toast.LENGTH_SHORT;


public class mainActivityFragment extends Fragment{

    private List<Review> reviewList = null;
    ReviewAdapter reviewsAdapter;
    ReviewSvcSQLiteImpl reviewSvcSQLite;
    final int CODE_ADD_REVIEW = 1;


    public mainActivityFragment() {
        // Required empty public constructor
    }


    //Add review to reviewsAdapter
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            case CODE_ADD_REVIEW:
                Review review = (Review)data.getSerializableExtra("Review");
                if(review.getReviewId() != -1) reviewsAdapter.add(review);  //review was save successfully, add to view
                break;

        }
    }// END OF onActivityResult()


    @Override   //Setup Context Menu for long press
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select Action");
        menu.add(0,v.getId(), 0, "Delete");
        menu.add(0,v.getId(), 0, "Share");
        menu.add(0,v.getId(), 0, "Exit");
    }

    @Override   //Handles Context Menu for long press
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        String message = item.getTitle().toString();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        if(message.equals("Delete")){   //delete review from adapter & db
            int rowsBefore, rowsAfter;
            reviewList = reviewSvcSQLite.retrieveAllReviews();              //Get reviews in db
            rowsBefore = reviewList.size();                                 //Get row count before delete
            reviewSvcSQLite.delete(reviewsAdapter.getItem(info.position));  //Delete review from db
            reviewsAdapter.remove(reviewsAdapter.getItem(info.position));   //Remove from AdapterListView
            reviewList = reviewSvcSQLite.retrieveAllReviews();              //Get new row count
            rowsAfter = reviewList.size();
            if(rowsAfter < rowsBefore) Toast.makeText(getContext(), "Review Deleted", Toast.LENGTH_SHORT).show();
            else Toast.makeText(getContext(), "Error deleting review.", LENGTH_SHORT).show();
        }
        else if(message.equals("Share"))
            Toast.makeText(getContext(), message + " coming soon!", LENGTH_SHORT).show();
        else return false;

        return true;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Store inflated view
        View rootView =  inflater.inflate(R.layout.fragment_main_activity, container, false);
        reviewSvcSQLite = new ReviewSvcSQLiteImpl(this.getContext()); // start SQLite database, final is used for onClick()

        //TODO-TESTING ITEMS
        //reviewSvcSQLite.deleteAll();    //REMOVE THIS, DELETES DATABASE ON START!!!!!!!!!!!!!!!!!!!
//        Review review = new Review();
//        review.setReviewTitle("Test Review Title");
//        review.setReviewParagraph("This is a test paragraph about stuff and things, blah blah blah blah blah blah blah blah blah blah ");
//        reviewSvcSQLite.create(review); // add review to db

        reviewList = new ArrayList<>(reviewSvcSQLite.retrieveAllReviews()); //Initialize review ArrayList with SQLite database.
        final ListView reviewListView = (ListView) rootView.findViewById(R.id.ListView_list_of_reviews); //get listView in fragMainActivity
        reviewsAdapter = new ReviewAdapter(rootView.getContext(), R.layout.review_list_item); //Custom Review list adapter
        reviewListView.setAdapter(reviewsAdapter); //Set adapter on the ListView

//        for(final Review r : getReviewEntries()) reviewsAdapter.add(r);   //populate list to adapter w/ test method getReviewEntries()
        for(final Review r : reviewList) reviewsAdapter.add(r);   //populate reviewList to adapter

    //ListView onClick handler
        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Review review = (Review) reviewListView.getItemAtPosition(i);   //Get i from list, i is the position
                Intent intent = new Intent(view.getContext(), reviewDetailActivity.class);  //New intent on reviewDetailActivity
                intent.putExtra("Review", review);  //Put Review extra in, i.e. clicked Review from listView.
                getActivity().startActivity(intent);
            }
        });


    // Add Review Button onClickListener
        Button addReviewButton = (Button) rootView.findViewById(R.id.button_addReview);
        addReviewButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Review review = new Review();
                Intent intent = new Intent(view.getContext(), reviewDetailActivity.class);
                intent.putExtra("Review", review);  // put the string extra in, i.e. the "item" clicked name.
                startActivityForResult(intent,CODE_ADD_REVIEW); // starts activity with my code of 1. Could use to check return.
            }
        });

        registerForContextMenu(reviewListView);

        // Inflate the layout for this fragment
        return rootView;
    }// END OF onCreate()



    /**
     * Test review list
     * @return a list of reviews
     */
    private List<Review> getReviewEntries() {

        // Let's setup some test data.
        // Normally this would come from some asynchronous fetch into a data source
        // such as a sqlite database, or an HTTP request

        final List<Review> reviews = new ArrayList<Review>();

        for(int i = 1; i < 50; i++) {
            reviews.add(new Review( i, "Review Title # " + i,
                    "This review is about something and I think it is important because of stuff and things, blah blah blah blah blah blah blah" +
                            "blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah blah"));
        }

        return reviews;
    }

}
