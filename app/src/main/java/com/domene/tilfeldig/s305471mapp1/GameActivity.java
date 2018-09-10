package com.domene.tilfeldig.s305471mapp1;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends Activity {

    TextView regnestykke, svar, en,to,tre,fire,fem,seks,syv,åtte,ni,svarPaaSpoersmaal, ingenting, tilbake;
    int currProblem = 0;
    String[] arraySvar;
    String[] arraySpm;
    int[] arrayPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar
        arraySvar = getApplicationContext().getResources().getStringArray(R.array.math_solutions_add);
        arraySpm = getApplicationContext().getResources().getStringArray(R.array.math_problems_add);
        arrayPos = getApplicationContext().getResources().getIntArray(R.array.array_positions);
        shuffle(arrayPos);


        regnestykke = findViewById(R.id.regnestykke);
        en = findViewById(R.id.en);
        to = findViewById(R.id.to);
        tre = findViewById(R.id.tre);
        fire = findViewById(R.id.fire);
        fem = findViewById(R.id.fem);
        seks = findViewById(R.id.seks);
        syv = findViewById(R.id.syv);
        åtte = findViewById(R.id.åtte);
        ni = findViewById(R.id.ni);
        ingenting = findViewById(R.id.ingenting);
        tilbake = findViewById(R.id.tilbake);
        svarPaaSpoersmaal = findViewById(R.id.svarPaaRegnestykke);
        svar = findViewById(R.id.svar);
        updateTextview();

        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("1");
            }
        });
        to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("2");
            }
        });
        tre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("3");
            }
        });
        fire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("4");
            }
        });
        fem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("5");
            }
        });
        seks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("6");
            }
        });
        syv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("7");
            }
        });
        åtte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("8");
            }
        });
        ni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("9");
            }
        });
        ingenting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visSvar("0");
            }
        });
        tilbake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reduser();
            }
        });
        svar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sjekkSvar();
            }
        });
        if(savedInstanceState != null){
            svarPaaSpoersmaal.setText(savedInstanceState.getString("current_answer"));
            regnestykke.setText(savedInstanceState.getString("current_question"));
            arrayPos = savedInstanceState.getIntArray("current_position_array");
            arraySpm = savedInstanceState.getStringArray("current_answer_array");
            updateTextview();

        }

    }

    public void nextQuestion(){
        currProblem++;
        updateTextview();
        svarPaaSpoersmaal.setText("");
    }
    void updateTextview(){
        String problem_txt = arraySpm[arrayPos[currProblem]];
        regnestykke.setText(problem_txt);
    }
    static void shuffle(int[] arrayPos){
        int len = arrayPos.length;
        Random rand = new Random();

        for(int i = 0; i < arrayPos.length; i++){
            int randomValue = i + rand.nextInt(len - i);
            int randomElement = arrayPos[randomValue];
            arrayPos[randomValue] = arrayPos[i];
            arrayPos[i] = randomElement;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("current_answer", svarPaaSpoersmaal.getText().toString());
        outState.putString("current_question", arraySpm[arrayPos[currProblem]] );
        outState.putIntArray("current_position_array" , arrayPos);
        outState.putStringArray("current_answer_array", arraySpm);
        super.onSaveInstanceState(outState);
    }
    public void visSvar(String tall){
        String s = tall + "";
        svarPaaSpoersmaal.append(s);
    }
     public void reduser(){
        String svar = svarPaaSpoersmaal.getText().toString();
        if(svar.length()==  0){
            return;
        }else {
            svar = svar.substring(0, svar.length()-1);
            svarPaaSpoersmaal.setText(svar);
        }
     }
     public void sjekkSvar(){
        String svar = svarPaaSpoersmaal.getText().toString();
         Log.d("sjekksvar", arraySvar[arrayPos[currProblem]]+","+svar);
        if (svar.equals(arraySvar[arrayPos[currProblem]])){
            Toast.makeText(this, "YAY", Toast.LENGTH_SHORT).show();
            nextQuestion();
        }else {
            Toast.makeText(this, "NAY", Toast.LENGTH_SHORT).show();
        }
     }

}
