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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Intent intent = getIntent();

        btn_transaction = findViewById(R.id.btn_history_transactions);
        btn_payments = findViewById(R.id.btn_history_payments);

        // TODO: add user ID to the intent
        btn_transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionsHistoryMain.class);
                //intent.putExtra("DESTINATION", "PaymentActivity");
                startActivity(intent);
            }
        });

        // TODO: add user ID to the intent
        btn_payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentsHistoryMain.class);
                //intent.putExtra("DESTINATION", "PaymentActivity");
                startActivity(intent);
            }
        });

    }
}
