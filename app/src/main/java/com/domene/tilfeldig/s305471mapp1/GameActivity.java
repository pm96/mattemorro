package com.domene.tilfeldig.s305471mapp1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TextView regnestykke, svar, en,to,tre,fire,fem,seks,syk,otte,ni,svarKnapp;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar

        String[] array = getApplicationContext().getResources().getStringArray(R.array.regnestykker);
        String randomStr = array[new Random().nextInt(array.length)];
        regnestykke = (TextView)findViewById(R.id.regnestykke);
        regnestykke.setText(randomStr);
        en = (TextView)findViewById(R.id.tall1);
        svar = (TextView)findViewById(R.id.svarPaaRegnestykke);
        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               visSvar();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);    }

    public void visSvar(){
        counter++;
        String counter1 = counter + " ";
        svar.setText(counter1);
    }
}
