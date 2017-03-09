package dgarcia.msse655.regis.project;

import android.content.Intent;
import android.support.annotation.StringDef;
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

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class reviewDetailActivityFragment extends Fragment {

    public reviewDetailActivityFragment(){

    }

    //Set view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final int NEW_REVIEW = -1;

        //TODO- Need to implement a check (if old review block edit/hide save button, else let edit EditTexts)
        final ReviewSvcSQLiteImpl reviewSvcSQLite = new ReviewSvcSQLiteImpl(this.getContext());
        final View rootView = inflater.inflate(R.layout.fragment_review_detail, container, false); // Set view
        Intent intent = getActivity().getIntent();  //Grab the intent.
        Review review = (Review) intent.getSerializableExtra("Review"); //get Review Object from intent, Object = serializable

        //Used to set editable or not
        final EditText titleET = (EditText)rootView.findViewById(R.id.reviewTitle_editText);
        final EditText paragraphET = (EditText)rootView.findViewById(R.id.reviewParagraph_edit_text);
        final TextView reviewHeaderTV = (TextView) rootView.findViewById(R.id.reviewHeader_editText);
        final Button saveReviewButton = (Button)rootView.findViewById(R.id.button_saveReview);

        //If a new review must be from AddReview Button. Allow editing
        if(review.getReviewId() == NEW_REVIEW){

            lockReviewView(false, titleET, paragraphET, saveReviewButton);  //unlock EditTxt/Button


        }
        else{   // Isn't a new review, lock it up for viewing only

            lockReviewView(true, titleET, paragraphET, saveReviewButton);  //lock EditTxt/Button
            reviewHeaderTV.setText(review.getReviewDate());
            titleET.setText(review.getReviewTitle());
            paragraphET.setText(review.getReviewParagraph());
        }


        saveReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String success = "";
                String title = titleET.getText().toString().trim();
                String paragraph = paragraphET.getText().toString().trim();

                // Check for empty title and paragraph
                if(title.isEmpty()) Toast.makeText(getContext(), "Enter title!", Toast.LENGTH_SHORT).show();
                else if(paragraph.isEmpty()) Toast.makeText(getContext(), "Enter paragraph!", Toast.LENGTH_SHORT).show();
                else{
                    //put review in db
                    Review review = new Review();   // new review with date, -1 id
                    review.setReviewTitle(title);
                    review.setReviewParagraph(paragraph);
                    review = reviewSvcSQLite.create(review);    // put in db and get Id
                    if(review != null) success = "successfully!";
                    else success = "unsuccessfully!";

                    //Send back to parent activity
                    // Example:http://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
                    Intent intentToParent = new Intent();
                    intentToParent.putExtra("Review", review);

                    final int CODE_ADD_REVIEW = 1;
                    getActivity().setResult(CODE_ADD_REVIEW, intentToParent); //For parent onActivityResult() method

                    Toast.makeText(getContext(), "Saved " + success, Toast.LENGTH_SHORT).show();
                    getActivity().finish();
                }
            }//END OF saveReviewButtonOnClick
        });


        return rootView; // return view
    }// END OF onCreate

   private void lockReviewView(Boolean lock, EditText title, EditText paragraph, Button saveReviewButton){
       if(lock){
           title.setFocusable(false);
           paragraph.setFocusable(false);
           saveReviewButton.setVisibility(INVISIBLE);
       }
       else{
           title.setFocusable(true);
           paragraph.setFocusable(true);
           saveReviewButton.setVisibility(VISIBLE);
       }
   }


}
