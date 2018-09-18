package com.domene.tilfeldig.s305471mapp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Locale;

public class StartActivity extends Activity {

    private TextView startBtn, statisticsBtn, preferencesBtn;
    private SharedPreferences sf;
    private SharedPreferences.Editor editor;
    private static final String LANG_KEY = "språk";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareSharedPreferences();
        setLanguageToNorwegian_NO_RECREATE();
        setLanguageToGerman_NO_RECREATE();
        setContentView(R.layout.activity_start);
        hideStatusBar();
        setTextViewIDs();
        setOnClickListenerToTextViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        setLanguageToNorwegian_NO_RECREATE();
        setLanguageToGerman_NO_RECREATE();
    }
    private void goToGame(){
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }
    private void goToPreferences(){
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
        finish();
    }
    private void goToStatistikk(){
        Intent i = new Intent(this, StatistikkActivity.class);
        startActivity(i);
        finish();

    }
    private void prepareSharedPreferences(){
        sf = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = sf.edit();
    }
    private void setLanguageToNorwegian_NO_RECREATE(){
        if(sf.getString(LANG_KEY,"").equals("no")){
            editor.putString(LANG_KEY, "no").apply();
            String lang = "no";
            Context c = getApplicationContext();
            Locale l = new Locale(lang);
            Locale.setDefault(l);
            Resources res = c.getResources();
            Configuration conf = new Configuration(res.getConfiguration());
            conf.locale = l;
            res.updateConfiguration(conf, res.getDisplayMetrics());
        }
    }
    private void setLanguageToGerman_NO_RECREATE(){
        if(sf.getString(LANG_KEY,"").equals("de")){
            editor.putString(LANG_KEY, "de").apply();
            String lang = "de";
            Context c = getApplicationContext();
            Locale l = new Locale(lang);
            Locale.setDefault(l);
            Resources res = c.getResources();
            Configuration conf = new Configuration(res.getConfiguration());
            conf.locale = l;
            res.updateConfiguration(conf, res.getDisplayMetrics());
        }
    }
    private void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar
    }
    private void setTextViewIDs(){
        startBtn = findViewById(R.id.startSpill);
        preferencesBtn = findViewById(R.id.preferanser);
        statisticsBtn = findViewById(R.id.statistikk);
    }
    private void setOnClickListenerToTextViews(){
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToGame();
            }
        });
        preferencesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPreferences();
            }
        });
        statisticsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToStatistikk();
            }
        });
    }

}
