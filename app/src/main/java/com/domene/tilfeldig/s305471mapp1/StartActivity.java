package com.domene.tilfeldig.s305471mapp1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    TextView startKnapp, statistikk, preferanser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar

        startKnapp = (TextView) findViewById(R.id.start_spill);
        startKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    public void goToGame(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
