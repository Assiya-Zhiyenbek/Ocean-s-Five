package com.qcb.financemanage;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button payments, transactions, history, myBank, piggybank;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Date date = new Date();
        Log.e("DATE is:", date.toString());

        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date weekago = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
        Log.e("DATE WEEK ago is:", weekago.toString());
        Log.e("Exact day week age is:", String.valueOf(getDayAndMonth(weekago)));

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        myBank = findViewById(R.id.btn_mybank);
        payments = findViewById(R.id.btn_payments);
        history = findViewById(R.id.btn_history);
        transactions = findViewById(R.id.btn_transactions);
        piggybank = findViewById(R.id.piggy_bank);


        myBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyBankActivity.class);
                startActivity(intent);
            }
        });

        payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Here we go to the "payments" screen, but we need to perform an authorization first.
                Intent intent = new Intent(getApplicationContext(), Authorization.class);
                intent.putExtra("DESTINATION", "PaymentActivity");
                startActivity(intent);
            }
        });

        // Open Authorization Page for the history
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Authorization.class);
                intent.putExtra("DESTINATION", "HistoryActivity");
                startActivity(intent);
            }
        });

        transactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Authorization.class);
                intent.putExtra("DESTINATION", "TransactionsActivity");
                startActivity(intent);
            }
        });

        piggybank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Authorization.class);
                intent.putExtra("DESTINATION", "PiggyBankActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public String getDayAndMonth(Date date){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String requiredDate = df.format(date).toString();
        String[] parts = requiredDate.split("/");
        return parts[0] + '.' + parts[1];
    }
}
