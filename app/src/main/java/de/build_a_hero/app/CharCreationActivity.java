package de.build_a_hero.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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

    private EditText handeln1;
    private int lastHandeln;
    private int currentHandeln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation);


        configureCancelButton();


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
        handeln1 = findViewById(R.id.HandelnPercent1);
        handeln1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;

                if (i == EditorInfo.IME_ACTION_DONE) {
                    currentHandeln = Integer.parseInt(textView.getText().toString());
                    int pts = Integer.parseInt(availablePoints.getText().toString()) + lastHandeln - currentHandeln;

                    availablePoints.setText(Integer.toString(pts));
                    lastHandeln = currentHandeln;
                }
                return handled;
            }
        });

        configureValueButton();


    }

    /*TextView availablePoints;
    TextView handelnPercent;


    TableLayout handelnLayout;

    EditText[] handelnArr = new EditText[5];
    EditText[] wissenArr = new EditText[5];
    EditText[] interArr = new EditText[5];

    int currHandeln;



    int[] lastHandelnArr = new int[]{0,0,0,0,0};

    String[] talentIDs = new String[]{"HandelnPercent1","HandelnPercent2","HandelnPercent3","HandelnPercent4","HandelnPercent5",
                                        "WissenPercent1","WissenPercent2","WissenPercent3","WissenPercent4","WissenPercent5",
                                        "InteragierenPercent1","InteragierenPercent2","InteragierenPercent3","InteragierenPercent4","InteragierenPercent5",};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        availablePoints = findViewById(R.id.availPointsNum);
        handelnPercent = findViewById(R.id.handelnPercent);



        handelnLayout = findViewById(R.id.handelnTable);

        for(int k = 1; k<6;k++){

            TableRow row = (TableRow) handelnLayout.getChildAt(k);
            handelnArr[k-1] = (EditText) row.getChildAt(1);
            final EditText handelnPerc1 = handelnArr[k-1];
            final int lastHandeln = lastHandelnArr[k-1];
            handelnPerc1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                    boolean handled = false;

                    if(i == EditorInfo.IME_ACTION_DONE || i == EditorInfo.IME_ACTION_SEND){
                        //currHandeln =  Integer.parseInt(textView.getText().toString());

                        //int[] sums = calcTablePercentage(handelnLayout);

                        int sumX = 0;
                        int sum = 0;

                        for(int u = 1; u<6; u++){
                            TableRow row = (TableRow) handelnLayout.getChildAt(u);
                            EditText cell = (EditText) row.getChildAt(1);
                            int cellValue = Integer.parseInt(cell.getText().toString());
                            if(cellValue >= 80){
                                sumX = sumX + cellValue + 100;
                                sum = sum + cellValue;
                            } else{
                                sumX = sumX + cellValue;
                                sum = sum + cellValue;
                            }

                        }

                        int pts = Integer.parseInt(availablePoints.getText().toString()) + lastHandeln - currHandeln;

                        handelnPercent.setText(Integer.toString(sumX/10));

                        availablePoints.setText(Integer.toString(sum));

                        /*handelnPercent.setText(Integer.toString(pts/10));
                        availablePoints.setText(Integer.toString(pts));

                    }
                    return handled;                }
            });

            lastHandelnArr[k-1] = currHandeln;

        }


    }

    public int[] calcTablePercentage(TableLayout table){
        int sumX = 0;
        int sum = 0;

        for(int i = 1; i<6; i++){
            TableRow row = (TableRow) table.getChildAt(i);
            EditText cell = (EditText) row.getChildAt(1);
            int cellValue = Integer.parseInt(cell.getText().toString());
            if(cellValue >= 80){
                sumX = sumX + cellValue + 100;
                sum = sum + cellValue;
            } else{
                sumX = sumX + cellValue;
                sum = sum + cellValue;
            }

        }
        int[] sumArr = new int[]{sumX/10, sum};
        return sumArr;
    }*/

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


        /*handelnLayout= findViewById(R.id.tableHandeln);

        availablePoints = findViewById(R.id.availPointsNum);
        handeln1 = findViewById(R.id.HandelnPercent1);
        handeln1.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                boolean handled = false;

                if (i == EditorInfo.IME_ACTION_DONE) {
                    currentHandeln = Integer.parseInt(textView.getText().toString());
                    int pts = Integer.parseInt(availablePoints.getText().toString()) + lastHandeln - currentHandeln;

                    availablePoints.setText(Integer.toString(pts));
                    lastHandeln = currentHandeln;
                }


                return handled;
            }
        });*/


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

}

