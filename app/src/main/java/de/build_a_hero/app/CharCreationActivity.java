package de.build_a_hero.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CharCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charcreation);

        configureCancelButton();
    }

    private void configureCancelButton() {

        Button cancelButton = findViewById(R.id.cancelCreation);
        cancelButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              finish();
                                          }
                                      }
        );
    }
}
