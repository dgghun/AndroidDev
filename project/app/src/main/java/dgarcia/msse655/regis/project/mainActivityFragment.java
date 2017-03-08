package dgarcia.msse655.regis.project;

import android.content.Intent;
import android.icu.util.GregorianCalendar;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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

//    private List<String> reviewList = null;
    private List<Review> reviewList = null;
    private ListView reviewListView = null;
    public ArrayAdapter<Review> reviewsArrayAdapter;

    public mainActivityFragment() {
        // Required empty public constructor
    }

/*    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        reviewsArrayAdapter.notifyDataSetChanged();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Store inflated view
        View rootView =  inflater.inflate(R.layout.fragment_main_activity, container, false);
        final ReviewSvcSQLiteImpl reviewSvcSQLite = new ReviewSvcSQLiteImpl(this.getContext()); // start SQLite database, final is used for onClick()

        /**
         * REMOVE THIS, DELETES DATABASE ON START!!!!!!!!!!!!!!!!!!!
         */
        reviewSvcSQLite.deleteAll();

        //TODO- Have adapter re-populate on return from detailFragment.
        reviewList = new ArrayList<>(reviewSvcSQLite.retrieveAllReviews()); //Initialize review ArrayList with SQLite database.
//        reviewListView = (ListView) rootView.findViewById(R.id.ListView_list_of_reviews); //Init to fragMainActivity ListView
//        reviewsArrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, reviewList); // Create adapter. ues simple_list_item_1 -> expects string types.
//        reviewListView.setAdapter(reviewsArrayAdapter); //Set the adapter on the ListView

        //Testing list view items
        final ListView reviewListV = (ListView) rootView.findViewById(R.id.ListView_list_of_reviews);
        final ReviewAdapter reviewsAdapter = new ReviewAdapter(rootView.getContext(), R.layout.review_list_item); // custom review list adapter
        reviewListV.setAdapter(reviewsAdapter); //Set the adapter on the ListView

        //TODO- Populate list with SQLite reviews
        for(final Review review : getReviewEntries()) reviewsAdapter.add(review);   //populate list through adapter


        //ListView onClick handler
        reviewListV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //get the item from the list
//                Review review = (Review) reviewListView.getItemAtPosition(i); // int i is the position
                Review review = (Review) reviewListV.getItemAtPosition(i); // int i is the position

                // Intent detail
                Intent intent = new Intent(view.getContext(), reviewDetailActivity.class);
                intent.putExtra("Review", review);  // put the string extra in, i.e. the "item" clicked name.
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
                getActivity().startActivity(intent);
                //getActivity().finish(); // close this activity to be refreshed with new review


            }
        });

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
            reviews.add(new Review( i, "Title # " + i, "Paragraph # " + i));
        }

        return reviews;
    }

}
