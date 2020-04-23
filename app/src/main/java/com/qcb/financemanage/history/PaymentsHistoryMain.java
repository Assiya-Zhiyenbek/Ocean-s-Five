package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.R;

public class PaymentsHistoryMain extends AppCompatActivity {

    private Button btn_history_list, btn_history_graph, btn_history_chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sctivity_payments_history_main);

        Intent intent = getIntent();


        btn_history_chart = findViewById(R.id.btn_payments_history_show_chart);
        btn_history_graph = findViewById(R.id.btn_payments_history_show_graph);
        btn_history_list = findViewById(R.id.btn_payments_history_show_list);

        btn_history_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentsChart.class);
                //intent.putExtra("DESTINATION", "PaymentActivity");
                startActivity(intent);
            }
        });

        btn_history_graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentsGraph.class);
                //intent.putExtra("DESTINATION", "PaymentActivity");
                startActivity(intent);
            }
        });

        btn_history_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentsList.class);
                //intent.putExtra("DESTINATION", "PaymentActivity");
                startActivity(intent);
            }
        });


    }
}
