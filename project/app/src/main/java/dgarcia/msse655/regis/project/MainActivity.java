package dgarcia.msse655.regis.project;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

import static android.support.v7.appcompat.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerList = (ListView)findViewById(R.id.navList); //Set nav drawer ListView
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout); //set to layout activity_main.xml
        mActivityTitle = getTitle().toString();                         //get current title

        addDrawerItems();   //call function to add items & config listView
        setUpDrawer();      //call function below

        //Show toggle switch
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    //To add items & configure navDrawer list, create a helper method that we can call
    private void addDrawerItems(){
        String[] osArray = getResources().getStringArray(R.array.navDrawerItems);   //get array items from strings.xml
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray); //set adapter to osArray
        mDrawerList.setAdapter(mAdapter);   //set ListView to osArray

        //onClick listener for na drawer items
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, (String)mDrawerList.getItemAtPosition(position), Toast.LENGTH_SHORT).show();//make toast of item clicked
            }
        });
    }// END OF addDrawerItems


    // We want to create a new ActionBarDrawerToggle instance that uses the context,
    // mDrawerLayout, and those two new string resources we added, and then we need to
    // implement two methods: onDrawerOpened() and onDrawerClosed():
    private void setUpDrawer(){
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(getResources().getString(R.string.nav_drawer_title));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

    }//END OF setUpDrawer

    //animate nav drawer hamburger
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    // keep sync when configuration of activity changes, i.e, portrait to landscape
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    // action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.myactionbar_menu, menu);

        return true;
    }

    // action bar item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId(); // get actionbar item clicked


        //nav drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
     /*   // If my action mar items were clicked, hide main activity action bar.
         if(id == R.id.first || id == R.id.second) {    // if the first or second id in menu_test.xml
            final String out = "Bye Action Bar", in = "Main actionbar is back!";
            Toast.makeText(getApplicationContext(), out, Toast.LENGTH_SHORT).show(); // make a toast
            getSupportActionBar().hide(); // hide bar/menu.

            final int time = 3000;

            // Pause for some time before I bring the bar back.
            Thread pause = new Thread(new Runnable() {  // start new thread
                @Override
                public void run () {
                    try {
                        Thread.sleep(time);             // sleep
                        runOnUiThread(new Runnable() {  // Thread stream to UI
                            @Override
                            public void run() {
                                getSupportActionBar().show();   // show bar/menu
                                Toast.makeText(getApplicationContext(), in, Toast.LENGTH_SHORT).show(); // make a toast
                            }
                        }); // END OF runOnUiThread

                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_SHORT);
                    }
                }
            });// END OF pause
            pause.start();
        }// END OF else if*/



        return super.onOptionsItemSelected(item);
    }
}
