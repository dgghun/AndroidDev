package com.dgarcia.project_firebase.services;

import com.dgarcia.project_firebase.firebase4j.service.Firebase;
import com.dgarcia.project_firebase.model.TestObject;



import java.util.List;


public class DataBaseSvcHttpImpl implements IDataBaseSvc {

    Firebase t;
    public DataBaseSvcHttpImpl(){

    }

    @Override
    public TestObject create(TestObject TestObject) {
        return null;
    }

    @Override
    public List<TestObject> retrieveAllTestObjects() {
        return null;
    }

    @Override
    public TestObject update(TestObject testObject) {
        return null;
    }

    @Override
    public TestObject delete(TestObject testObject) {
        return null;
    }
}
