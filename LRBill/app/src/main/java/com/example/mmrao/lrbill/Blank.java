package com.example.mmrao.lrbill;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Blank extends AppCompatActivity {
    Intent intent = new Intent();
    private  Bundle b;
    //String getname = getIntent().getStringExtra(UserMerchant.KEY_NAME);
    //String getwallet =getIntent().getStringExtra(UserMerchant.KEY_WALLET);


    public static final String JSON_ARRAY = "result";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        b = getIntent().getExtras();
        String username = b.getString(UserMerchant.KEY_NAME);
        String wallet1 = b.getString(UserMerchant.KEY_WALLET);
        TextView textView7 = (TextView) findViewById(R.id.textView7);
        TextView textView8 = (TextView) findViewById(R.id.textView8);
        textView8.setText("Wallet "+wallet1);
        textView7.setText("Welcome "+username);


    }
}
