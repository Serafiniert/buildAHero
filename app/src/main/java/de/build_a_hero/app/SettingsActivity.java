package de.build_a_hero.app;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.support.v4.app.Fragment;



import com.example.ninad.buildahero.ContactFragment;
import com.example.ninad.buildahero.CreditFragment;


public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        configureBackButton();

    }

    private void configureBackButton() {

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              finish();
                                          }
                                      }
        );
    }

    public void changeFragment(View view){
        Fragment fragment;

        if(view == findViewById(R.id.contactButton)){
            fragment = new ContactFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.settingFragment,fragment);
            ft.commit();
        }
        if(view == findViewById(R.id.creditButton)){
            fragment = new CreditFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.settingFragment,fragment);
            ft.commit();
        }
    }
}
