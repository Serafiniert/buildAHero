package de.build_a_hero.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class StartActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_start);

        configureNextButton();

        InputStream inputStream = getResources().openRawResource(R.raw.mitte);
        CSVFile csv = new CSVFile(inputStream);
        csv.read();

        List<String> female = csv.getFemale();
        List<String> male = csv.getMale();

        for(int i = 0; i < female.size(); i++){
            System.out.println(i + " | " + female.get(i));
        }

        for(int i = 0; i < male.size(); i++){
            System.out.println(i + " | " + male.get(i));
        }
    }

    private void configureNextButton() {

        Button nextButton = findViewById(R.id.startButton);
        nextButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              startActivity(new Intent(StartActivity.this, MenuActivity.class));
                                          }
                                      }
        );
    }

    public void buttonOnClick(View v) {
        Button button = (Button) v;
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
