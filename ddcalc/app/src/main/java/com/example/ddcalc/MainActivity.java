package com.example.ddcalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etKm;
    private EditText etMileage;
    private EditText etGas;
    private EditText etEarnings;
    private TextView dollarPerKmText;
    private TextView resultText;
    private CheckBox returnTrip;

    private String dollarS;
    private String dollarSR;
    private EditText etDollarPerKm;

    private final int RED = 0xFFFF0000;
    private final int GREEN = 0xff458B00;

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() <= 0;
    }
    private boolean isEmptyAll()
    {
        return isEmpty(etKm) || isEmpty(etMileage) || isEmpty(etGas) || isEmpty(etEarnings);
    }
    private boolean isGood(float f1, float f2, float f3)
    {
        float f = (f1/f2);
        int aux = (int) (f * 100);
        f = aux / 100f;
        if (f3 == 0){
            return f;
        }else
            return f >= (f3 - 0.1);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find result text
        resultText = (TextView) findViewById(R.id.resultantText);


        //listen to calculate button for clicks if user has input
            Button calcB = (Button) findViewById(R.id.calc_button);
            calcB.setOnClickListener(this);
            CheckBox checkReturn = (CheckBox) findViewById(R.id.checkBoxReturn);
            checkReturn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        //get user input
        etKm = (EditText) findViewById(R.id.editText_Km);
        etMileage = (EditText) findViewById(R.id.editText_KpL);
        etGas = (EditText) findViewById(R.id.editText_GasPrice);
        etEarnings = (EditText) findViewById(R.id.editText_DeliveryCost);
        dollarPerKmText = (TextView) findViewById(R.id.dollarPerKmText);
        returnTrip = (CheckBox) findViewById(R.id.checkBoxReturn);
        etDollarPerKm = (EditText) findViewById(R.id.editText_dollarPerKm);


        if(!isEmptyAll())
        {

            //convert user input to float number
            float fKm = Float.parseFloat(etKm.getText().toString());
            float fMileage = Float.parseFloat(etMileage.getText().toString());
            float fGas = Float.parseFloat(etGas.getText().toString());
            float fEarn = Float.parseFloat(etEarnings.getText().toString());
            float fDollarPerKm = 0;
           if (!isEmpty(etDollarPerKm)) {
               fDollarPerKm = Float.parseFloat(etDollarPerKm.getText().toString());
           }

            //core math calculation
            float percent = fKm / fMileage;
            float res = percent * fGas;

            //removes the unnecessary decimals
            int aux = (int) (res * 100);
            float resultP = aux / 100f;

            float worth = (fEarn - resultP); //calculate the net profit
            float returnWorth = (fEarn - (resultP*2));
            //convert float back to string
            String sRes = "$ " + String.valueOf(worth);
            String cRes = "$ " + String.valueOf(returnWorth);
            String dollarS = "$" + String.valueOf(worth / fKm);
            String dollarSR = "$" + String.valueOf((returnWorth / fKm));



            if(returnTrip.isChecked()) {
                resultText.setText((cRes));
                dollarPerKmText.setText(dollarSR);
                if (!isGood(returnWorth, fKm, fDollarPerKm))
                {
                    resultText.setTextColor(RED);
                    dollarPerKmText.setTextColor(RED);
                }
                else if (isGood(returnWorth, fKm, fDollarPerKm))
                {
                    resultText.setTextColor(GREEN);
                    dollarPerKmText.setTextColor(GREEN);
                }
            }
            if (!returnTrip.isChecked()){
                resultText.setText((sRes));
                dollarPerKmText.setText(dollarS);
                if (!isGood(worth, fKm, fDollarPerKm))
                {
                    resultText.setTextColor(RED);
                    dollarPerKmText.setTextColor(RED);
                }
                else if (isGood(worth, fKm,fDollarPerKm))
                {
                    resultText.setTextColor(GREEN);
                    dollarPerKmText.setTextColor(GREEN);
                }
            }
        }
        else if (!(view.getId() == R.id.checkBoxReturn) && isEmptyAll()) {
            resultText.setText("More info Needed!");
            dollarPerKmText.setText("");
        }
    }

}
