package com.dgarcia.project_firebase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainFragment extends Fragment{

    private Button mPostButton;
    private TextView mOutputWindow;
    private TestObject testObject;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){

        final View view = inflater.inflate(R.layout.fragment_main, container, false);

        mOutputWindow = (TextView) view.findViewById(R.id.TV_post_output_window); //handle on OutputWindow TV
       // mOutputWindow.setMovementMethod(new ScrollingMovementMethod()); //set as scrolling text view

        //Set up POST button
        mPostButton = (Button)view.findViewById(R.id.button_POST);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText  = mOutputWindow.getText().toString();
                String dfString = "MMMM dd yyyy - hh:mm:ss a";  // date format string
                android.text.format.DateFormat dateFormat = new DateFormat();
                testObject = new TestObject();  //my object

                mOutputWindow.setText(currentText + "Object #" + testObject.getId() + " - " + dateFormat.format(dfString, testObject.getmDate()) + "\n");
                scrollDown(mOutputWindow, view);

            }
        }); // END OF Button listener

        return view;
    }

    public void scrollDown(final TextView mOutputWindow, View view){
        final ScrollView scrollView = (ScrollView)view.findViewById(R.id.Scroll_post_output_window);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, mOutputWindow.getBottom());
            }
        });
    }
}
