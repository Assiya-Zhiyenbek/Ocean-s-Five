package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.qcb.financemanage.Payment;
import com.qcb.financemanage.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaymentsChart extends AppCompatActivity {

    private FirebaseFirestore db;
    private CollectionReference payments;
    private String accID;
    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;

    Set<String> categories = new HashSet<>();
    HashMap<String, Integer> moneySpentByCategory = new HashMap<>();

    float rainfall[] = {98.8f, 123.8f, 161.6f, 24.2f, 52f};
    String Names[] = {"Cat1", "Cat2", "Cat3", "Cat4", "Cat5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_chart);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        //Log.e("ID", "Acc id is: " + accID);

        db = FirebaseFirestore.getInstance();
        payments = db.collection("payments");

        payments
                .whereEqualTo("payer_id", accID)
                .whereGreaterThan("date", new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                               // Log.e("DATA: ", "DATA IS: " + document.getData());

                                // Mapping loaded data to POJO object
                                Payment currentPaymentDetails = document.toObject(Payment.class);


                                // Filling in HashMap
                                String currCategory = currentPaymentDetails.getType();
                                Integer currAmount = currentPaymentDetails.getAmount();
                                if(!moneySpentByCategory.containsKey(currCategory)){
                                    moneySpentByCategory.put(currCategory, currAmount);
                                } else {
                                    Integer existingAmount = moneySpentByCategory.get(currCategory);
                                    moneySpentByCategory.put(currCategory, existingAmount + currAmount);
                                }
                                // Adding to the set of categories
                                categories.add(currCategory);

                                setupPieChart();
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
        for(String cat: categories){
            pieEntries.add(new PieEntry(moneySpentByCategory.get(cat), cat));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Rainfall for fff");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        // Get the chart
        PieChart chart = findViewById(R.id.payments_pie_chart);
        chart.setData(data);
        chart.invalidate();
        chart.animateY(1000);
    }

}
