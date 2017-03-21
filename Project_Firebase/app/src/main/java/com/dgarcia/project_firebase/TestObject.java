package com.dgarcia.project_firebase;


import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

public class TestObject {

    private int id;
    private Date mDate;
    private static int count = 1;

    public TestObject(){
        id = count;
        mDate = new Date();
        count++;
    }

    public int getId() {
        return id;
    }

    public Date getmDate() {
        return mDate;
    }

    public static int getCount() {
        return count;
    }
}
