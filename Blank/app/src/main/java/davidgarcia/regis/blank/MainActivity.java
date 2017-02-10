package davidgarcia.regis.blank;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_test, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id == R.id.first || id == R.id.second) {    // if the first or second id in menu_test.xml
           final String out = "Bye Action Bar", in = "I'm back!";
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
