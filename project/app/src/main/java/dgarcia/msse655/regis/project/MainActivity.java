package dgarcia.msse655.regis.project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.myactionbar_menu, menu);

        return true;
    }

    // action bar item selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId(); // get actionbar item clicked

        // If my action mar items were clicked, hide main activity action bar.
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
        }// END OF else if



        return super.onOptionsItemSelected(item);
    }
}
