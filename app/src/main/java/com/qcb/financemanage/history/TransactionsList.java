package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.qcb.financemanage.R;
import com.qcb.financemanage.Transaction;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/** This activity represents the list of past transactions **/

public class TransactionsList extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference transactions = db.collection("transactions");
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
        setContentView(R.layout.transactions_list);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");



        listView = (ListView) findViewById(R.id.transactions_history_list_view);

        transactions
                .whereEqualTo("sender_id", accID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Transaction currentTransactionDetails = document.toObject(Transaction.class);

                                // Formatting the date
                                Timestamp timestamp = (Timestamp) currentTransactionDetails.getDate();
                                Date date = timestamp.toDate();
                                //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
                                String details = "Transaction of " + currentTransactionDetails.getAmount() + "$ to " +
                                                    currentTransactionDetails.getReceiver() + "\n" + DateFormat.getDateTimeInstance().format(date);
                                list.add(details);
                            }

                            adapter = new ArrayAdapter(TransactionsList.this, android.R.layout.simple_expandable_list_item_1, list);
                            listView.setAdapter(adapter);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}
