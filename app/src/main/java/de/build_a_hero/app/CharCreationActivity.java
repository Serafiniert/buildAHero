package de.build_a_hero.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CharCreationActivity extends AppCompatActivity {

    private TextView availablePoints;
    private EditText handeln1;
    private int lastHandeln;
    private int currentHandeln;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation);

        configureCancelButton();

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
}

