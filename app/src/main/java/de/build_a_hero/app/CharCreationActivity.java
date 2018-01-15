package de.build_a_hero.app;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CharCreationActivity extends AppCompatActivity {

    private static final String tag = "Text";
    ArrayAdapter<String> nameAdapter;
    //Layout: whole table
    //Header: most top row
    //Wert: total percentage of trait class
    //total points available that you can spend on traits
    private TextView availablePoints;

    private TableLayout handelnLayout;
    private TableRow handelnHeader;
    private TextView handelnWert;

    private TableLayout wissenLayout;
    private TableRow wissenHeader;
    private TextView wissenWert;

    private TableLayout interagLayout;
    private TableRow interagHeader;
    private TextView interagWert;

    private String charDetails = "";
    private String loadText;
    private Spinner nameSpinner;
    private Spinner genderSpinner;
    private ArrayList<View> formList;
    private ArrayList<Integer> spinnerPositions;

    private String[] gender = {"", "weiblich", "männlich", "anderes", "unbestimmt"};
    private List<String> male;
    private List<String> female;
    private List<String> allNames;

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

        nameSpinner = findViewById(R.id.name);
        genderSpinner = findViewById(R.id.gender);

        formList = new ArrayList<>();
        spinnerPositions = new ArrayList<>();

        allNames = new ArrayList<>();

        NamesURL namesUrl = new NamesURL();

        try {
            namesUrl.read();
            male = namesUrl.getMale();
            female = namesUrl.getFemale();
            allNames = namesUrl.getAllNames();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(CharCreationActivity.this, android.R.layout.simple_spinner_item, gender);
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String gender = parent.getItemAtPosition(position).toString();

                if (gender.equals("weiblich")) {
                    nameAdapter = new ArrayAdapter<>(CharCreationActivity.this, android.R.layout.simple_spinner_item, female);
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    nameSpinner.setAdapter(nameAdapter);
                } else if (gender.equals("männlich")) {
                    nameAdapter = new ArrayAdapter<>(CharCreationActivity.this, android.R.layout.simple_spinner_item, male);
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    nameSpinner.setAdapter(nameAdapter);
                } else if (gender.equals("anders") || gender.equals("unbestimmt")) {
                    nameAdapter = new ArrayAdapter<>(CharCreationActivity.this, android.R.layout.simple_spinner_item, allNames);
                    nameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    nameSpinner.setAdapter(nameAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //configureCancelButton();
        // configures + and -, so it adds to or substracts 10 of the current Value
        configureValueButton();
        // configures the button "Fertig" to save the character
        configureSaveButton();
        // configures Load Button, not necessary here
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

                                                        if (availPoints == 0) {
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

                    if (availPoints == 0) {
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

                    if (availPoints == 0) {
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

    private void configureForms() {

        //formList

        RelativeLayout compLayout = findViewById(R.id.compLayout);

        for (int i = 0; i < compLayout.getChildCount(); i++) {
            Log.v(tag, Integer.toString(compLayout.getChildCount()));
            Object child = compLayout.getChildAt(i);

            if (child instanceof Spinner) {
                Spinner spinner = (Spinner) child;
                formList.add(spinner);

            } else if (child instanceof EditText) {
                formList.add((EditText) child);

            } else if (child instanceof TableLayout) {
                TableLayout lyo = (TableLayout) child;
                for (int j = 1; j < lyo.getChildCount(); j++) {
                    TableRow tr = (TableRow) (lyo.getChildAt(j));
                    formList.add(tr.getChildAt(0));
                    formList.add(tr.getChildAt(1));
                }
            }
        }

        return;
    }

    private void configureSaveButton() {

        Button saveButton = findViewById(R.id.finishCreation);
        saveButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              //Log.i(tag, text);

                                              charDetails = "";
                                              configureForms();


                                              for (int i = 0; i < formList.size(); i++) {

                                                  View input = formList.get(i);

                                                  if (input instanceof Spinner) {
                                                      Spinner sp = (Spinner) input;
                                                      if (sp.getSelectedItem() != null) {
                                                          charDetails = charDetails + sp.getSelectedItem().toString() + ";";
                                                          spinnerPositions.add(sp.getSelectedItemPosition());
                                                      } else {
                                                          charDetails = charDetails + "null;";
                                                      }

                                                  } else if (input instanceof EditText) {
                                                      EditText et = (EditText) input;
                                                      if (et.getText() == null || et.getText().equals("")) {
                                                          charDetails = charDetails + "null;";
                                                      } else {
                                                          charDetails = charDetails + et.getText() + ";";
                                                      }
                                                  }


                                              }

                                              if (charDetails.length() > 0 && charDetails.charAt(charDetails.length() - 1) == ';') {
                                                  charDetails = charDetails.substring(0, charDetails.length() - 1);
                                              }

                                              save("androidsavetexttest.txt", charDetails);
                                              //Log.i(tag, getFilesDir().toString());


                                              //EditText temp = (EditText) formList.get(1);
                                              //temp.setText(loadTxt);
                                          }
                                      }
        );
    }

    private void configureLoadButton() {

        Button loadButton = findViewById(R.id.cancelCreation);
        loadButton.setOnClickListener(new View.OnClickListener() {

                                          @Override
                                          public void onClick(View view) {
                                              String loadTxt = load("androidsavetexttest.txt");


                                              String[] inputs = load("androidsavetexttest.txt").split(";");

                                              int spinnerCount = 0;


                                              for (int i = 0; i < formList.size(); i++) {

                                                  View v = formList.get(i);

                                                  if (v instanceof Spinner) {
                                                      if(spinnerPositions.size() != 0){
                                                          Spinner sp = (Spinner) v;
                                                          sp.setSelection(spinnerPositions.get(spinnerCount));
                                                          spinnerCount++;
                                                      }
                                                  } else if (v instanceof EditText) {
                                                      EditText et = (EditText) v;
                                                      et.setText(inputs[i]);
                                                  }
                                              }
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



