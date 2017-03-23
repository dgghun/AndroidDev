package com.dgarcia.project_firebase;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.*;

import java.util.Date;

public class MainFragment extends Fragment{

    private Button mPostButton;
    private TextView mOutputWindow;
    private TestObject testObject;
    private DatabaseReference fireBase;
    private static int count = 0;

    final String dfString = "MMMM dd yyyy - hh:mm:ss a";  // date format string
    final android.text.format.DateFormat dateFormat = new DateFormat();
    final int ERROR = 0, POSTED = 1, ADDED = 2;

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("dgghun@gmail.com", "123456");

        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        fireBase = FirebaseDatabase.getInstance().getReference(); //get firebase handle
        mOutputWindow = (TextView) view.findViewById(R.id.TV_post_output_window); //handle on OutputWindow TV


        //Set up POST button
        mPostButton = (Button)view.findViewById(R.id.button_POST);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                testObject = new TestObject();
                testObject.setId(count);
                testObject.setDate(dateFormat.format(dfString, new Date()).toString());
                fireBase.child("TestObject").child(Integer.toString(testObject.getId())).setValue(testObject); //Add Object
                outputWindow(testObject, view, POSTED); //set OutPut window notification that we are posting to firebase
            }// END OF onClick()
        }); // END OF setonClickListener()


        //TODO-Get data from firebase
        //Firebase POST listener setup/implementation
        ValueEventListener postListener = fireBase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TestObject dbObject = dataSnapshot.getValue(TestObject.class);  // Get object from firebase


                if(dbObject != null){
                    outputWindow(dbObject, view, ADDED);
                    Log.e("FIREBASE", dataSnapshot.getValue(TestObject.class).toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                mOutputWindow.append("\n ERROR: " + databaseError.toException().toString() + "\n" );
                scrollDown(mOutputWindow, view);
            }
        });

        return view;
    }

    public void outputWindow(TestObject testObject, View view, int outPutType){
        String currentText  = mOutputWindow.getText().toString();
        int objectId = -1;
        String objectDate = "NULL";
        String postedStr = "POST ID: ";
        String addedStr = "ADDED ID: ";
        String message = "";

        try {
            objectDate = testObject.getDate();
            objectId = testObject.getId();
        }catch (Exception e){}

        if(outPutType == POSTED) message = postedStr;
        else if (outPutType == ADDED) message = addedStr;

        mOutputWindow.setText(currentText + message + objectId + " Date: " + objectDate + "\n");
        scrollDown(mOutputWindow, view);
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
