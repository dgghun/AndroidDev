package dgarcia.msse655.regis.project;

import android.content.Intent;
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        reviewsArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Store inflated view
        View rootView =  inflater.inflate(R.layout.fragment_main_activity, container, false);
        final ReviewSvcSQLiteImpl reviewSvcSQLite = new ReviewSvcSQLiteImpl(this.getContext()); // start SQLite database, final is used for onClick()

        //ADD Review button onClickListener
        Button addReviewButton = (Button) rootView.findViewById(R.id.button_addReview);
        addReviewButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Review review = new Review();
                Intent intent = new Intent(view.getContext(), reviewDetailActivity.class);
                intent.putExtra("Review", review);  // put the string extra in, i.e. the "item" clicked name.
                getActivity().startActivity(intent);
                getActivity().finish(); // close this activity to be refreshed with new review


            }
        });

        //TODO- Set list view with review name and date
        //Intialize reviewsList with strings.xml values
        reviewList = new ArrayList<>(reviewSvcSQLite.retrieveAllReviews());

        //Initialize to the ListView from rootView, e.g. fragment_main_activity
        reviewListView = (ListView) rootView.findViewById(R.id.ListView_list_of_reviews);

        // Create adapter with type string (the type you are using).
        // Since we are using strings, ues simple_list_item_1 from android because it expects string types.
        reviewsArrayAdapter = new ArrayAdapter<>(rootView.getContext(), android.R.layout.simple_list_item_1, reviewList);

        //Set the adapter on the ListView
        reviewListView.setAdapter(reviewsArrayAdapter);

        // Create onClick handler for the listView
        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //get the item from the list
                Review review = (Review) reviewListView.getItemAtPosition(i); // int i is the position

                // Intent detail
                Intent intent = new Intent(view.getContext(), reviewDetailActivity.class);
                intent.putExtra("Review", review);  // put the string extra in, i.e. the "item" clicked name.
                getActivity().startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }// END OF onCreate()

}
