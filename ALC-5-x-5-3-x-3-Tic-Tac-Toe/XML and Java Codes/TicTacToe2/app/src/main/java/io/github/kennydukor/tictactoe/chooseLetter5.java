package io.github.kennydukor.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class chooseLetter5 extends AppCompatActivity {
    private Button playX;
    private Button playO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_letter5);

        playO = (Button) findViewById(R.id.playO5);
        playX = (Button) findViewById(R.id.playX5);

        playX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseLetter5.this, fiveByFiveComX.class);
                startActivity(intent);

            }
        });

        playO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(chooseLetter5.this, fiveByFiveCom.class);
                startActivity(intent);

            }
        });


    }
}
