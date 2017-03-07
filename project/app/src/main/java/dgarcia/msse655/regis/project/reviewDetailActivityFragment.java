package dgarcia.msse655.regis.project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import dgarcia.msse655.regis.project.domain.Review;
import dgarcia.msse655.regis.project.services.ReviewSvcSQLiteImpl;


public class reviewDetailActivityFragment extends Fragment {

    public reviewDetailActivityFragment(){

    }

    //Set view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //TODO- Need to implement a check (if old review block edit/hide save button, else let edit EditTexts)

        final ReviewSvcSQLiteImpl reviewSvcSQLite = new ReviewSvcSQLiteImpl(this.getContext());

        // Set view
        final View rootView = inflater.inflate(R.layout.fragment_review_detail, container, false);

        //Grab the intent.
        Intent intent = getActivity().getIntent();

        // grab string extra called "item" from intent.
        //String item = intent.getStringExtra("item");
        final Review review = (Review) intent.getSerializableExtra("Review"); //get Review Object from intent

        //Find the TextView and set the text using string from intent
        final TextView reviewTitleTV = (TextView) rootView.findViewById(R.id.textView_reviewTitle); // sets id to what
        TextView reviewHeaderTV = (TextView) rootView.findViewById(R.id.reviewHeader_editText);
        final EditText reviewParagraphET = (EditText) rootView.findViewById(R.id.reviewParagraph_edit_text);

        //reviewTitleTV.setText(item);
        //reviewHeaderTV.setText(review.getReviewDate());
        reviewTitleTV.setText(review.getReviewTitle());
        reviewParagraphET.setText(review.getReviewParagraph());

        Button saveButton = (Button) rootView.findViewById(R.id.button_saveReview);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String success = "";

                review.setReviewTitle(reviewTitleTV.getText().toString());
                review.setReviewParagraph(reviewParagraphET.getText().toString());

                if(reviewSvcSQLite.create(review) != null) success = "successfully!";
                else success = "unsuccessfully!";
                Toast.makeText(getContext(), "Saved " + success, Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(v.getContext(), MainActivity.class);
                intent1.putExtra("Review", review);
                getActivity().startActivity(intent1);
                getActivity().finish();


            }
        });

        // return view
        return rootView;
    }

}
