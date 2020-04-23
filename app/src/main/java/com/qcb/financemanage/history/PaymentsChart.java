package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.qcb.financemanage.R;

import java.util.ArrayList;
import java.util.List;

public class PaymentsChart extends AppCompatActivity {

    float rainfall[] = {98.8f, 123.8f, 161.6f, 24.2f, 52f};
    String Names[] = {"Cat1", "Cat2", "Cat3", "Cat4", "Cat5"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payments_chart);

        Intent intent = getIntent();

        setupPieChart();
    }

    private void setupPieChart(){
        List<PieEntry> pieEntries = new ArrayList<>();
        for(int i = 0; i < rainfall.length; i++){
            pieEntries.add(new PieEntry(rainfall[i], Names[i]));
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
