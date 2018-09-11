package com.domene.tilfeldig.s305471mapp1;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

public class StartActivity extends Activity {

    TextView startKnapp, statistikk, preferanser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar
        startAnimation(this);
        startKnapp = findViewById(R.id.startSpill);
        preferanser = findViewById(R.id.preferanser);
        statistikk = findViewById(R.id.statistikk);
        startKnapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame();
            }
        });
        preferanser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreferences();
            }
        });
        statistikk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStatistikk();
            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        try{
            String s = prefs.getString("språkNorsk","funkaKje");
            if(s == s ){
                Log.d("hm", "onCreate: " + s);
            }
        }catch (ClassCastException cce){
            Log.d("SA", "onCreate: ClassCastException");
        }
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

    public void goToPreferences(){
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
    }

    public void goToStatistikk(){
        //Intent i = new Intent(this, PrefsFragment.class);
        //startActivity(i);
    }

    public void startAnimation(StartActivity view){
        ImageView logo = (ImageView)findViewById(R.id.logo);

        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(1000);

        Animation fadeOut = new AlphaAnimation(1,0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(1000);

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        animation.addAnimation(fadeOut);
        logo.setAnimation(animation);

    }


    

    
}
