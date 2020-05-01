package com.qcb.financemanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.history.PaymentsHistoryMain;
import com.qcb.financemanage.history.TransactionsHistoryMain;

public class HistoryActivity extends AppCompatActivity {

    private Button btn_transaction, btn_payments;
    private String accID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        btn_transaction = findViewById(R.id.btn_history_transactions);
        btn_payments = findViewById(R.id.btn_history_payments);


        btn_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionsHistoryMain.class);
                //intent.putExtra("DESTINATION", "PaymentActivity");
                intent.putExtra("Account_ID", accID);
                startActivity(intent);
            }
        });


        btn_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentsHistoryMain.class);
                //intent.putExtra("DESTINATION", "PaymentActivity");
                intent.putExtra("Account_ID", accID);
                startActivity(intent);
            }
        });

    }
}
