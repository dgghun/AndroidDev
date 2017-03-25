package com.dgarcia.project_firebase;

import android.text.format.DateFormat;
import android.util.Log;

import com.dgarcia.project_firebase.model.TestObject;
import com.dgarcia.project_firebase.services.DataBaseSvcFirebaseImpl;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class TEST_DataBaseSvcFirebaseImpl {

    @Test
    public void read_and_write_ToFirebase(){

        android.text.format.DateFormat dateFormat = new DateFormat();
        String dfString = "MM/dd/yy  hh:mm:ss a";  // date format string


        DataBaseSvcFirebaseImpl dataBaseSvcFirebase = new DataBaseSvcFirebaseImpl("Davids Objects");
        dataBaseSvcFirebase.startListeners();

        TestObject testObject = new TestObject(1, dateFormat.format(dfString, new Date()).toString());
        TestObject returnedObject = dataBaseSvcFirebase.create(testObject);
        Log.e("TEST", "ReadWrite - Add (" + returnedObject + ") to database");

        dataBaseSvcFirebase.stopListeners();
    }
}
