package com.qcb.financemanage.history;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.qcb.financemanage.R;

import java.util.ArrayList;

public class TransactionsGraph extends AppCompatActivity {

    private BarChart mchart;
    //ArrayList<String> xLabels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transactions_graph);

        Intent intent = getIntent();

        mchart = (BarChart) findViewById(R.id.bar_chart_transactions);

        setData(7);    // Number of bars
        mchart.setFitBars(true);

    }

    private void setData(int count) {
        ArrayList<BarEntry> yvals = new ArrayList<>();
        //ArrayList<String> xAxisLables = new ArrayList<>();

        for(int i = 0; i < count; i++ ){
            float value = (float) (Math.random() * 100);
            yvals.add(new BarEntry(i, (int) value));
           // xAxisLables.add(String.valueOf(i));
        }

        final String[] weekdays = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; // Your List / array with String Values For X-axis Labels

        // Set the value formatter
        XAxis xAxis = mchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays));




        BarDataSet set = new BarDataSet(yvals, "Data Set Labels");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);
        //mchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xAxisLables));

        BarData data = new BarData(set);
        mchart.setData(data);
        mchart.invalidate();
        mchart.animateY(500);


    }
}
