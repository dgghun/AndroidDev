package regis.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    String nameStatic = new String();

    public void touchMe(View view){
        TextView tv = (TextView)findViewById(R.id.TV_paragraph);
        TextView name = (TextView)findViewById(R.id.TV_enterName);


        if(name.getText().toString().isEmpty())
            tv.setText("Whats your name?");
        else if(!nameStatic.equals(name.getText().toString()))
            tv.setText("Whats up " + name.getText() + "!");
        else
            tv.setText("stop touching me!!");

        nameStatic = name.getText().toString();
    }
}
