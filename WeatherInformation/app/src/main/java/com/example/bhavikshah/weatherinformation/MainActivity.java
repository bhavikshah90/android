package com.example.bhavikshah.weatherinformation;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    EditText cityName;
    EditText resultText;
    public void findWeather(View view){
        Log.i("City" ,  cityName.getText().toString());
        try {
            String encodeCityName = URLEncoder.encode(cityName.getText().toString(),"UTF-8");
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute("http://api.openweathermap.org/data/2.5/weather?q="+encodeCityName+"&APPID=e73be994795a917116f862e016bbcaf6");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(cityName.getWindowToken(),0);
        }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.checkbutton);
        cityName = (EditText) findViewById(R.id.cityTxt);
        resultText = (EditText) findViewById(R.id.infoLabelTxt);

    }

    public class DownloadTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;
            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                int data = inputStreamReader.read();
                while(data != -1){
                    char current = (char)data;
                    result += current;
                    data = inputStreamReader.read();
                }
                return  result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplication().getApplicationContext(),"Something is wrong in city name!",Toast.LENGTH_SHORT);
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            try {
                String message = "";
                JSONObject jsonObject = new JSONObject(result);
                String info = jsonObject.getString("weather");
                Log.i("Website Content" , info);
                JSONArray arr = new JSONArray(info);
                for(int i =0; i<arr.length();i++){

                    JSONObject jsonObject1 = arr.getJSONObject(i);
                    String main = "";
                    String description  = "";
                    main = jsonObject1.getString("main");
                    description = jsonObject1.getString("description");

                    if(main != "" && description != ""){
                        message += main + ": " + description+ "\r\n";
                    }
                }
                if(message != ""){
                    resultText.setText(message);
                }
                else
                {
                    Toast.makeText(getApplication().getApplicationContext(),"Could not find weather Information at this time",Toast.LENGTH_SHORT);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
    }


}
