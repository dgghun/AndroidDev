package dgarcia.msse655.regis.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class mainActivityFragment extends Fragment {

    private List<String> reviewList = null;
    private ListView reviewListView = null;

    public mainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Store inflated view
        View rootView =  inflater.inflate(R.layout.fragment_main_activity, container, false);

        //Intialize reviewsList with strings.xml values
        reviewList = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.reviews_list)));

        //Initialize to the ListView from rootView, e.g. fragment_main_activity
        reviewListView = (ListView) rootView.findViewById(R.id.list_of_reviews);

        // Create adapter with type string (the type you are using).
        // Since we are using strings, ues simple_list_item_1 from android because it expects string types.
        ArrayAdapter<String> reviewsArrayAdapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, reviewList);

        //Set the adapter on the ListView
        reviewListView.setAdapter(reviewsArrayAdapter);

        // Create onClick handler for the list
        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //get the item from the list, string type in this case
                String item = (String) reviewListView.getItemAtPosition(i); // int i is the position

                //Toast when clicked
              // Toast.makeText(view.getContext(), "#"+i+" Clicked:"+item, Toast.LENGTH_SHORT).show();


                // Intent detail
                Intent intent = new Intent(view.getContext(), reviewDetailActivity.class);
                intent.putExtra("item", item);  // put the string extra in, i.e. the "item" clicked name.
                startActivity(intent);          // start activity

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }



}
