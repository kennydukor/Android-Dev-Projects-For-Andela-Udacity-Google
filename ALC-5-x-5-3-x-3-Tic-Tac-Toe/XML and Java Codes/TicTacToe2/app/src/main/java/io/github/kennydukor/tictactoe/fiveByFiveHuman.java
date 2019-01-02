package io.github.kennydukor.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class fiveByFiveHuman extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[5][5];
    private boolean PL_1_turn = true;
    private int roundCounter;
    private int PL_1Point;
    private int PL_2Point;
    private TextView PL_1View;
    private TextView PL_2View;
    private Button quit;
    private Button buttonReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_by_five_human);
        PL_1View = findViewById(R.id.text_view_5x5H_p1);
        PL_2View = findViewById(R.id.text_view_5x5H_p2);
        quit = findViewById(R.id.quit_button_5Hum);
        buttonReset = findViewById(R.id.button_5x5_resetHum);

        quit();
        resetG();
        setBoard();

    }

    private void resetG() {
        // adding listener to the buttons to activityTwo and activityThree class
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });
    }

    private void quit() {
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fiveByFiveHuman.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void setBoard() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    // Initiate play for player 1 and player 2
    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if (PL_1_turn) {
            ((Button) view).setTextColor(Color.BLUE);
            ((Button) view).setText("X");
        } else {
            ((Button) view).setTextColor(Color.BLUE);
            ((Button) view).setText("O");
        }

        roundCounter++;

        if (checkWinner()) {
            if (PL_1_turn) {
                PL_1Wins();
            } else {
                PL_2Wins();
            }
        } else if (roundCounter == 25) {
            draw();
        } else {
            PL_1_turn = !PL_1_turn;
        }

    }


    private boolean checkWinner() {
        String[][] field = new String[5][5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 5; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && field[i][0].equals(field[i][3])
                    && field[i][0].equals(field[i][4])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 5; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && field[0][i].equals(field[3][i])
                    && field[0][i].equals(field[4][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && field[0][0].equals(field[3][3])
                && field[0][0].equals(field[4][4])
                && !field[0][0].equals("")) {
            return true;
        }


        if (field[0][4].equals(field[1][3])
                && field[0][4].equals(field[2][2])
                && field[0][4].equals(field[3][1])
                && field[0][4].equals(field[4][0])
                && !field[0][4].equals("")) {
            return true;
        }
        return false;
    }


    //print action if player 1 wins
    private void PL_1Wins() {
        PL_1Point++;
        Toast.makeText(this, "Play 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    //print action if player 2 wins
    private void PL_2Wins() {
        PL_2Point++;
        Toast.makeText(this, "Play 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
    }

    //print action if its is a draw
    private void draw() {
        Toast.makeText(this, "TIE", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    // Update score board
    private void updatePointsText() {
        PL_1View.setText("Player 1 (X): " + PL_1Point);
        PL_2View.setText("Player 2 (O): " + PL_2Point);
    }

    //reset all values in the game
    private void resetGame() {
        PL_1Point = 0;
        PL_2Point = 0;
        updatePointsText();
        resetBoard();
    }

    // clear X and O values on the board
    private void resetBoard() {
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                buttons[i][j].setText("");
                //buttons[i][j].setTextColor(Color.WHITE);
            }
        }

        roundCounter = 0;
        PL_1_turn = true;
    }
}


    // Maintain design in landscape mode
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putInt("roundCounter", roundCounter);
//        outState.putInt("PL_1Point", PL_1Point);
//        outState.putInt("PL_2Point", PL_2Point);
//        outState.putBoolean("PL_1_turn", PL_1_turn);
//    }
//
//    @Override
//    protected void onRestoreInstanceState(Bundle savedInstanceState) {
//        super.onRestoreInstanceState(savedInstanceState);
//        roundCounter = savedInstanceState.getInt("roundCounter");
//        PL_1Point = savedInstanceState.getInt("PL_1Point");
//        PL_2Point = savedInstanceState.getInt("PL_2Point");
//        PL_1_turn = savedInstanceState.getBoolean("PL_1_turn");
//    }
//}