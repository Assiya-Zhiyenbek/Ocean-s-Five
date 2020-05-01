package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

public class PaymentsGraph extends AppCompatActivity {

    private BarChart mchart;
    private String accID;
    private final long DAY_IN_MS = 1000 * 60 * 60 * 24;             // This is necessary to find a date a week ago
    private HashMap<String, Integer> moneyTransferredByDate = new HashMap<>();

    private FirebaseFirestore db;
    private CollectionReference payments;
    //ArrayList<String> xLabels = new ArrayList<>();

    private final String[] weekdays = new String[7];    // Your List / array with String Values For X-axis Labels

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_graph);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        // Date and time stuff
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        for(int i = 0; i < 7; i++){
            weekdays[i] = getDayAndMonth(new Date(System.currentTimeMillis() - ((6 - i) * DAY_IN_MS)));
            moneyTransferredByDate.put(weekdays[i], 0);
        }


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

                                // Mapping loaded data to POJO object
                                Payment currentTransactionDetails = document.toObject(Payment.class);

                                // Formatting the date
                                Timestamp timestamp = (Timestamp) currentTransactionDetails.getDate();
                                Date date = timestamp.toDate();

                                String monthAndDay = getDayAndMonth(date);

                                // Filling in HashMap
                                Integer currAmount = moneyTransferredByDate.get(monthAndDay);
                                moneyTransferredByDate.put(monthAndDay, currAmount + Integer.valueOf(currentTransactionDetails.getAmount()));


                                // Setting up the graph
                                mchart = (BarChart) findViewById(R.id.bar_chart_payments);

                                setData(7);    // Number of bars
                                mchart.setFitBars(true);

                            }

                        } else {
                            Log.d("Error Tag", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    private void setData(int count) {
        ArrayList<BarEntry> yvals = new ArrayList<>();
        //ArrayList<String> xAxisLables = new ArrayList<>();


        for(int i = 0; i < count; i++ ){
            Integer value = moneyTransferredByDate.get(weekdays[i]);
            yvals.add(new BarEntry(i, value));
            // xAxisLables.add(String.valueOf(i));
        }


        // Set the value formatter
        XAxis xAxis = mchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays));


        BarDataSet set = new BarDataSet(yvals, "Amount of money in $");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);
        //mchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));

        BarData data = new BarData(set);
        mchart.setData(data);
        mchart.invalidate();
        mchart.animateY(500);


    }

    public String getDayAndMonth(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String requiredDate = df.format(date).toString();
        String[] parts = requiredDate.split("/");
        return parts[0] + '.' + parts[1];
    }
}
