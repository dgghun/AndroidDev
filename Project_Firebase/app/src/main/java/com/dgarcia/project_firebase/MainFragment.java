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

import com.dgarcia.project_firebase.model.TestObject;
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
    private DatabaseReference fireBaseRef;
    private DatabaseReference connectedRef;
    private ValueEventListener mConnectedListener;
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

        view = inflater.inflate(R.layout.fragment_main, container, false);
        mOutputWindow = (TextView) view.findViewById(R.id.TV_post_output_window); //handle on OutputWindow TV

        //Set up POST button
        mPostButton = (Button)view.findViewById(R.id.button_POST);
        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                testObject = new TestObject(count, dateFormat.format(dfString, new Date()).toString()); //Create new object

                fireBaseRef.child("Object " + Integer.toString(testObject.getId())).setValue(testObject); //Add Object via Firebase Android API

                mOutputWindow.append("\n" + " -> Posting (ID:" + testObject.getId() + "-" + testObject.getDate() + ")");
                scrollDown(mOutputWindow, view);

            }// END OF onClick()
        }); // END OF setonClickListener()


        return view;
    } // END OF onCreate()


    // DON'T USE BELOW YET. Android Firebase API stuff

    @Override
    public void onStart(){
        super.onStart();

         //Temp login for Firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("dgghun@gmail.com", "david123456");

        fireBaseRef = FirebaseDatabase.getInstance().getReference(ROOT); //get firebase handle
        fireBaseRef.getRef().removeValue(); //Clear data base

        //Add connected listener
        connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        ValueEventListener connectedListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean connected = dataSnapshot.getValue(Boolean.class);
                if(connected){
                    Toast.makeText(view.getContext(), "Connected to Firebase", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(view.getContext(), "Disconnected from Firebase", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };


        //Add value event listener
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    for(DataSnapshot dbObject : dataSnapshot.getChildren()){

                        mOutputWindow.append(" <-------onDataChange (Name: " + dbObject.getKey() + ")");
                        mOutputWindow.append(" (ID:" + dbObject.getValue(TestObject.class).getId() + ")");
                        mOutputWindow.append(" (Date:" + dbObject.getValue(TestObject.class).getDate() + ")" + "\n");
                        scrollDown(mOutputWindow, view);
                    }
                }catch (Exception e){
                    Toast.makeText(view.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ERROR", "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(view.getContext(), "Failed to load post.", Toast.LENGTH_SHORT).show();
            }
        }; //END OF ValueEventListener()


        //Add child listener
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
        }; //END OF ChildEventListener

        fireBaseRef.addValueEventListener(postListener);
        fireBaseRef.addChildEventListener(childListener);
        connectedRef.addValueEventListener(connectedListener);

        //Copy listeners to stop later on
        mChildListener = childListener;
        mPostListener = postListener;
        mConnectedListener = connectedListener;

    } //END OF onStart()


    @Override
    public void onStop(){
        super.onStop();
        if(mPostListener != null)
            fireBaseRef.removeEventListener(mPostListener);

        if(mChildListener != null)
            fireBaseRef.removeEventListener(mChildListener);

        if(mConnectedListener != null)
            connectedRef.removeEventListener(mConnectedListener);

        fireBaseRef.getRef().removeValue(); // remove values from db
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


}// END OF MainFragment()



