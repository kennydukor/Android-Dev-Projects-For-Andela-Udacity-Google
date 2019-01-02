package io.github.kennydukor.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class fiveByFiveHum extends AppCompatActivity implements View.OnClickListener {
    private Button[][] buttons = new Button[5][5];
    private boolean PL_1_turn = true;
    private int roundCounter;
    private int PL_1Point;
    private int PL_2Point;
    private TextView PL_1TextView;
    private TextView PL_2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_by_five_com);

        PL_1TextView = findViewById(R.id.text_view_5x5_p1);
        PL_2TextView = findViewById(R.id.text_view_5x5_p2);

        Button quit = findViewById(R.id.quit_button_5Hum);


//        Button buttonReset = findViewById(R.id.button_5x5_resetHum);
//        buttonReset.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                reset();
//            }
//        });
//
//        quit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(fiveByFiveHum.this, MainActivity.class);
//                startActivity(intent);
//            }
//        });
        setBoard();
    }


    private void setBoard() {
        buttons[0][0] = (Button) findViewById(R.id.button5c_00);
        buttons[0][1] = (Button) findViewById(R.id.button5c_01);
        buttons[0][2] = (Button) findViewById(R.id.button5c_02);
        buttons[0][3] = (Button) findViewById(R.id.button5c_03);
        buttons[0][4] = (Button) findViewById(R.id.button5c_04);

        buttons[1][0] = (Button) findViewById(R.id.button5c_10);
        buttons[1][1] = (Button) findViewById(R.id.button5c_11);
        buttons[1][2] = (Button) findViewById(R.id.button5c_12);
        buttons[1][3] = (Button) findViewById(R.id.button5c_13);
        buttons[1][4] = (Button) findViewById(R.id.button5c_14);

        buttons[2][0] = (Button) findViewById(R.id.button5c_20);
        buttons[2][1] = (Button) findViewById(R.id.button5c_21);
        buttons[2][2] = (Button) findViewById(R.id.button5c_22);
        buttons[2][3] = (Button) findViewById(R.id.button5c_23);
        buttons[2][4] = (Button) findViewById(R.id.button5c_24);

        buttons[3][0] = (Button) findViewById(R.id.button5c_30);
        buttons[3][1] = (Button) findViewById(R.id.button5c_31);
        buttons[3][2] = (Button) findViewById(R.id.button5c_32);
        buttons[3][3] = (Button) findViewById(R.id.button5c_33);
        buttons[3][4] = (Button) findViewById(R.id.button5c_34);

        buttons[4][0] = (Button) findViewById(R.id.button5c_40);
        buttons[4][1] = (Button) findViewById(R.id.button5c_41);
        buttons[4][2] = (Button) findViewById(R.id.button5c_42);
        buttons[4][3] = (Button) findViewById(R.id.button5c_43);
        buttons[4][4] = (Button) findViewById(R.id.button5c_44);


        // add the click listeners for each button           
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                buttons[i][j].setOnClickListener(this);
            }
        }
    }


//    private void setBoard() {
//        for (int i = 0; i <= 4; i++) {
//            for (int j = 0; j <= 4; j++) {
//                String buttonID = "button_" + i + j;
//                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
//                buttons[i][j] = findViewById(resID);
//                buttons[i][j].setOnClickListener(this);
//            }
//        }
//    }

    @Override
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if (PL_1_turn) {
            ((Button) view).setText("X");
        } else {
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
                && field[0][2].equals(field[2][2])
                && field[0][2].equals(field[3][1])
                && field[0][2].equals(field[4][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }


    private void PL_1Wins() {
        PL_1Point += 1;
        Toast.makeText(this, "Play 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private void PL_2Wins() {
        PL_2Point += 1;
        Toast.makeText(this, "Play 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();

    }

    private void draw() {
        Toast.makeText(this, "TIE", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void updatePointsText() {
        PL_1TextView.setText("Player 1 (X): " + PL_1Point);
        PL_2TextView.setText("Player 2 (O): " + PL_2Point);
    }

    private void resetBoard() {
        buttons[0][0].setText("");
        buttons[0][1].setText("");
        buttons[0][2].setText("");
        buttons[0][3].setText("");
        buttons[0][4].setText("");

        buttons[1][0].setText("");
        buttons[1][1].setText("");
        buttons[1][2].setText("");
        buttons[1][3].setText("");
        buttons[1][4].setText("");

        buttons[2][0].setText("");
        buttons[2][1].setText("");
        buttons[2][2].setText("");
        buttons[2][3].setText("");
        buttons[2][4].setText("");

        buttons[3][0].setText("");
        buttons[3][1].setText("");
        buttons[3][2].setText("");
        buttons[3][3].setText("");
        buttons[3][4].setText("");

        buttons[4][0].setText("");
        buttons[4][1].setText("");
        buttons[4][2].setText("");
        buttons[4][3].setText("");
        buttons[4][4].setText("");
//        for (int i = 0; i <= 4; i++) {
//            for (int j = 0; j <= 4; i++) {
//                buttons[i][j].setText(" ");
//            }
//        }
        roundCounter = 0;
        PL_1_turn = true;
    }

    private void reset() {
        PL_1Point = 0;
        PL_2Point = 0;
        updatePointsText();
        resetBoard();
    }

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
