package com.example.mmrao.lrbill;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.os.AsyncTask;

import com.example.mmrao.lrbill.MainActivity;
/*import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;*/

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

public class Register extends AppCompatActivity {

    Button bRegister;
    private EditText etName, etMobileNumber, etPassword, etEmail;
   // public static final String USER_NAME = "USERNAME";


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
                url = new URL("http://mahikanthnag.net23.net/metta.php");

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        etName = (EditText) findViewById(R.id.etName);
        etMobileNumber = (EditText) findViewById(R.id.etMobileNumber);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etEmail = (EditText) findViewById(R.id.etEmail);

        bRegister = (Button) findViewById(R.id.bRegister);

        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final int mobilenumber = Integer.parseInt(etMobileNumber.getText().toString().trim());
                final String password = etPassword.getText().toString();
                final String email = etEmail.getText().toString();
                JSONObject log1;
                log1 = new JSONObject();
                try {
                    log1.put("name", name);
                    log1.put("password", password);
                    log1.put("email", email);
                    log1.put("mobilenumber", mobilenumber + "");
                    Log.d("Json string", log1.toString());
                    new DownloadWebpageTask().execute(String.valueOf(log1));
                    Handler handler = new Handler();//for delay
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(Register.this, MainActivity.class);
                           // intent.putExtra(USER_NAME, name);
                            startActivity(intent);

                        }
                    }, 5000);
                } catch (Exception e) {
                    Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
