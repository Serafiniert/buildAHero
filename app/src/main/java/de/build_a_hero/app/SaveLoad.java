package de.build_a_hero.app;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveLoad extends Activity {

    public void save() {

        String FILENAME = "hello_file";
        String string = "hello world!";

        FileOutputStream fos;

        try {
            fos = openFileOutput("test.txt", Context.MODE_PRIVATE);
            fos.write(string.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load() {

        String string = "hello world!";

        FileInputStream fis;

        try {
            fis = openFileInput("test.txt");
            fis.read();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
