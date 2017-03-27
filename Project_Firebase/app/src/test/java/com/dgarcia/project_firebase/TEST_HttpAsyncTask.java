package com.dgarcia.project_firebase;

import android.text.format.DateFormat;
import android.util.Log;

import com.dgarcia.project_firebase.model.TestObject;
import com.dgarcia.project_firebase.services.DataBaseSvcFirebaseImpl;
import com.dgarcia.project_firebase.services.HttpAsyncTask;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TEST_HttpAsyncTask {

    @Test
    public void read_and_write_ToFirebase(){

        android.text.format.DateFormat dateFormat = new DateFormat();
        String dfString = "MM/dd/yy  hh:mm:ss a";  // date format string

        Log.e("STATUS", "START OF TEST");
        //new HttpAsyncTask().execute("https://regis-project.firebaseio.com/");
        HttpAsyncTask httpAsyncTask = new HttpAsyncTask();
        httpAsyncTask.execute("https://regis-project.firebaseio.com/");
        Log.e("STATUS", "END OF TEST");

    }
}
