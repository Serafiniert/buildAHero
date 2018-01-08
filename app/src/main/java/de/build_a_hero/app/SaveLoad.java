package de.build_a_hero.app;

import android.app.Activity;
import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveLoad extends Activity {

    public void save(String filename, String text) {

        //String FILENAME = "hello_file";
        //String string = "hello world!";

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

    public void load(String filename) {

        //String string = "hello world!";

        FileInputStream fis;

        try {
            fis = openFileInput(filename);
            fis.read();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
