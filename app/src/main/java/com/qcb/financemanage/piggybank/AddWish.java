package com.qcb.financemanage.piggybank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qcb.financemanage.R;

import java.util.HashMap;
import java.util.Map;

public class AddWish extends AppCompatActivity {

    EditText addGoal, addGoalPrice;
    Button add;
    String accID;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference piggy_bank = db.collection("piggy_bank");

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_wish);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        addGoal = findViewById(R.id.edit_text_add_goal);
        addGoalPrice = findViewById(R.id.edit_text_add_goal_price);
        add = findViewById(R.id.btn_append_wish);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> data = new HashMap<>();
                data.put("goal", addGoal.getText().toString());
                data.put("goal_price", Long.parseLong(addGoalPrice.getText().toString()));
                data.put("money_left", Long.parseLong(addGoalPrice.getText().toString()));
                data.put("money_present", 0);
                data.put("account_id", accID);

                db.collection("piggy_bank").document(accID)
                        .set(data)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //.d(TAG, "DocumentSnapshot successfully written!");
                                Toast.makeText(getApplicationContext(), "Your wish was successfully added!",
                                        Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                //Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        });

    }
}
