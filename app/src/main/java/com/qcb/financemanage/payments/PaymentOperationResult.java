package com.qcb.financemanage.payments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.qcb.financemanage.MainActivity;
import com.qcb.financemanage.R;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PaymentOperationResult extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_operation_result);

        Intent intent = getIntent();


        String payer = intent.getStringExtra("payer");
        String pay_for = intent.getStringExtra("pay_for");
        String type = intent.getStringExtra("type");
        String payer_id = intent.getStringExtra("payer_id");
        long money =  intent.getLongExtra("amount", 0);
        Date date = new Date();

        //Log.e("NEWLCASSSMONEY", "final amout is " + money);

        // Update one field, creating the document if it does not already exist.
        Map<String, Object> data = new HashMap<>();
        data.put("payer", payer);
        data.put("pay_for", pay_for);
        data.put("payer_id", payer_id);
        data.put("amount", money);
        data.put("type", type);
        data.put("date", date);


        String uniqueID = "pmt_" + UUID.randomUUID().toString();

        //Log.e("MONEY", "Money Amount to transfer: " + money);

        db.collection("payments").document(uniqueID)
                .set(data, SetOptions.merge());


        Button back_to_main = findViewById(R.id.btn_payments_back_to_main);
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
