package com.dgarcia.project_firebase;


import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

public class TestObject {

    private int id;
    private String Date;

    public TestObject(){

    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "TestObject{" +
                "id=" + id +
                ", mDate=" + Date +
                '}';
    }
}
