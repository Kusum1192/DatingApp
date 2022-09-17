package com.honeybunch.app.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.cooltechworks.creditcarddesign.CardEditActivity;
import com.cooltechworks.creditcarddesign.CreditCardUtils;
import com.cooltechworks.creditcarddesign.CreditCardView;
import com.honeybunch.app.R;

public class CreditCardActivity extends AppCompatActivity {

    CreditCardView creditCardView;
    TextView tv_pay;
    final int GET_NEW_CARD = 2;
    private static final String TAG = "CreditCardActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_card);

        creditCardView = findViewById(R.id.ccView);
        tv_pay = findViewById(R.id.tv_pay);
        tv_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreditCardActivity.this, CardEditActivity.class);
                startActivityForResult(intent, GET_NEW_CARD);
            }
        });
    }

    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            String cardHolderName = data.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME);
            String cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER);
            String expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY);
            String cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV);

            // Your processing goes here.
            creditCardView.setCVV(cvv);
            creditCardView.setCardHolderName(cardHolderName);
            creditCardView.setCardExpiry(expiry);
            creditCardView.setCardNumber(cardNumber);
        }

    }


}