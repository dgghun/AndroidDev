package dgarcia.msse655.regis.project;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.StringDef;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import dgarcia.msse655.regis.project.adapters.ReviewIconSpinnerAdapter;
import dgarcia.msse655.regis.project.domain.Review;
import dgarcia.msse655.regis.project.services.ReviewSvcSQLiteImpl;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class reviewDetailActivityFragment extends Fragment {

    public reviewDetailActivityFragment(){

    }


    //TODO-Add icon spinner code from http://abhiandroid.com/ui/custom-spinner-examples.html
    //Set view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final int NEW_REVIEW = -1;

        Intent intent = getActivity().getIntent();  //Grab the intent.
        Review review = (Review) intent.getSerializableExtra("Review"); //get Review Object from intent, Object = serializable

        final ReviewSvcSQLiteImpl reviewSvcSQLite = new ReviewSvcSQLiteImpl(this.getContext());
        final View rootView;

        //Used to set editable or not
        final EditText titleET;
        final EditText paragraphET;
        final TextView reviewHeaderTV;
        final Button saveReviewButton;
        final Spinner spinner;
        final StringBuffer iconId = new StringBuffer(" ");


        //If new review, must be from AddReview Button. Allow editing
        if(review.getReviewId() == NEW_REVIEW){
            final int icons[] = getReviewIcons();
            rootView = inflater.inflate(R.layout.fragment_review_add, container, false); // Set fragment

            titleET = (EditText)rootView.findViewById(R.id.reviewTitle_editText);
            paragraphET = (EditText)rootView.findViewById(R.id.reviewParagraph_edit_text);
            saveReviewButton = (Button) rootView.findViewById(R.id.button_saveReview);
            spinner = (Spinner) rootView.findViewById(R.id.spinner_reviewIcons);

            // Icon spinner listener
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try{
                         iconId.replace(0, iconId.length()-1, Long.toString(id));    // getIconId
                    }catch (Exception e){Log.e("ERROR", e.toString());}
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                //TODO-auto generated method stub
                }
            });

            ReviewIconSpinnerAdapter spinnerAdapter = new ReviewIconSpinnerAdapter(rootView.getContext(), icons);
            spinner.setAdapter(spinnerAdapter);

        }
        else{   // Isn't a new review, lock it up for viewing only
            rootView = inflater.inflate(R.layout.fragment_review_detail, container, false); // Set fragment


            titleET = (EditText)rootView.findViewById(R.id.reviewTitle_editText);
            paragraphET = (EditText)rootView.findViewById(R.id.reviewParagraph_edit_text);
            reviewHeaderTV = (TextView) rootView.findViewById(R.id.reviewHeader_editText);
            saveReviewButton = null;

            // Set views with Review info
            reviewHeaderTV.setText(review.getReviewDate());
            titleET.setText(review.getReviewTitle());
            paragraphET.setText(review.getReviewParagraph());
        }

        //If adding a Review, activate SaveButton listener
        if(saveReviewButton != null) {

            saveReviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String success = "";
                    String title = titleET.getText().toString().trim();
                    String paragraph = paragraphET.getText().toString().trim();

                    // Check for empty title and paragraph
                    if (title.isEmpty())
                        Toast.makeText(getContext(), "Enter title!", Toast.LENGTH_SHORT).show();
                    else if (paragraph.isEmpty())
                        Toast.makeText(getContext(), "Enter paragraph!", Toast.LENGTH_SHORT).show();
                    else {
                        //put review in db
                        Review review = new Review();   // new review with date, -1 id
                        review.setReviewTitle(title);
                        review.setReviewParagraph(paragraph);
                        review.setReviewImageID(Integer.parseInt(iconId.toString().trim()));
                        review = reviewSvcSQLite.create(review);    // put in db and get Id

                        if (review != null) success = "successfully!";
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
        }// END OF If SaveButton Listener

        return rootView; // return view
    }// END OF onCreate


    public int[] getReviewIcons(){

        final int MAX_ICONS = 120, MIN_ICONS = 0;
        String iconName = "ic_review", icon;
        int icons[] = new int[MAX_ICONS+1];

        try {
            for (int i = MIN_ICONS; i <= MAX_ICONS; i++) {
                icon = iconName + String.valueOf(i);
                icons[i] = getResources().getIdentifier(icon, "drawable", getContext().getPackageName());
                Log.e("ICONS", i + "-" + icon + "   ID:" + icons[i]);
            }
        }catch (Exception e){
            Log.e("ERROR", e.toString());
        }

        return icons;
    }

}
