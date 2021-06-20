package com.example.game;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // coin =0 , doin =1 , emoty =2 :- active player
    int activePlayer = 0;
    int[] gameState = {2,2,2,2,2,2,2,2,2} ;
    int[][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    boolean gameActive =true;       // so that nobody plays after game has stopped

    public void dropIn(View view) {
        ImageView counter = (ImageView) view;

        int tappedCounter = Integer.parseInt(counter.getTag().toString());

        if(gameState[tappedCounter] ==2 && gameActive) {        //so that, nobody can overwrite someone's marked place

            counter.setTranslationY(-1500);
            gameState[tappedCounter] = activePlayer;

            if (activePlayer == 0) {
                activePlayer = 1;
                counter.setImageResource(R.drawable.coin);
            } else {
                activePlayer = 0;
                counter.setImageResource(R.drawable.doin);
            }
            counter.animate().translationYBy(1500).rotationBy(3600).setDuration(1000);

            for (int[] winningPosition : winningPositions) {
                if (gameState[winningPosition[0]] == gameState[winningPosition[1]] && gameState[winningPosition[1]] == gameState[winningPosition[2]] && gameState[winningPosition[0]] != 2) {
                    // someone has won

                    gameActive =false;

                    String winner = "";

                    if (activePlayer == 0) {
                        winner = "Doin";
                    } else {
                        winner = "Coin";
                    }


                    TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

                    winnerTextView.setText(winner + " has won");

                    winnerTextView.setVisibility(View.VISIBLE);
                }
            }
            Button newGameButton = (Button) findViewById(R.id.newGameButton);
            newGameButton.setVisibility(View.VISIBLE);
        }
    }

    public void newGame(View view){
        Button newGameButton = (Button) findViewById(R.id.newGameButton);
        TextView winnerTextView = (TextView) findViewById(R.id.winnerTextView);

        newGameButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayout);

        for(int i=0; i<gridLayout.getChildCount();i++){
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }

        for(int i=0 ;i<gameState.length;i++){
            gameState[i] = 2;

        }

        activePlayer =0;
        gameActive = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}