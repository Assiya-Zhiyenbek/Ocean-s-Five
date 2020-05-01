package com.qcb.financemanage.payments;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.qcb.financemanage.R;

public class GamesPaymentActivity extends AppCompatActivity {

    ImageButton minecraft, cookie_clicker;
    String accID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_payment);

        Intent intent = getIntent();
        accID = intent.getStringExtra("Account_ID");

        minecraft = findViewById(R.id.btn_minecraft);
        cookie_clicker = findViewById(R.id.btn_cookie_clicker);

        final Intent next_intent = new Intent(getApplicationContext(), GamesPaymentTransaction.class);
        next_intent.putExtra("Account_ID", accID);

        minecraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_intent.putExtra("GAME", "MINECRAFT");
                startActivity(next_intent);

            }
        });

        cookie_clicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next_intent.putExtra("GAME", "COOKIE CLICKER");
                startActivity(next_intent);

            }
        });




    }
}
