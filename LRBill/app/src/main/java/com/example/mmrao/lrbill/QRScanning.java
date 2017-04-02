package com.example.mmrao.lrbill;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

import static com.example.mmrao.lrbill.R.id.textView2;

public class QRScanning extends AppCompatActivity {
    Bundle b;
    String jsonparse = "";
    private TextView textview2;
    private TextView textview3;
    private TextView textview4;
    private Button button2;
    private String name="";
    private String mobilenumber1="";
    private String amount="";
    private String mobilenumber ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanning);
        b = getIntent().getExtras();
        jsonparse = b.getString("JSON_PARSE");
        mobilenumber = b.getString("mobilenumber");
        textview2 = (TextView) findViewById(R.id.textView6);
        textview3 = (TextView) findViewById(R.id.textView9);
        textview4 = (TextView) findViewById(R.id.textView10);
        scanData();
        //addButtonClick();
    }
    private void scanData()
        {
        int posOfName = jsonparse.indexOf("name");
        int posOfMob = jsonparse.indexOf("mobile");
        int posOfAmt =jsonparse.indexOf("amount");
//        int posOfCurrentUserMobNo = jsonparse.indexOf("mobilenumber");
        int name1=posOfName+5;
        int mob1=posOfMob-1;
        int mob2= posOfMob+13;
        int amt1= posOfAmt-1;
        int amt2 =posOfAmt+7;
        int amt3 =jsonparse.length()-1;
        name  = new String(jsonparse.substring(name1,mob1));
        mobilenumber1 = new String(jsonparse.substring(mob2,amt1));
        amount= new String(jsonparse.substring(amt2,amt3));

            textview2.setText(name);
            textview3.setText(mobilenumber1);
            textview4.setText(amount);

        button2 = (Button) findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener(){


                @Override
                public void onClick(View v) {
//                    final int mobilenumberLocal = Integer.parseInt(mobilenumber1);
                    JSONObject jobj = new JSONObject();
                    try {
                        jobj.put("name",name);
                        jobj.put("mobilenumberOfPayee",mobilenumber1);
                        jobj.put("wallet",amount);
                        jobj.put("mobilenumberOfPayer", mobilenumber+"");
                        Log.e("objectmetta",jobj.toString());
                        new DownloadWebpageTask().execute(String.valueOf(jobj));
                    }catch(Exception e)
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            });

    }
    private class DownloadWebpageTask extends AsyncTask<String, Void, String> {

        protected void preExecute() {

        }

        @Override
        protected String doInBackground(String... params) {

            String JsonDATA = params[0];

            URL url;
            Log.d("enetered async", "Source File not exist :");
            HttpURLConnection urlConnection = null;

            try {
                url = new URL("http://mahikanthnag.net23.net/update_wallet.php");

                BufferedReader br = null;
                //  Long l=Long.parseLong(params[0]);
                Log.e("entered after url", "debug");
                Log.e("uploadFile", "Source File not exist :");
                Log.e("checkingsss values:", JsonDATA);

                //Log.e("params created", "with :"+param );
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("POST");
                Log.e("after post", "posted");

                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                Log.e("before writing", "mime json");

                Writer wr = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                wr.write(JsonDATA);
                wr.close();

                Log.e("after:", "writing to server");

                String response = "";

                Scanner inStream = new Scanner(urlConnection.getInputStream());
                while (inStream.hasNextLine())
                    response += (inStream.nextLine());
                Log.e("response:", response);
                urlConnection.disconnect();


            } catch (MalformedURLException e) {
                Toast.makeText(getApplicationContext(), "malformed url caught", Toast.LENGTH_SHORT).show();
                return "Unable to retrieve web page. URL may be invalid.";

            } catch (ProtocolException e) {
                Toast.makeText(getApplicationContext(), "protocol exception caught", Toast.LENGTH_SHORT).show();
                return "Unable to retrieve web page. URL may be invalid.";

            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "io exception caught", Toast.LENGTH_SHORT).show();
                return "Unable to retrieve web page. URL may be invalid.";

            } catch (Exception e) {
                Log.e("Exception in bac ", e.getMessage());
            }
            return "failed";
        }

    }

}
