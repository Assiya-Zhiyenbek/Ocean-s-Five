package com.qcb.financemanage.payments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.R;

public class PaymentOperationResult extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_operation_result);

        Intent intent = getIntent();

    }
}
