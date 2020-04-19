package com.qcb.financemanage;


/* This activity will be loaded when transaction is finished */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class TransactionResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_results);

        Intent intent = getIntent();
//        String sID = intent.getStringExtra("SENDER_ACCOUNT_ID");
//        String rID = intent.getStringExtra("RECEIVER_ACCOUNT_ID");
//        String money



    }
}
