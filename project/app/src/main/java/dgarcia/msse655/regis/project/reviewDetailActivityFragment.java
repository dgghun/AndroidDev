package dgarcia.msse655.regis.project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.LayoutInflaterCompat;
import android.view.LayoutInflater;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class reviewDetailActivityFragment extends Fragment {

    public reviewDetailActivityFragment(){

    }

    //Set view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        // Set view
        View rootView = inflater.inflate(R.layout.fragment_review_detail, container, false);

        //Grab the intent.
        Intent intent = getActivity().getIntent();

        // grab string extra called "item" from intent.
        String item = intent.getStringExtra("item");

        //Find the TextView and set the text using string from intent
        TextView reviewDetailTV = (TextView) rootView.findViewById(R.id.detail_review_name); // sets id to what
        reviewDetailTV.setText(item);

        // return view
        return rootView;
    }

}
