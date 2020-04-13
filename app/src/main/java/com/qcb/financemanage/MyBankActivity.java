package com.qcb.financemanage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MyBankActivity extends AppCompatActivity {

    private EditText account_ID;
    private Button btn_getData;
    private TextView data;
    public static final String TAG = "Firebase Tag";

    private FirebaseFirestore db;
    private DocumentReference docRefforAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mybank);

        db = FirebaseFirestore.getInstance();

        Intent intent = getIntent();


        account_ID = findViewById(R.id.acc_ID);
        btn_getData = findViewById(R.id.getData);
        data = findViewById(R.id.data);

        btn_getData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = account_ID.getText().toString();
                System.out.println("ID is: " + id);
                docRefforAccount = db.collection("accounts").document(id);
                docRefforAccount.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();


                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                Account currentAccount = document.toObject(Account.class);
                                data.setText("Account id: " + currentAccount.getAccount_id() + "\n" +
                                                "Account name: " + currentAccount.getAccount_name() + "\n" +
                                                "Balance: " + currentAccount.getBalance() + "\n" +
                                                "Open Date: " + currentAccount.getOpen_date()
                                );
                            } else {
                                Log.d(TAG, "No such document");
                                Toast.makeText(MyBankActivity.this, "No such ID", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                            Toast.makeText(MyBankActivity.this, task.getException().toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }

}


