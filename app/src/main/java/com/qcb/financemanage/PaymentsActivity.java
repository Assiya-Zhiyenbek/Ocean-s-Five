package com.qcb.financemanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.payments.MobilePaymentActivity;

public class PaymentsActivity extends AppCompatActivity {

    Button mobile, communale, internet, fines, games, credits, food;
    String accID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        Log.e("ACCOUNT_ID", "ACCOUNT ID is: " + accID);

        mobile = findViewById(R.id.pay_telephone);
        communale = findViewById(R.id.pay_kommunalka);
        internet = findViewById(R.id.pay_internet);
        fines = findViewById(R.id.pay_fines);
        games = findViewById(R.id.pay_games);
        credits = findViewById(R.id.pay_credits);
        food = findViewById(R.id.pay_food);

        mobile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Passing Account ID through Activities;
                Intent intent = new Intent(getApplicationContext(), MobilePaymentActivity.class);
                intent.putExtra("Account_ID", accID);
                startActivity(intent);
            }
        });

    }
}
