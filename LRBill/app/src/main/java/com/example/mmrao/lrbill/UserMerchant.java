package com.example.mmrao.lrbill;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.name;
import static com.example.mmrao.lrbill.MainActivity.*;

public class UserMerchant extends AppCompatActivity {
    //private Button button2;
    Bundle b;
    private RadioButton radioButton, radioButton2;
    private RadioGroup radioUser;
    private ProgressDialog loading;
    private Button btnDisplay;
    String name1 = "";
    private long mobilenumber;
    String wallet = "";
    String username = "";
    public static final String DATA_URL = "http://mahikanthnag.net23.net//manphp.php?mobilenumber=";
    public static final String KEY_NAME = "name";
    public static final String KEY_WALLET = "wallet";
    public static final String JSON_ARRAY = "result";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_merchant);
        b = getIntent().getExtras();
        username = b.getString("USER_NAME");
        Toast.makeText(getApplicationContext(),".."+username,Toast.LENGTH_LONG);
        radioButton = (RadioButton) findViewById(R.id.radioButton);
        radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        radioUser = (RadioGroup) findViewById(R.id.radioUser);
        addListenerOnButton();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void addListenerOnButton() {

        radioUser = (RadioGroup) findViewById(R.id.radioUser);
        btnDisplay = (Button) findViewById(R.id.btnDisplay);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getData();
            }
        });

    }

    private void getData() {
        username = getIntent().getStringExtra("USER_NAME");
        Log.e("username",username);
        String url = DATA_URL+username;

        loading = ProgressDialog.show(this,"Please wait...","Fetching...",false,false);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                try {
                    Log.e("responseaaaa",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
                    JSONObject merData = result.getJSONObject(0);
                    name1 = merData.getString(KEY_NAME);
                    wallet = merData.getString(KEY_WALLET);
                    mobilenumber = Long.parseLong(username);
                    Log.e("name",name1);
                    Log.e("wallet",wallet);
                    Log.e("mobilenumber ", mobilenumber+"");
                    if (radioUser.getCheckedRadioButtonId() == radioButton.getId()) {
                        Intent intent = new Intent(UserMerchant.this, UserProfile.class);
                        intent.putExtra(KEY_NAME, name1);
                        intent.putExtra(KEY_WALLET, wallet);
                        intent.putExtra("mobilenumber",username);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(UserMerchant.this, Blank.class);
                        intent.putExtra(KEY_NAME, name1);
                        intent.putExtra(KEY_WALLET, wallet);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserMerchant.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("UserMerchant Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    /* private void showJSON(String response){

        try {
             JSONObject jsonObject = new JSONObject(response);
             JSONArray result = jsonObject.getJSONArray(JSON_ARRAY);
             JSONObject merData = result.getJSONObject(0);
             name1 = merData.getString(KEY_NAME);
             wallet = merData.getString(KEY_WALLET);
            // vc = collegeData.getString(Config.KEY_VC);
         } catch (JSONException e) {
             e.printStackTrace();
         }

       //  textViewResult.setText("Name:\t"+name+"\nAddress:\t" +address+ "\nVice Chancellor:\t"+ vc);
     }*/
  /*  public void onRadioButtonClicked(View view) {
        getData();
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radioButton:
                if (checked) {
                    Intent intent = new Intent(UserMerchant.this, UserProfile.class);
                    intent.putExtra(KEY_NAME, name1);
                    intent.putExtra(KEY_WALLET, wallet);
                    startActivity(intent);
                    break;
                }
            case R.id.radioButton2:
                if (checked) {

                    Intent intent = new Intent(UserMerchant.this, Blank.class);
                    intent.putExtra(KEY_NAME, name1);
                    intent.putExtra(KEY_WALLET, wallet);
                    startActivity(intent);
                    break;
                }
        }
    }*/
    //  }
    // });
}
