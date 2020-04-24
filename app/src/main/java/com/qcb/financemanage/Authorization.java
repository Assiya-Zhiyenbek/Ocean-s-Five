package com.qcb.financemanage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class Authorization extends AppCompatActivity {

    private EditText accIDField;
    private Button btn_authorize;
    private String accID;

    // This is an activity that will be opened after this authorization step
    private String destinationActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        Intent intent = getIntent();
        destinationActivity = intent.getStringExtra("DESTINATION");

        accIDField = findViewById(R.id.editText_authorization_acc_ID);
        btn_authorize = findViewById(R.id.btn_authorize);


        // TODO: Check whether entered account ID exists or not
        btn_authorize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                accID = accIDField.getText().toString();

                // TODO: Add separate intent for the Transactions activity
                if (destinationActivity.equals("PaymentActivity")) {
                    Intent intent = new Intent(getApplicationContext(), PaymentsActivity.class);
                    intent.putExtra("Account_ID", accID);
                    startActivity(intent);
                } else if (destinationActivity.equals("HistoryActivity")){
                    Intent intent = new Intent(getApplicationContext(), HistoryActivity.class);
                    intent.putExtra("Account_ID", accID);
                    startActivity(intent);
                } else if (destinationActivity.equals("TransactionsActivity")) {
                    Intent intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                    intent.putExtra("Account_ID", accID);
                    startActivity(intent);
                }


            }
        });

    }
}
