package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentsList extends AppCompatActivity {

    private List list = new ArrayList();
    private ListView listView;
    private ArrayAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_list);

        Intent intent = getIntent();

        listView = (ListView) findViewById(R.id.payments_history_list_view);

        for(int i = 0; i < 20; i++){
            list.add("Transaction" + i);
        }


        adapter = new ArrayAdapter(PaymentsList.this, android.R.layout.simple_expandable_list_item_1, list);
        listView.setAdapter(adapter);


    }
}
