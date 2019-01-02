package io.github.kennydukor.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class threeByThreeHum extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[3][3];
    private boolean PL_1_turn = true;
    private int roundCounter;
    private int PL_1Point;
    private int PL_2Point;
    private TextView PL_1TextView;
    private TextView PL_2TextView;
    private Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_by_three_hum);

        PL_1TextView = findViewById(R.id.text_view_3x3_p1_hum);
        PL_2TextView = findViewById(R.id.text_view_3x3_p2_hum);
        quit = findViewById(R.id.quit_button_3hum);
        Button buttonReset = findViewById(R.id.button_3x3_resetHum);

        setBoard();


        // adding listener to the buttons to activityTwo and activityThree class
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(threeByThreeHum.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    //Set up board game (Linking to all buttons)
    private void setBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
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
//                ((Button) view).setText("X");
                PL_1Wins();
            } else {
                PL_2Wins();
            }
        } else if (roundCounter == 9) {
            draw();
        } else {
            PL_1_turn = !PL_1_turn;
        }

    }

    //Check for winner
    private boolean checkWinner() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
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
        PL_1TextView.setText("Player 1 (X): " + PL_1Point);
        PL_2TextView.setText("Player 2 (O): " + PL_2Point);
    }

    //reset all values in the game
    private void resetGame() {
        PL_1Point = 0;
        PL_2Point = 0;
        updatePointsText();
        resetBoard();
    }

    private void delay() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    // clear X and O values on the board
    private void resetBoard() {
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                //buttons[i][j].setTextColor(Color.WHITE);
                buttons[i][j].setText("");
            }
        }

        roundCounter = 0;
        PL_1_turn = true;
    }


    // Maintain design in landscape mode
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCounter", roundCounter);
        outState.putInt("PL_1Point", PL_1Point);
        outState.putInt("PL_2Point", PL_2Point);
        outState.putBoolean("PL_1_turn", PL_1_turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCounter = savedInstanceState.getInt("roundCounter");
        PL_1Point = savedInstanceState.getInt("PL_1Point");
        PL_2Point = savedInstanceState.getInt("PL_2Point");
        PL_1_turn = savedInstanceState.getBoolean("PL_1_turn");
    }
}
