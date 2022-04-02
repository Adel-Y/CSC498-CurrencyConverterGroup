package com.csc498.groupCurrencyConverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;

import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConversionActivity extends AppCompatActivity {
    TextView text;
    TextView conversion_text;
    String s;
    String api_rate = null;
    String to_display = null;
    EditText editText;

    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion);
        text=(TextView) findViewById(R.id.header1);
        conversion_text=(TextView)findViewById(R.id.result);
        editText= (EditText)findViewById(R.id.amount);

        Intent i = getIntent();
        //conversion type the user intends to do
        s = i.getStringExtra("conversion_type");



        String amount =  ""; //get the amount from the view
        String url = "http://192.168.0.103:8000/api/rate";

        GetRate getRate = new GetRate();
        getRate.execute(url);



    }


    public class GetRate extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            HttpURLConnection http;

            try{
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while( data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

            return result;
        }


        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{
                JSONObject json = new JSONObject(s);
                 api_rate = json.getString("rate");
                text.setText("Today's rate is: "+api_rate);

                //Log.i("Rate", api_rate);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    public class StoreRate extends AsyncTask<String, Void, String> {

        protected String doInBackground(String... urls){
            String result = "";
            URL url;
            HttpURLConnection http;

            try{
                url = new URL(urls[0]);
                http = (HttpURLConnection) url.openConnection();

                InputStream in = http.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while( data != -1){
                    char current = (char) data;
                    result += current;
                    data = reader.read();

                }
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

            return result;
        }


        protected void onPostExecute(String s){
            super.onPostExecute(s);

            try{
                JSONObject json = new JSONObject(s);
                to_display = json.getString("to_display");
                conversion_text.setText("The entered amount is equivalent to: "+to_display);

                //Log.i("Rate", api_rate);

            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }

    public void onClick(View v){
        double x = Double.parseDouble(editText.getText().toString());
        double rate = Double.parseDouble(api_rate);
        String url = null;
        if(s.equals("USD")){
            url = "http://192.168.0.103:8000/api/conversions?amount="+x+"&rate="+rate+"&convert_to="+s+"&currency=$";
        }
        else if (s.equals("LBP")){

            url = "http://192.168.0.103:8000/api/conversions?amount="+x+"&rate="+rate+"&convert_to="+s+"&currency=LL";
        }

        StoreRate storeRate = new StoreRate();
        storeRate.execute(url);
    }

}