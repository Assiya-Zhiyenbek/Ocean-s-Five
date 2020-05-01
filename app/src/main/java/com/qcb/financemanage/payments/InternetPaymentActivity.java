package com.qcb.financemanage.payments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.HistoryActivity;
import com.qcb.financemanage.R;

public class InternetPaymentActivity extends AppCompatActivity {

    Button provider_kazakhtelecom, provider_beeline, provider_Altel, provider_megaline;
    String accID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_payment);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        provider_kazakhtelecom = findViewById(R.id.kazakhtelecom);
        provider_beeline = findViewById(R.id.beeline);
        provider_Altel = findViewById(R.id.Altel_4G);
        provider_megaline = findViewById(R.id.megaline);

        final Intent next_intent = new Intent(getApplicationContext(), CommunalePaymentActivity.class);
        next_intent.putExtra("Account_ID", accID);

        provider_kazakhtelecom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_intent.putExtra("PROVIDER", "KAZAKHTELECOM");
                startActivity(next_intent);
            }
        });

        provider_Altel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_intent.putExtra("PROVIDER", "ALTEL_4G");
                startActivity(next_intent);
            }
        });

        provider_beeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_intent.putExtra("PROVIDER", "BEELINE");
                startActivity(next_intent);
            }
        });

        provider_megaline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                next_intent.putExtra("PROVIDER", "MEGALINE");
                startActivity(next_intent);
            }
        });



    }
}
