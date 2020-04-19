package com.qcb.financemanage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class TransactionsActivity extends AppCompatActivity {

    EditText receiverField, senderField, moneyAmountField;
    Button btn_transfer;
    private FirebaseFirestore db;
    private DocumentReference senderAccount;
    private DocumentReference receiverAccount;
    private CollectionReference accounts;

    private final Long[] receiverBalance = new Long[1];
    private final Long[] senderBalance = new Long[1];
    private final Long[] balances = new Long[2];


    public static final String TAG = "Firebase Tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transactions);

        Intent intent = getIntent();

        db = FirebaseFirestore.getInstance();
        receiverField = findViewById(R.id.receiver_field);
        senderField = findViewById(R.id.sender_field);
        moneyAmountField = findViewById(R.id.money_amount_field);
        accounts = db.collection("accounts");
        //senderAccount = db.collection("accounts").document(sender_ID);
        //receiverAccount = db.collection("accounts").document(reveiver_ID);

        btn_transfer = findViewById(R.id.btn_trans);

        btn_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reveiver_ID = receiverField.getText().toString();
                String sender_ID = senderField.getText().toString();
                final int moneyAmountToTransfer = Integer.parseInt(moneyAmountField.getText().toString());

                System.out.println("Rec ID: " + reveiver_ID);
                System.out.println("Send ID: " + sender_ID);
                System.out.println("Money AM : " + moneyAmountToTransfer);

                senderAccount = accounts.document(sender_ID);
                receiverAccount = accounts.document(reveiver_ID);

                Task task1 = senderAccount.get();
                Task task2 = receiverAccount.get();

                Task<List<DocumentSnapshot>> allTasks = Tasks.whenAllSuccess(task1, task2);
                allTasks.addOnSuccessListener(new OnSuccessListener<List<DocumentSnapshot>>() {
                    @Override
                    public void onSuccess(List<DocumentSnapshot> querySnapshots) {

                        int i = 0;
                        for(DocumentSnapshot documentSnapshot : querySnapshots) {
                            Long currentBalance = documentSnapshot.getLong("balance");
                            balances[i] = currentBalance;
                            i++;
                            //Log.e(TAG, "SENDER BALANCE IS: " + sendBal);
                        }
                        Log.e(TAG, "SENDER BALANCE IS: " + balances[0]);
                        Log.e(TAG, "Receiver BALANCE IS: " + balances[1]);

                        /* Sender Balance is balances[0], Receiver's balance is balances[1] */

                        // TODO: Check if there is not enough money to transfer
                        balances[0] -= moneyAmountToTransfer;
                        balances[1] += moneyAmountToTransfer;

                        Task task3 = senderAccount.update("balance", balances[0]);
                        Task task4 = receiverAccount.update("balance", balances[1]);
                        Task allFinalTasks = Tasks.whenAllSuccess(task3, task4);
                        allFinalTasks.addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Log.e(TAG, "This thing is SUCCESSFUL!!!!!");
                            }
                        });

                    }
                });
//                /* Get data from the sender Account */
//                senderAccount.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//
//
//
//
//                            if (document.exists()) {
//                                senderBalance[0] = document.getLong("balance");
//                                Log.e(TAG, "(1) Sender balance is: " + senderBalance[0]);
//
//
//
//                                receiverAccount.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                        if(task.isSuccessful()){
//                                            DocumentSnapshot document = task.getResult();
//
//
//                                            receiverBalance[0] =  document.getLong("balance");
//                                            Log.e(TAG, "(1) Receiver balance is: " + receiverBalance[0]);
//
//                                            if(document.exists()){
//
//                                            }
//                                        }
//                                    }
//                                });
//
//
//
//
//
//
//
//
//
//
//                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
//                            } else {
//                                Toast.makeText(TransactionsActivity.this, "Invalid Sender ID", Toast.LENGTH_LONG).show();
//                                Log.e(TAG, "Document does not exist");
//                            }
//                        } else {
//                            Log.e(TAG, "get failed with ", task.getException());
//                        }
//                    }
//                });

                //Log.e(TAG, "(2) Sender balance is: " + senderBalance[0]);

//                getBalance(senderAccount, "Sender");
//                getBalance(receiverAccount, "Receiver");
//
//                Log.e(TAG, "Sender balance is: " + senderBalance[0]);
//                Log.e(TAG, "Receiver balance is: " + receiverBalance[0]);


//                Intent intent = new Intent(getApplicationContext(), TransactionResultActivity.class);
//                startActivity(intent);
            }
        });
    }


}
