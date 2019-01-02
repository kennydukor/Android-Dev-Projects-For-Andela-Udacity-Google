package io.github.kennydukor.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class chooseLetter3 extends AppCompatActivity {
    private Button chooseX;
    private Button chooseO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_letter3);

        chooseX = (Button) findViewById(R.id.play_X3);
        chooseO = (Button) findViewById(R.id.play_O3);

        chooseX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseLetter3.this, ThreeByThreeComX.class);
                startActivity(intent);
            }
        });

        chooseO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseLetter3.this, threeByThreeCom.class);
                startActivity(intent);
            }
        });

    }
}
