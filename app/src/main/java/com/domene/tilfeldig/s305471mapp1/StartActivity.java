package com.domene.tilfeldig.s305471mapp1;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class StartActivity extends AppCompatActivity {

    TextView startKnapp, statistikk, preferanser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar
        startAnimation(this);
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

    public void tysk(View v){
        String language = "de";
        Context context = getApplicationContext();
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration configuration = new Configuration(res.getConfiguration());
        configuration.locale = locale;
        res.updateConfiguration(configuration, res.getDisplayMetrics());
        recreate();
    }

    public void norsk(View v){
        String language= "no";
        Context context = getApplicationContext();
        Locale locale = new Locale(language);
        locale.setDefault(locale);
        Resources res = context.getResources();
        Configuration configuration = new Configuration(res.getConfiguration());
        configuration.locale = locale;
        res.updateConfiguration(configuration, res.getDisplayMetrics());
        recreate();
    }
    

    
}
