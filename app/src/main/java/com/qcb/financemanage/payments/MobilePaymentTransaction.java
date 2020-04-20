package com.qcb.financemanage.payments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.qcb.financemanage.R;

public class MobilePaymentTransaction extends AppCompatActivity {

    EditText amountOfMoneyToPay;
    Button btnPay;
    TextView mobileOperatorName;
    String operatorName;

    String accID;

    private FirebaseFirestore db;
    private DocumentReference docRefforAccount;
    private CollectionReference accounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_payment_transaction);

        db = FirebaseFirestore.getInstance();
        accounts = db.collection("accounts");

        Intent intent = getIntent();
        operatorName = intent.getStringExtra("OPERATOR");
        accID = intent.getStringExtra("Account_ID");

        //Log.e("ACC_ID", accID);
        //System.out.println("!!!!!!!!!!!!!!!!!!!!!!! ACCOUNT ID IS: " + accID + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        amountOfMoneyToPay = findViewById(R.id.mob_pay_trans_vvedite_summu);
        btnPay = findViewById(R.id.btn_mob_trans_pay);
        mobileOperatorName = findViewById(R.id.text_view_mob_trans_operator_name);

        mobileOperatorName.setText(operatorName);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /* Access the account by its ID in the database*/
                docRefforAccount = accounts.document(accID);
                Task<? extends DocumentSnapshot> task = docRefforAccount.get();
                task.addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        Long currentBalance = documentSnapshot.getLong("balance");              // Get current balance
                        Long amountToPay = Long.parseLong(amountOfMoneyToPay.getText().toString());  // Get amount of payment
                        currentBalance -= amountToPay;                                               // Update current balance

                        /* Update the balance in the database */
                        Task task3 = docRefforAccount.update("balance", currentBalance);
                        task3.addOnSuccessListener(new OnSuccessListener() {

                            // If Payment is successful, open a new activity
                            @Override
                            public void onSuccess(Object o) {
                                Intent intent = new Intent(getApplicationContext(), PaymentOperationResult.class);
                                startActivity(intent);
                            }
                        });
                    }
                });



            }
        });

    }
}
