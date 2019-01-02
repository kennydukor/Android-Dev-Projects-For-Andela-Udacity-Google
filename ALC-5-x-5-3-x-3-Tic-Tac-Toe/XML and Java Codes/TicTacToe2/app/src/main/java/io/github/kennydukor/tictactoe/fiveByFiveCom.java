package io.github.kennydukor.tictactoe;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class fiveByFiveCom extends AppCompatActivity {

    private int[][] c = new int[6][6];
    private int i, j = 0;
    private Button[][] buttons = new Button[6][6];
    private AI ai = new AI();
    private int youPoint;
    private int botPoint;
    private TextView youTextView;
    private TextView botTextView;
    private Button quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_by_five_com);

        youTextView = findViewById(R.id.text_view_5x5_you);
        botTextView = findViewById(R.id.text_view_5x5_bot);
        quit = findViewById(R.id.quit_button_5Com);
        Button resetGame = findViewById(R.id.button_5x5_resetCom);

        // adding listener to the buttons to activityTwo and activityThree class
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fiveByFiveCom.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        setBoard();

    }
    //Set up board game
    private void setBoard() {
        buttons[1][5] = (Button) findViewById(R.id.button5c_00);
        buttons[1][4] = (Button) findViewById(R.id.button5c_01);
        buttons[1][3] = (Button) findViewById(R.id.button5c_02);
        buttons[1][2] = (Button) findViewById(R.id.button5c_03);
        buttons[1][1] = (Button) findViewById(R.id.button5c_04);

        buttons[2][5] = (Button) findViewById(R.id.button5c_10);
        buttons[2][4] = (Button) findViewById(R.id.button5c_11);
        buttons[2][3] = (Button) findViewById(R.id.button5c_12);
        buttons[2][2] = (Button) findViewById(R.id.button5c_13);
        buttons[2][1] = (Button) findViewById(R.id.button5c_14);

        buttons[3][5] = (Button) findViewById(R.id.button5c_20);
        buttons[3][4] = (Button) findViewById(R.id.button5c_21);
        buttons[3][3] = (Button) findViewById(R.id.button5c_22);
        buttons[3][2] = (Button) findViewById(R.id.button5c_23);
        buttons[3][1] = (Button) findViewById(R.id.button5c_24);

        buttons[4][5] = (Button) findViewById(R.id.button5c_30);
        buttons[4][4] = (Button) findViewById(R.id.button5c_31);
        buttons[4][3] = (Button) findViewById(R.id.button5c_32);
        buttons[4][2] = (Button) findViewById(R.id.button5c_33);
        buttons[4][1] = (Button) findViewById(R.id.button5c_34);

        buttons[5][5] = (Button) findViewById(R.id.button5c_40);
        buttons[5][4] = (Button) findViewById(R.id.button5c_41);
        buttons[5][3] = (Button) findViewById(R.id.button5c_42);
        buttons[5][2] = (Button) findViewById(R.id.button5c_43);
        buttons[5][1] = (Button) findViewById(R.id.button5c_44);

        for (i = 1; i < 6; i++) {
            for (j = 1; j < 6; j++)
                c[i][j] = 2;
        };

        // add the click listeners for each button           
        for (i = 1; i < 6; i++) {
            for (j = 1; j < 6; j++) {
                buttons[i][j].setOnClickListener(new fiveByFiveCom.MyClickListener(i, j));
                if (!buttons[i][j].isEnabled()) {
                    buttons[i][j].setText(" ");
                    //buttons[i][j].setTextColor(Color.WHITE);
                    buttons[i][j].setEnabled(true);
                }
            }
        }
    }

    // check the board to see if there is a winner and print action  
    private boolean checkBoard() {
        boolean gameOver = false;
        if ((c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0 && c[4][4] == 0 && c[5][5] == 0)
                || (c[1][5] == 0 && c[2][4] == 0 && c[3][3] == 0 && c[4][2] == 0 && c[5][1] == 0)
                || (c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0 && c[4][1] == 0 && c[5][1] == 0)
                || (c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0 && c[4][2] == 0 && c[5][2] == 0)
                || (c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0 && c[4][3] == 0 && c[5][3] == 0)
                || (c[1][4] == 0 && c[2][4] == 0 && c[3][4] == 0 && c[4][4] == 0 && c[5][4] == 0)
                || (c[1][5] == 0 && c[2][5] == 0 && c[3][5] == 0 && c[4][5] == 0 && c[5][5] == 0)
                || (c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0 && c[1][4] == 0 && c[1][5] == 0)
                || (c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0 && c[2][4] == 0 && c[2][5] == 0)
                || (c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0 && c[3][4] == 0 && c[3][5] == 0)
                || (c[4][1] == 0 && c[4][2] == 0 && c[4][3] == 0 && c[4][4] == 0 && c[4][5] == 0)
                || (c[5][1] == 0 && c[5][2] == 0 && c[5][3] == 0 && c[5][4] == 0 && c[5][5] == 0)) {
            youWin();
            gameOver = true;
        } else if ((c[1][1] == 1 && c[2][2] == 1 && c[3][3] == 1 && c[4][4] == 1 && c[5][5] == 1)
                || (c[1][5] == 1 && c[2][4] == 1 && c[3][3] == 1 && c[4][2] == 1 && c[5][1] == 1)
                || (c[1][1] == 1 && c[2][1] == 1 && c[3][1] == 1 && c[4][1] == 1 && c[5][1] == 1)
                || (c[1][2] == 1 && c[2][2] == 1 && c[3][2] == 1 && c[4][2] == 1 && c[5][2] == 1)
                || (c[1][3] == 1 && c[2][3] == 1 && c[3][3] == 1 && c[4][3] == 1 && c[5][3] == 1)
                || (c[1][4] == 1 && c[2][4] == 1 && c[3][4] == 1 && c[4][4] == 1 && c[5][4] == 1)
                || (c[1][5] == 1 && c[2][5] == 1 && c[3][5] == 1 && c[4][5] == 1 && c[5][5] == 1)
                || (c[1][1] == 1 && c[1][2] == 1 && c[1][3] == 1 && c[1][4] == 1 && c[1][5] == 1)
                || (c[2][1] == 1 && c[2][2] == 1 && c[2][3] == 1 && c[2][4] == 1 && c[2][5] == 1)
                || (c[3][1] == 1 && c[3][2] == 1 && c[3][3] == 1 && c[3][4] == 1 && c[3][5] == 1)
                || (c[4][1] == 1 && c[4][2] == 1 && c[4][3] == 1 && c[4][4] == 1 && c[4][5] == 1)
                || (c[5][1] == 1 && c[5][2] == 1 && c[5][3] == 1 && c[5][4] == 1 && c[5][5] == 1)){
            botWin();
            gameOver=true;
        } else {
            boolean empty=false;
            for(i = 1; i < 6; i++){
                for(j = 1; j < 6; j++){
                    if(c[i][j] == 2){
                        empty=true;
                        break;
                    }
                }
            }
            if(!empty){
                gameOver=true;
                draw();
            }
        }
        return gameOver;
    }

    // print action for a win
    private void youWin() {
        youPoint += 1;
        Toast.makeText(this, "You Win!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        setBoard();
    }

    // print action for a lose
    private void botWin() {
        botPoint += 1;
        Toast.makeText(this, "You Lost to the BOT!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        setBoard();

    }

    // print action for a draw
    private void draw() {
        Toast.makeText(this, "TIE", Toast.LENGTH_SHORT).show();
        setBoard();
    }

    //Update the points on the scoreboard
    private void updatePointsText(){
        youTextView.setText(" You:   " + youPoint + " ");
        botTextView.setText(" Com:  " + botPoint + " ");
    }

    //Reset all values in the game
    private void reset(){
        youPoint = 0;
        botPoint = 0;
        updatePointsText();
        setBoard();
    }

    class MyClickListener implements View.OnClickListener {
        int x;
        int y;


        public MyClickListener(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void onClick(View view) {
            if (buttons[x][y].isEnabled()) {
                buttons[x][y].setEnabled(false);
                buttons[x][y].setTextColor(Color.BLUE);
                buttons[x][y].setText("O");
                c[x][y] = 0;

                if (!checkBoard()) {
                    ai.takeTurn();
                }
            }
        }
    }

    // Creating Computer game style
    private class AI {
        public void takeTurn() {
            if (c[1][1] == 2 && ((c[1][2] == 0 && c[1][3] == 0 && c[1][4] == 0 && c[1][5] == 0 ) || (c[2][2] == 0 && c[3][3] == 0 && c[4][4] == 0 && c[5][5] == 0 ) || (c[2][1] == 0 && c[3][1] == 0 && c[4][1] == 0 && c[5][1] ==0 ))) {
                markSquare(1, 1);
            } else if (c[1][2] == 2 && ((c[2][2] == 0 && c[3][2] == 0 && c[4][2] == 0 && c[5][2] == 0 ) || (c[1][1] == 0 && c[1][3] == 0 && c[1][4] == 0 && c[1][5] == 0 ))) {
                markSquare(1, 2);
            } else if (c[1][3] == 2 && ((c[1][1] == 0 && c[1][2] == 0 && c[1][4] == 0 && c[1][5] == 0 ) || (c[2][3] == 0 && c[3][3] == 0 && c[4][3] == 0 && c[5][3] ==0 ))) {
                markSquare(1, 3);
            } else if (c[1][4] == 2 && ((c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0 && c[1][5] == 0 ) || (c[2][4] == 0 && c[3][4] == 0 && c[4][4] == 0 && c[5][4] ==0 ))) {
                markSquare(1, 4);
            } else if (c[1][5] == 2 && ((c[1][1] == 0 && c[1][2] == 0 && c[1][3] == 0 && c[1][4] == 0 ) || (c[2][5] == 0 && c[3][5] == 0 && c[4][5] == 0 && c[5][5] ==0 ) || (c[2][4] == 0 && c[3][3] == 0 && c[4][2] == 0 && c[5][1] ==0 ))) {
                markSquare(1, 5);
            }

            else if (c[2][1] == 2 && ((c[2][2] == 0 && c[2][3] == 0 && c[2][4] == 0 && c[2][5] == 0 ) || (c[1][1] == 0 && c[3][1] == 0 && c[4][1] == 0 && c[5][1] == 0 ))) {
                markSquare(2, 1);
            } else if (c[2][2] == 2 && ((c[1][1] == 0 && c[3][3] == 0 && c[4][4] == 0 && c[5][5] == 0 ) || (c[1][2] == 0 && c[3][2] == 0 && c[4][2] == 0 && c[5][2] == 0 ) || (c[2][1] == 0 && c[2][3] == 0 && c[2][4] == 0 && c[2][5] == 0 ))){
                markSquare(2, 2);
            } else if (c[2][3] == 2 && ((c[2][1] == 0 && c[2][2] == 0 && c[2][4] == 0 && c[2][5] == 0 ) || (c[1][3] == 0 && c[3][3] == 0 && c[4][3] == 0 && c[5][3] == 0 ))){
                markSquare(2, 3);
            } else if (c[2][4] == 2 && ((c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0 && c[2][5] == 0 ) || (c[1][4] == 0 && c[3][4] == 0 && c[4][4] == 0 && c[5][4] == 0 ) || (c[1][5] == 0 && c[3][3] == 0 && c[4][2] == 0 && c[5][1] == 0 ))){
                markSquare(2, 4);
            } else if (c[2][5] == 2 && ((c[2][1] == 0 && c[2][2] == 0 && c[2][3] == 0 && c[2][4] == 0 ) || (c[1][5] == 0 && c[3][5] == 0 && c[4][5] == 0 && c[5][5] == 0 ))){
                markSquare(2, 5);
            }


            else if (c[3][1] == 2 && ((c[1][1] == 0 && c[2][1] == 0 && c[4][1] == 0 && c[5][1] == 0 ) || (c[3][2] == 0 && c[3][3] == 0 && c[3][4] == 0 && c[3][5] == 0 ))){
                markSquare(3, 1);
            } else if (c[3][2] == 2 && ((c[1][2] == 0 && c[2][2] == 0 && c[4][2] == 0 && c[5][2] == 0 ) || (c[3][1] == 0 && c[3][3] == 0&& c[3][4] == 0 && c[3][5] == 0 ))){
                markSquare(3, 2);
            } else if (c[3][3] == 2 && (c[1][1] == 0 && c[2][2] == 0 && c[4][4] == 0 && c[5][5] == 0 ) || (c[1][3] == 0 && c[2][3] == 0 && c[4][3] == 0 && c[5][3] == 0 ) || (c[3][1] == 0 && c[3][2] == 0 && c[3][4] == 0 && c[3][5] == 0 )) {
                markSquare(3, 3);
            } else if (c[3][4] == 2 && (c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0 && c[3][5] == 0 ) || (c[1][4] == 0 && c[2][4] == 0 && c[4][4] == 0 && c[5][4] == 0)) {
                markSquare(3, 4);
            } else if (c[3][5] == 2 && (c[1][5] == 0 && c[2][5] == 0 && c[4][5] == 0 && c[5][5] == 0 ) || (c[3][1] == 0 && c[3][2] == 0 && c[3][3] == 0 && c[3][4] == 0)) {
                markSquare(3, 5);
            }

            else if (c[4][1] == 2 && ((c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0 && c[5][1] == 0 ) || (c[4][2] == 0 && c[4][3] == 0 && c[4][4] == 0 && c[4][5] == 0 ))){
                markSquare(4, 1);
            } else if (c[4][2] == 2 && ((c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0 && c[5][2] == 0 ) || (c[4][1] == 0 && c[4][3] == 0&& c[4][4] == 0 && c[4][5] == 0 ) || (c[1][5] == 0 && c[2][4] == 0&& c[3][3] == 0 && c[5][1] == 0 ))){
                markSquare(4, 2);
            } else if (c[4][3] == 2 && (c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0 && c[5][3] == 0 ) || (c[4][1] == 0 && c[4][2] == 0 && c[4][4] == 0 && c[4][5] == 0 )) {
                markSquare(4, 3);
            } else if (c[4][4] == 2 && (c[4][1] == 0 && c[4][2] == 0 && c[4][3] == 0 && c[4][5] == 0 ) || (c[1][4] == 0 && c[2][4] == 0 && c[3][4] == 0 && c[5][4] == 0) || (c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0 && c[5][5] == 0)) {
                markSquare(4, 4);
            } else if (c[4][5] == 2 && (c[1][5] == 0 && c[2][5] == 0 && c[3][5] == 0 && c[5][5] == 0 ) || (c[4][1] == 0 && c[4][2] == 0 && c[4][3] == 0 && c[4][4] == 0)) {
                markSquare(4, 5);
            }

            else if (c[5][1] == 2 && ((c[1][1] == 0 && c[2][1] == 0 && c[3][1] == 0 && c[4][1] == 0 ) || (c[5][2] == 0 && c[5][3] == 0 && c[5][4] == 0 && c[5][5] == 0 ) || (c[1][5] == 0 && c[2][4] == 0 && c[3][3] == 0 && c[4][2] == 0 ))){
                markSquare(5, 1);
            } else if (c[5][2] == 2 && ((c[1][2] == 0 && c[2][2] == 0 && c[3][2] == 0 && c[4][2] == 0 ) || (c[5][1] == 0 && c[5][3] == 0&& c[5][4] == 0 && c[5][5] == 0 ))){
                markSquare(5, 2);
            } else if (c[5][3] == 2 && (c[1][3] == 0 && c[2][3] == 0 && c[3][3] == 0 && c[4][3] == 0 ) || (c[5][1] == 0 && c[5][2] == 0 && c[5][4] == 0 && c[5][5] == 0 )) {
                markSquare(5, 3);
            } else if (c[5][4] == 2 && (c[5][1] == 0 && c[5][2] == 0 && c[5][3] == 0 && c[5][5] == 0 ) || (c[1][4] == 0 && c[2][4] == 0 && c[5][4] == 0 && c[4][4] == 0)) {
                markSquare(5, 4);
            } else if (c[5][5] == 2 && (c[1][5] == 0 && c[2][5] == 0 && c[3][5] == 0 && c[4][5] == 0 ) || (c[5][1] == 0 && c[5][2] == 0 && c[5][3] == 0 && c[5][4] == 0) || (c[1][1] == 0 && c[2][2] == 0 && c[3][3] == 0 && c[4][4] == 0)){
                markSquare(5, 5);
            }
            else {
                Random rand = new Random();
                int a = rand.nextInt(6);
                int b = rand.nextInt(6);
                while (a == 0 || b == 0 || c[a][b] != 2) {
                    a = rand.nextInt(6);
                    b = rand.nextInt(6);
                }
                markSquare(a, b);
            }
        }


        private void markSquare(int x, int y) {
            buttons[x][y].setEnabled(false);
            buttons[x][y].setTextColor(Color.BLUE);
            buttons[x][y].setText("X");
            c[x][y] = 1;
            checkBoard();
        }
    }
}


