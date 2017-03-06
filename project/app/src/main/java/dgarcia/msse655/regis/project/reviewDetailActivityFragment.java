package dgarcia.msse655.regis.project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import dgarcia.msse655.regis.project.domain.Review;


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
        //String item = intent.getStringExtra("item");
        Review review = (Review) intent.getSerializableExtra("Review"); //get Review Object from intent

        //Find the TextView and set the text using string from intent
        TextView reviewDetailTV = (TextView) rootView.findViewById(R.id.detail_review_name); // sets id to what
        TextView reviewHeaderTV = (TextView) rootView.findViewById(R.id.reviewHeader_editText);
        EditText reviewParagraphET = (EditText) rootView.findViewById(R.id.reviewParagraph_edit_text);


        //reviewDetailTV.setText(item);
        reviewHeaderTV.setText(review.getReviewDate());
        reviewDetailTV.setText(review.getReviewTitle());
        reviewParagraphET.setText(review.getReviewParagraph());



        // return view
        return rootView;
    }

}
