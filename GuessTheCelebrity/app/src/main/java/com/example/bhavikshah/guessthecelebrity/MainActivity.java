package com.example.bhavikshah.guessthecelebrity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> celebURLS= new ArrayList<String>();
    ArrayList<String> celebNames= new ArrayList<String>();
    int choosenCelebrities = 0;
    ImageView imageView;
    Button button1;
    Button button2;
    Button button3;
    Button button4;

    int  locationOfCorrectAnswer = 0;
    String[] answers = new String[4];

    public void choosenCeleb(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Toast.makeText(getApplication().getApplicationContext(),"Correct!",Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplication().getApplicationContext(),"InCorrect! It was "+celebNames.get(choosenCelebrities),Toast.LENGTH_LONG).show();
        }
        generateQuestion();

    }
    public class ImageDownloader extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap myImage = BitmapFactory.decodeStream(inputStream);
                return myImage;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    public  class DownloadTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {
                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(inputStream);
                int data = reader.read();
                while (data != -1){
                    char current = (char) data;
                    result += current;
                    data= reader.read();
                }
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        DownloadTask downloadTask = new DownloadTask();
        String result = null;
        try {
            result = downloadTask.execute("http://www.posh24.se/kandisar").get();
            String[] splitResult =result.split("<div class=\"sidebarContainer\">");
            Pattern pattern = Pattern.compile("<img src=\"(.*?)\"");
            Matcher matcher = pattern.matcher(splitResult[0]);
            while (matcher.find()){
                //System.out.println(matcher.group(1));
                celebURLS.add(matcher.group(1));

            }
            pattern = Pattern.compile("alt=\"(.*?)\"");
            matcher = pattern.matcher(splitResult[0]);
            while (matcher.find()){
                //System.out.println(matcher.group(1));
                celebNames.add(matcher.group(1));
            }

            generateQuestion();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void generateQuestion(){

        Random random = new Random();
        choosenCelebrities = random.nextInt(celebURLS.size());
        ImageDownloader imageDownloader = new ImageDownloader();
        Bitmap myImage = null;
        try {
            myImage = imageDownloader.execute(celebURLS.get(choosenCelebrities)).get();
            imageView.setImageBitmap(myImage);
            locationOfCorrectAnswer = random.nextInt(4);
            int incorrectAnswer;
            for(int i = 0;i<4;i++ ){
                if(i == locationOfCorrectAnswer){
                    answers[i] = celebNames.get(choosenCelebrities);
                }
                else{
                    incorrectAnswer = random.nextInt(celebURLS.size());
                    while(incorrectAnswer == choosenCelebrities){
                        incorrectAnswer = random.nextInt(celebURLS.size());
                    }
                    answers[i] = celebNames.get(incorrectAnswer);
                }

            }
            button1.setText(answers[0]);
            button2.setText(answers[1]);
            button3.setText(answers[2]);
            button4.setText(answers[3]);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
