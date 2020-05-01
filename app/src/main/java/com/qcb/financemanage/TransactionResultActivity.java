package com.qcb.financemanage;


/* This activity will be loaded when transaction is finished */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TransactionResultActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button back_to_main;
    //private CollectionReference transactions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_results);

        Intent intent = getIntent();
        String sID = intent.getStringExtra("SENDER_ACCOUNT_ID");
        String rID = intent.getStringExtra("RECEIVER_ACCOUNT_ID");
        int money = intent.getIntExtra("MONEY_TO_TRANSFER", 0);

        back_to_main = findViewById(R.id.btn_transactions_back_to_main);

        // Update one field, creating the document if it does not already exist.
        Map<String, Object> data = new HashMap<>();
        data.put("sender", sID);
        data.put("receiver", rID);
        data.put("amount", money);
        data.put("date", new Date());
        data.put("sender_id", sID);

        String uniqueID = "tr_" + UUID.randomUUID().toString();

        //Log.e("MONEY", "Money Amount to transfer: " + money);

        db.collection("transactions").document(uniqueID)
                .set(data, SetOptions.merge());


        back_to_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


    }
}
