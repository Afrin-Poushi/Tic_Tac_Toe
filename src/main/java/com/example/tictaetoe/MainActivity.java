package com.example.tictaetoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Trun = true;

    private int roundCnt;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i<3; i++){
            for (int j = 0; j<3; j++){
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener( this );

            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Trun) {
            ((Button) v).setText("X");
        }
        else {
            ((Button) v).setText("O");
        }

        roundCnt++;

        if(checkForWin()) {
            if (player1Trun) {
                player1Wins();
            }
            else {
                player2Wins();
            }
        }
        else if ( roundCnt == 9) {
            draw();
        }
        else {
            player1Trun = !player1Trun;
        }

    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i<3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i<3; i++) {
            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
            && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i<3; i++) {
            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }

        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
               && !field[0][0].equals("")) {
               return true;
            }

        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
                    && !field[0][2].equals("")) {
                return true;
            }


        }
        return false;

    }
    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
//        updatePoints();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
//        updatePoints();
        resetBoard();
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePoints() {
        String p1 = String.valueOf(player1Points);
        String p2 = String.valueOf(player2Points);
        textViewPlayer1.setText("Player 1: " + p1);
        textViewPlayer2.setText("Player 2: " + p2);
    }

    private void resetBoard() {
        for(int i =0; i<3; i++) {
            for (int j = 0; j<3; j++) {
                buttons[i][j].setText("");
            }
        }

        roundCnt = 0;
        player1Trun = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
//        updatePoints();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount", roundCnt);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Trun);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCnt = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Trun = savedInstanceState.getBoolean("player1Turn");

    }
}