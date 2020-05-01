package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.HistoryActivity;
import com.qcb.financemanage.PaymentsActivity;
import com.qcb.financemanage.R;

public class TransactionsHistoryMain extends AppCompatActivity {

    private Button btn_history_list, btn_history_graph;
    private String accID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sctivity_transactions_history_main);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        btn_history_list = findViewById(R.id.btn_history_transactions_list);
        btn_history_graph = findViewById(R.id.btn_history_transactions_graph);

        // Open List of transactions
        btn_history_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionsList.class);
                intent.putExtra("Account_ID", accID);
                startActivity(intent);
            }
        });

        // Open graph of transactions
        btn_history_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TransactionsGraph.class);
                intent.putExtra("Account_ID", accID);
                startActivity(intent);
            }
        });


    }
}
