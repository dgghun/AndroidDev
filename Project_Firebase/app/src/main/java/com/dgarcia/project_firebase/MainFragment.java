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
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.*;

import org.json.JSONArray;

import java.util.Date;

public class MainFragment extends Fragment{

    private Button mPostButton;
    private TextView mOutputWindow;
    private TestObject testObject;
    private DatabaseReference fireBaseRef;
    private ValueEventListener mPostListener;
    private ChildEventListener mChildListener;
    private static int count = 0;
    private View view;
    private final String ROOT = "TestObjects";

    final String dfString = "MM/dd/yy  hh:mm:ss a";  // date format string
    final android.text.format.DateFormat dateFormat = new DateFormat();

    @Override
    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstances){

        //Temp login for Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("dgghun@gmail.com", "123456");

        view = inflater.inflate(R.layout.fragment_main, container, false);
        fireBaseRef = FirebaseDatabase.getInstance().getReference(ROOT); //get firebase handle
        mOutputWindow = (TextView) view.findViewById(R.id.TV_post_output_window); //handle on OutputWindow TV

        //Set up POST button
        mPostButton = (Button)view.findViewById(R.id.button_POST);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                testObject = new TestObject(count, dateFormat.format(dfString, new Date()).toString()); //Create new object

                fireBaseRef.child("Object " + Integer.toString(testObject.getId())).setValue(testObject); //Add Object

                mOutputWindow.append("\n" + " -> Posting (ID:" + testObject.getId() + "-" + testObject.getDate() + ")");
                scrollDown(mOutputWindow, view);
            }// END OF onClick()
        }); // END OF setonClickListener()


        return view;
    } // END OF onCreate()


    @Override
    public void onStart(){
        super.onStart();

        //Add value event listener

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object testObject =  dataSnapshot.child(dataSnapshot.getKey()).getValue();
                try {
                    mOutputWindow.append("\n <- onDataChange (" + testObject + ")\n");
                    scrollDown(mOutputWindow, view);
                }catch (Exception e){
                    Toast.makeText(view.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(view.getContext(), "Failed to load post.", Toast.LENGTH_SHORT).show();
            }
        };

        ChildEventListener childListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                TestObject testObject = dataSnapshot.getValue(TestObject.class);
                try {
                    mOutputWindow.append("\n <- onChildAdded (ID:" + testObject.getId() + "-" + testObject.getDate() + ")\n");
                    scrollDown(mOutputWindow, view);
                }catch (Exception e){
                    Toast.makeText(view.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                TestObject testObject = dataSnapshot.getValue(TestObject.class);
                try {
                    mOutputWindow.append("\n <- onChildChanged (ID:" + testObject.getId() + "-" + testObject.getDate() + ")\n");
                    scrollDown(mOutputWindow, view);
                }catch (Exception e){
                    Toast.makeText(view.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

//        fireBaseRef.addValueEventListener(postListener);
        fireBaseRef.addChildEventListener(childListener);


        mChildListener = childListener;
        mPostListener = postListener;
    } //END OF onStart()

    @Override
    public void onStop(){
        super.onStop();
        if(mPostListener != null) {
            fireBaseRef.removeEventListener(mPostListener);
        if(mChildListener != null)
            fireBaseRef.removeEventListener(mChildListener);
        }
    }




    public void scrollDown(final TextView mOutputWindow, View view){
        final ScrollView scrollView = (ScrollView)view.findViewById(R.id.Scroll_post_output_window);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.smoothScrollTo(0, mOutputWindow.getBottom());
            }
        });
    } //END OF scrollDown()


}



