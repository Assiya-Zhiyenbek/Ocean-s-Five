package com.qcb.financemanage.piggybank;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.PaymentsActivity;
import com.qcb.financemanage.R;

public class PiggyBank extends AppCompatActivity {

    private String accID;
    private Button addWish, myWish, deleteWish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piggy_bank);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        Log.e("PIGGYBANK", "Account id is: " + accID);

        addWish = findViewById(R.id.btn_add_wish);
        myWish = findViewById(R.id.btn_my_wish);
        deleteWish = findViewById(R.id.btn_delete_wish);

        myWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyWish.class);
                intent.putExtra("Account_ID", accID);
                startActivity(intent);
            }
        });

    }
}
