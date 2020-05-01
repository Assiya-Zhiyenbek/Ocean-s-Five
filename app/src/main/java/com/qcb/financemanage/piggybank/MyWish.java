package com.qcb.financemanage.piggybank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.qcb.financemanage.R;

import java.util.ArrayList;
import java.util.List;

public class MyWish extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference piggy_bank;
    private String accID;
    private final String[] categories = {"Present", "Left"};
    private final Long[] moneyStatuses = new Long[2];

    private TextView wish_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wish);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        wish_info = findViewById(R.id.wish_info);

        //Log.e("MYWISH", "Account id is: " + accID);

        db = FirebaseFirestore.getInstance();
        piggy_bank = db.collection("piggy_bank");

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
