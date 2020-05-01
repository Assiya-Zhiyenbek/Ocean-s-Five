package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.qcb.financemanage.Payment;
import com.qcb.financemanage.R;
import com.qcb.financemanage.Transaction;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentsList extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference transactions = db.collection("payments");
    //private DocumentReference TransactionsForSpecificUser;

    public static final String TAG = "SOME TAG";


    private List list = new ArrayList();
    private ListView listView;
    private ArrayAdapter adapter;
    private String accID;
    private List<String> tr_list;   // transactions list for the specific user



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_list);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");


        listView = (ListView) findViewById(R.id.payments_history_list_view);

        transactions
                .whereEqualTo("payer_id", accID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                Payment currentPaymentDetails = document.toObject(Payment.class);

                                // Formatting the date
                                Timestamp timestamp = (Timestamp) currentPaymentDetails.getDate();
                                Date date = timestamp.toDate();
                                //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                                String details = "Payment of " + currentPaymentDetails.getAmount() + "$ to " +
                                        currentPaymentDetails.getPay_for() + "\n" + DateFormat.getDateTimeInstance().format(date);
                                list.add(details);
                            }

                            adapter = new ArrayAdapter(PaymentsList.this, android.R.layout.simple_expandable_list_item_1, list);
                            listView.setAdapter(adapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}
