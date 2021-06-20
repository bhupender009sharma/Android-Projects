package com.example.guesstheplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    ArrayList playerURLs = new ArrayList<String>();
    ArrayList playerNames = new ArrayList<String>();
    int chosenPlayer =0;
    ImageView imageView;

    String[] answers = new String[3];
    int locationOfCorrectAnswer =0;

    Button button0;
    Button button1;
    Button button2;

    public void chosenPlayer(View view){
        if(view.getTag().toString().equals(Integer.toString(locationOfCorrectAnswer))){
            Toast.makeText(getApplicationContext(),"CORRECT ANSWER!!",Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getApplicationContext(),"WRONG!! It Was " + playerNames.get(chosenPlayer) ,Toast.LENGTH_SHORT).show();

        }
        newQuestion();
    }

    public class ImageDownloader extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream =connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                return myBitmap;

            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            }
        }
    }

    public class DownloadTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in =urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();

                while (data != -1){
                    char current = (char)data;
                    result.append(current);
                    data = reader.read();
                }

                return result.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return  null;
            }
        }

    }

    public void newQuestion(){
        try {
            Random rand = new Random();
            chosenPlayer = rand.nextInt(playerURLs.size());

            ImageDownloader imageTask = new ImageDownloader();
            Bitmap playerImage = imageTask.execute((String) playerURLs.get(chosenPlayer)).get();

            imageView.setImageBitmap(playerImage);

            locationOfCorrectAnswer = rand.nextInt(3) ;

            int incorrectAnswerLocation;

            for(int i=0;i<3;i++){
                if(locationOfCorrectAnswer == i){
                    answers[i] = (String) playerNames.get(chosenPlayer);
                }
                else{
                    incorrectAnswerLocation = rand.nextInt(playerURLs.size());

                    while (chosenPlayer== incorrectAnswerLocation){
                        incorrectAnswerLocation = rand.nextInt(playerURLs.size());
                    }

                    answers[i] = (String) playerNames.get(incorrectAnswerLocation);
                }
            }
            button0.setText(answers[0]);
            button1.setText(answers[1]);
            button2.setText(answers[2]);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView =findViewById(R.id.imageView);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        DownloadTask task = new DownloadTask();
        String result = null;

        try {
            result =task.execute("https://www.imdb.com/list/ls021591480/").get();

            Pattern p = Pattern.compile("src=\"(.*?).jpg\"");
            Matcher m= p.matcher(result);

            while(m.find()){
                playerURLs.add(m.group(1) + ".jpg");
                Log.i("image links",m.group(1));
            }

            p = Pattern.compile("<img alt=\"(.*?)\"");
            m= p.matcher(result);

            while(m.find()){
                playerNames.add(m.group(1));
                Log.i("Player Names",m.group(1));
            }

            newQuestion();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}