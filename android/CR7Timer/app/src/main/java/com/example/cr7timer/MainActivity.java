package com.example.cr7timer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    TextView timerTextView;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    Button siiiButton;
    CountDownTimer countDownTimer;
    CountDownTimer gifTimer;
    GifImageView siiiGifImageView;
    //int count =1;

    public void updateTimer(int secondsLeft){
        int minutes = secondsLeft/60;
        int seconds = secondsLeft - (minutes*60);

        String minuteString= Integer.toString(minutes);
        String secondString= Integer.toString(seconds);

        if( minutes < 10){
            minuteString = "0" +minuteString;
        }
        if(seconds < 10){
            secondString = "0" + secondString;
        }

        timerTextView.setText(minuteString + ":" + secondString);
    }

    public void gifEndTimer(){
        gifTimer = new CountDownTimer(4000,1000) {
            int count=1;
            @Override
            public void onTick(long millisUntilFinished) {
                if(count ==2 ){
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.ronaldo_si);
                    mediaPlayer.start();
                }
                count++;
            }

            @Override
            public void onFinish() {
                siiiGifImageView.setVisibility(View.INVISIBLE);


            }
        }.start();
    }

    public void resetTimer(){

        counterIsActive= false;
        siiiButton.setText("Siii !!!");
        timerSeekBar.setEnabled(true);
        timerTextView.setText("00:03");
        timerSeekBar.setProgress(3);
        countDownTimer.cancel();


    }
    public void siiiClicked(View view){
        if(counterIsActive){

            resetTimer();

        }else {

            counterIsActive = true;
            siiiButton.setText("STOP!!");
            timerSeekBar.setEnabled(false);


            countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    siiiGifImageView.setVisibility(View.VISIBLE);
                    gifEndTimer();

                    resetTimer();
                }
            }.start();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        siiiButton = (Button) findViewById(R.id.siiiButton);
        siiiGifImageView = (GifImageView) findViewById(R.id.siiiGifImageView);

        siiiGifImageView.setVisibility(View.INVISIBLE);

        timerSeekBar.setMax(600);
        timerSeekBar.setProgress(3);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}