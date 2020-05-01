package com.qcb.financemanage.payments;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.R;

public class InternetPaymentsACtivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_payment);

        Intent intent = getIntent();

    }
}
