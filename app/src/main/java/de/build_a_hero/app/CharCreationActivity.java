package de.build_a_hero.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CharCreationActivity extends AppCompatActivity {

    //total points available that you can spend on traits
    private TextView availablePoints;

    //Layout: whole table
    //Header: most top row
    //Wert: total percentage of trait class

    private TableLayout handelnLayout;
    private TableRow handelnHeader;
    private TextView handelnWert;

    private TableLayout wissenLayout;
    private TableRow wissenHeader;
    private TextView wissenWert;

    private TableLayout interagLayout;
    private TableRow interagHeader;
    private TextView interagWert;


    private static final String tag = "Text";

    private String charDetails = "";
    private String loadText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation);





        handelnLayout = findViewById(R.id.tableHandeln);
        handelnHeader = (TableRow) handelnLayout.getChildAt(0);
        handelnWert = (TextView) handelnHeader.getChildAt(1);


        wissenLayout = findViewById(R.id.tableWissen);
        wissenHeader = (TableRow) wissenLayout.getChildAt(0);
        wissenWert = (TextView) wissenHeader.getChildAt(1);


        interagLayout = findViewById(R.id.tableInterag);
        interagHeader = (TableRow) interagLayout.getChildAt(0);
        interagWert = (TextView) interagHeader.getChildAt(1);


        availablePoints = findViewById(R.id.availPointsNum);

        configureCancelButton();
        configureValueButton();
        configureSaveButton();
        configureLoadButton();


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

    private void configureValueButton() {



        //final Button addButton = findViewById(R.id.plusButton);
        for (int i = 1; i < 6; i++) {
            final TableRow handelnRow = (TableRow) handelnLayout.getChildAt(i);
            Button handelnAddButton = (Button) handelnRow.getChildAt(3);
            Button handelnDecreaseButton = (Button) handelnRow.getChildAt(2);


            handelnAddButton.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     int availPoints = Integer.parseInt(availablePoints.getText().toString());

                    if(availPoints == 0){
                        availablePoints.setTextColor(Color.parseColor("#ffcc0000"));


                    } else {


                        //TableRow row = (TableRow) addButton.getParent();
                        EditText cell = (EditText) handelnRow.getChildAt(1);
                        int currentHandeln = Integer.parseInt(cell.getText().toString());
                        currentHandeln = currentHandeln + 10;
                        int klassenWert = Integer.parseInt(handelnWert.getText().toString());
                        if (currentHandeln == 80) {
                            klassenWert = klassenWert + 10;
                        }

                        klassenWert = klassenWert + 1;


                        availablePoints.setText(Integer.toString(availPoints - 10));
                        cell.setText(Integer.toString(currentHandeln));
                        handelnWert.setText(Integer.toString(klassenWert));
                    }

                                                    }
                                                }
            );

            handelnDecreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    availablePoints.setTextColor(Color.parseColor("#000000"));

                    EditText cell = (EditText) handelnRow.getChildAt(1);
                    int currentHandeln = Integer.parseInt(cell.getText().toString());
                    currentHandeln = currentHandeln - 10;
                    int klassenWert = Integer.parseInt(handelnWert.getText().toString());

                    if (currentHandeln == 70) {
                        klassenWert = klassenWert - 10;
                    }

                    klassenWert = klassenWert - 1;

                    int availPoints = Integer.parseInt(availablePoints.getText().toString());

                    availablePoints.setText(Integer.toString(availPoints + 10));
                    cell.setText(Integer.toString(currentHandeln));
                    handelnWert.setText(Integer.toString(klassenWert));


                }
            });

            final TableRow wissenRow = (TableRow) wissenLayout.getChildAt(i);
            Button wissenAddButton = (Button) wissenRow.getChildAt(3);
            Button wissenDecreaseButton = (Button) wissenRow.getChildAt(2);


            wissenAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int availPoints = Integer.parseInt(availablePoints.getText().toString());

                    if(availPoints == 0){
                        availablePoints.setTextColor(Color.parseColor("#ffcc0000"));


                    } else {


                        EditText cell = (EditText) wissenRow.getChildAt(1);
                        int currentWissen = Integer.parseInt(cell.getText().toString());
                        currentWissen = currentWissen + 10;
                        int klassenWert = Integer.parseInt(wissenWert.getText().toString());
                        if (currentWissen == 80) {
                            klassenWert = klassenWert + 10;
                        }

                        klassenWert = klassenWert + 1;



                        availablePoints.setText(Integer.toString(availPoints - 10));
                        cell.setText(Integer.toString(currentWissen));
                        wissenWert.setText(Integer.toString(klassenWert));
                    }


                }
            });

            wissenDecreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    availablePoints.setTextColor(Color.parseColor("#000000"));

                    EditText cell = (EditText) wissenRow.getChildAt(1);
                    int currentWissen = Integer.parseInt(cell.getText().toString());
                    currentWissen = currentWissen - 10;
                    int klassenWert = Integer.parseInt(wissenWert.getText().toString());

                    if (currentWissen == 70) {
                        klassenWert = klassenWert - 10;
                    }

                    klassenWert = klassenWert - 1;

                    int availPoints = Integer.parseInt(availablePoints.getText().toString());

                    availablePoints.setText(Integer.toString(availPoints + 10));
                    cell.setText(Integer.toString(currentWissen));
                    wissenWert.setText(Integer.toString(klassenWert));
                }
            });


            final TableRow interagRow = (TableRow) interagLayout.getChildAt(i);
            Button interagAddButton = (Button) interagRow.getChildAt(3);
            Button interagDecreaseButton = (Button) interagRow.getChildAt(2);


            interagAddButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int availPoints = Integer.parseInt(availablePoints.getText().toString());

                    if(availPoints == 0){
                        availablePoints.setTextColor(Color.parseColor("#ffcc0000"));


                    } else {

                        EditText cell = (EditText) interagRow.getChildAt(1);
                        int currentInterag = Integer.parseInt(cell.getText().toString());
                        currentInterag = currentInterag + 10;
                        int klassenWert = Integer.parseInt(interagWert.getText().toString());
                        if (currentInterag == 80) {
                            klassenWert = klassenWert + 10;
                        }

                        klassenWert = klassenWert + 1;


                        availablePoints.setText(Integer.toString(availPoints - 10));
                        cell.setText(Integer.toString(currentInterag));
                        interagWert.setText(Integer.toString(klassenWert));
                    }

                }
            });

            interagDecreaseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    availablePoints.setTextColor(Color.parseColor("#000000"));

                    EditText cell = (EditText) interagRow.getChildAt(1);
                    int currentInterag = Integer.parseInt(cell.getText().toString());
                    currentInterag = currentInterag - 10;
                    int klassenWert = Integer.parseInt(interagWert.getText().toString());

                    if (currentInterag == 70) {
                        klassenWert = klassenWert - 10;
                    }

                    klassenWert = klassenWert - 1;

                    int availPoints = Integer.parseInt(availablePoints.getText().toString());

                    availablePoints.setText(Integer.toString(availPoints + 10));
                    cell.setText(Integer.toString(currentInterag));
                    interagWert.setText(Integer.toString(klassenWert));
                }
            });
        }


    }

    private void configureSaveButton() {

        Button saveButton = findViewById(R.id.finishCreation);
        saveButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              //Log.i(tag, text);

                                              charDetails = "";


                                              ArrayList<EditText> myEditTextList = new ArrayList<EditText>();

                                              for( int i = 0; i < handelnLayout.getChildCount(); i++ ) {

                                                  Object child = handelnLayout.getChildAt(i);

                                                  if ( child instanceof EditText){

                                                      myEditTextList.add((EditText) handelnLayout.getChildAt(i));
                                                      EditText cell = (EditText) ((TableRow) child).getChildAt(0);

                                                      charDetails = charDetails + cell.getText().toString();



                                                  } else if(child instanceof TableRow){

                                                        EditText cell = (EditText) ((TableRow) child).getChildAt(0);

                                                        charDetails = charDetails + cell.getText().toString();

                                                        cell = (EditText) ((TableRow) child).getChildAt(1);

                                                        charDetails = charDetails + cell.getText().toString();
                                                  }

                                              }

                                              save("androidsavetexttest.txt", charDetails);
                                              //Log.i(tag, getFilesDir().toString());
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
                                              //testLoad.setText(loadText);
                                          }
                                      }
        );
    }


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

