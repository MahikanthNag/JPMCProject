package com.example.mmrao.lrbill;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class UserProfile extends AppCompatActivity {
    private Button scan_btn;
  private  Bundle b;
    public static final String JSON_PARSE = "JSON_PARSE";
private String mobilenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        scan_btn=(Button) findViewById(R.id.scan_btn);
        final Activity activity=this;
        scan_btn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(false);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();


            }

        });

//        Intent intent = getIntent();
        b = getIntent().getExtras();
        String username = b.getString(UserMerchant.KEY_NAME);
        String wallet1 = b.getString(UserMerchant.KEY_WALLET);
        mobilenumber  = b.getString("mobilenumber");
        Log.e("username","_"+username+"_");
        Log.e("wallet","_"+wallet1+"_");
        TextView textView = (TextView) findViewById(R.id.textView3);
        TextView textView1 = (TextView) findViewById(R.id.textView5);

        textView1.setText("Wallet "+wallet1);
        textView.setText("Welcome "+username);
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result !=null){
            if(result.getContents()==null){
                Toast.makeText(this,"you cancelled the scanning",Toast.LENGTH_LONG).show();
            }
            else {
                JSONParser parser = new JSONParser();
                try {
                    JSONObject json = (JSONObject) parser.parse(result.getContents());
                    Log.e("json",json.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserProfile.this, QRScanning.class);
                intent.putExtra("JSON_PARSE",result.getContents());
                intent.putExtra("mobilenumber",mobilenumber);
                Log.e("result",result.getContents());
                finish();
                startActivity(intent);
            }
        }
        else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}