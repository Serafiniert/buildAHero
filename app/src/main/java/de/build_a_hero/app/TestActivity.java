package de.build_a_hero.app;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestActivity extends AppCompatActivity {

    private static final String tag = "Text";
    private EditText testText;
    private TextView testLoad;
    private String text;
    private String loadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testText = findViewById(R.id.testText);
        testLoad = findViewById(R.id.loadData);


        testText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    text = v.getText().toString();

                    handled = true;
                }
                return handled;
            }
        });
        configureSaveButton();
        configureLoadButton();
    }

    private void configureSaveButton() {

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              //Log.i(tag, text);

                                              save("androidsavetexttest.txt", text);
                                              Log.i(tag, getFilesDir().toString());
                                          }
                                      }
        );
    }

    private void configureLoadButton() {

        Button loadButton = findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              loadText = load("androidsavetexttest.txt");
                                              testLoad.setText(loadText);
                                          }
                                      }
        );
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    public void save(String filename, String text) {

        FileOutputStream fos;

        try {
            fos = openFileOutput(filename, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("TryWithIdenticalCatches")
    public String load(String filename) {

        String text = null;
        FileInputStream fis;

        try {
            fis = openFileInput(filename);
            byte[] dataArray = new byte[fis.available()];

            while (fis.read(dataArray) != -1) {
                text = new String(dataArray);
            }
            fis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}