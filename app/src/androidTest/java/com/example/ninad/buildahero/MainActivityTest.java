package com.example.ninad.buildahero;

import android.app.Activity;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.build_a_hero.app.StartActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Tim on 17.01.2018.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule public final ActivityRule<StartActivity> start = new ActivityRule<>(StartActivity.class);

    @Test
    public void shouldBeAbleToLaunchStartScreen(){
        onView(withText("Los gehts!")).check(ViewAssertions.matches(isDisplayed()));
    }
}
