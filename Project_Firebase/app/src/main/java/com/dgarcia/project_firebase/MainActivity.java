package com.dgarcia.project_firebase;

import android.support.v4.app.Fragment;


public class MainActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment(){
        return new MainFragment();
    }
}
