package com.qcb.financemanage.payments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.HistoryActivity;
import com.qcb.financemanage.R;

public class MobilePaymentActivity extends AppCompatActivity {

    Button operator_tele2, operator_kcell, operator_activ, operator_beeline;
    String accID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_payment);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        operator_tele2 = findViewById(R.id.operator_tele2);
        operator_activ = findViewById(R.id.operator_activ);
        operator_beeline = findViewById(R.id.operator_beeline);
        operator_kcell = findViewById(R.id.operator_kcell);

        final Intent next_intent = new Intent(getApplicationContext(), MobilePaymentTransaction.class);
        next_intent.putExtra("Account_ID", accID);

        operator_tele2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_intent.putExtra("OPERATOR", "TELE2");
                startActivity(next_intent);
            }
        });

        operator_activ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_intent.putExtra("OPERATOR", "ACTIV");
                startActivity(next_intent);
            }
        });

        operator_kcell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_intent.putExtra("OPERATOR", "KCELL");
                startActivity(next_intent);
            }
        });

        operator_beeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_intent.putExtra("OPERATOR", "BEELINE");
                startActivity(next_intent);
            }
        });



    }
}
