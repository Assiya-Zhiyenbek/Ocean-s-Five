package com.qcb.financemanage.piggybank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.qcb.financemanage.R;
import com.qcb.financemanage.TransactionResultActivity;

import java.util.ArrayList;
import java.util.List;

public class MyWish extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference piggy_bank, accounts;
    private String accID;
    private final String[] categories = {"Present", "Left"};
    private final Long[] moneyStatuses = new Long[2];

    private TextView wish_info;
    private Button putToPiggyBank;
    private EditText moneyToPut;
    private Long currentBalance, moneyPresent, moneyLeft, goal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        wish_info = findViewById(R.id.wish_info);
        putToPiggyBank = findViewById(R.id.btn_put_to_piggybank);
        moneyToPut = findViewById(R.id.edit_text_add_money_to_wish);

        //Log.e("MYWISH", "Account id is: " + accID);

        db = FirebaseFirestore.getInstance();
        piggy_bank = db.collection("piggy_bank");
        accounts = db.collection("accounts");

        piggy_bank
                .whereEqualTo("account_id", accID)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Log.e("MYWISH", "data is: " + document.getData());

                                Long money_present = document.getLong("money_present");
                                Long money_left = document.getLong("money_left");

                                moneyStatuses[0] = money_present;
                                moneyStatuses[1] = money_left;
                                setupPieChart();

                                String info = "Your wish is: " + document.getString("goal") + "\n" +
                                                "Your goal is to reach: " + document.getLong("goal_price") + "$\n" +
                                                "You currently have a " + document.getLong("money_present") + "$\n" +
                                                "You have a " + document.getLong("money_left") + "$ left\n";

                                wish_info.setText(info);
                            }

                        } else {
                            Log.d("Error Tag", "Error getting documents: ", task.getException());
                        }
                    }
                });


        putToPiggyBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int money = Integer.parseInt(moneyToPut.getText().toString());
                Task task1 = accounts.document(accID).get();
                Task task2 = piggy_bank.document(accID).get();

                Task<List<DocumentSnapshot>> allTasks = Tasks.whenAllSuccess(task1, task2);
                allTasks.addOnSuccessListener(new OnSuccessListener<List<DocumentSnapshot>>() {
                    @Override
                    public void onSuccess(List<DocumentSnapshot> querySnapshots) {

                        int i = 0;
                        for(DocumentSnapshot documentSnapshot : querySnapshots) {

                            if(i == 0) {
                                currentBalance = documentSnapshot.getLong("balance");
                                Log.e("MYWISH", "CurrentBalance is: " + currentBalance);
                            } else if (i == 1){
                                moneyPresent = documentSnapshot.getLong("money_present");
                                moneyLeft = documentSnapshot.getLong("money_left");
                                goal = documentSnapshot.getLong("goal_price");

                                Log.e("MYWISH", "moneyPresent is: " + moneyPresent);
                                Log.e("MYWISH", "moneyLeft is: " + moneyLeft);
                                Log.e("MYWISH", "goal is: " + goal);

                            }
                            i++;
                        }
                        currentBalance -= money;
                        moneyPresent += money;
                        moneyLeft -= money;

                        if(moneyLeft < 0)
                            moneyLeft = Long.valueOf(0);

                        //Log.e(TAG, "SENDER BALANCE IS: " + balances[0]);
                        //Log.e(TAG, "Receiver BALANCE IS: " + balances[1]);

                        /* Sender Balance is balances[0], Receiver's balance is balances[1] */

                        // TODO: Check if there is not enough money to transfer


                        Task task3 = accounts.document(accID).update("balance", currentBalance);
                        Task task4 = piggy_bank.document(accID).update("money_present", moneyPresent);
                        Task task5 = piggy_bank.document(accID).update("money_left", moneyLeft);

                        Task allFinalTasks = Tasks.whenAllSuccess(task3, task4);
                        allFinalTasks.addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {

                                if(moneyLeft <= 0){
                                    Toast.makeText(getApplicationContext(), "CONGRATULATIONS!!!\n You've reached your goal!",
                                            Toast.LENGTH_LONG).show();
                                }

                                finish();
                                overridePendingTransition(0, 0);
                                startActivity(getIntent());
                                overridePendingTransition(0, 0);
                            }
                        });

                    }
                });

            }
        });
    }

    private void setupPieChart(){
        List<PieEntry> pieEntries = new ArrayList<>();
//        for(int i = 0; i < rainfall.length; i++){
//            pieEntries.add(new PieEntry(rainfall[i], Names[i]));
//        }
//
        for(int i = 0; i < 2; i++){
            pieEntries.add(new PieEntry(moneyStatuses[i], categories[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Amount of Money");
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData(dataSet);

        // Get the chart
        PieChart chart = findViewById(R.id.wish_pie_chart);
        chart.setData(data);
        chart.invalidate();
        chart.animateY(1000);
    }
}
