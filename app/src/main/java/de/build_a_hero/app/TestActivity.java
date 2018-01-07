package de.build_a_hero.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class TestActivity extends AppCompatActivity {

    private static final String tag = "MyActivity";
    private EditText testText;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        testText = findViewById(R.id.testText);

        testText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    text = v.getText().toString();

                    Log.i(tag, text);

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
                                              Log.i(tag, text);
                                          }
                                      }
        );
    }

    private void configureLoadButton() {

        Button loadButton = findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              finish();
                                          }
                                      }
        );
    }
}
