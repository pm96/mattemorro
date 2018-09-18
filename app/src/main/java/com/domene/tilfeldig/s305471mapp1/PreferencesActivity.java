package com.domene.tilfeldig.s305471mapp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.Locale;

public class PreferencesActivity extends Activity {
    private RadioButton norwegian, german, five, ten, twentyFive;
    private int questionValue;
    private SharedPreferences sf;
    private SharedPreferences.Editor editor;
    private static final String LANG_KEY = "språk";
    private static final String SPM_KEY = "spm";

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareSharedPrefs();
        checkAndSetLanguage();
        setContentView(R.layout.activity_preferences);
        hideStatusBar();
        getViewIDs();
        checkAndSetLangRadioButton();
        checkAndSetQuestionAmountRadioButton();
        setOnCheckChangedListeners();

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
        finish();
        return super.onKeyDown(keyCode, event);
    }
    private void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar
    }
    private void setLanguageToGerman(){
        editor.putString(LANG_KEY, "de").apply();
        String lang = "de";
        Context c = getApplicationContext();
        Locale l = new Locale(lang);
        Locale.setDefault(l);
        Resources res = c.getResources();
        Configuration conf = new Configuration(res.getConfiguration());
        conf.locale = l;
        res.updateConfiguration(conf, res.getDisplayMetrics());
        recreate();
    }
    private void setLanguageToNorwegian(){
        editor.putString(LANG_KEY, "no").apply();
        String lang = "no";
        Context c = getApplicationContext();
        Locale l = new Locale(lang);
        Locale.setDefault(l);
        Resources res = c.getResources();
        Configuration conf = new Configuration(res.getConfiguration());
        conf.locale = l;
        res.updateConfiguration(conf, res.getDisplayMetrics());
        recreate();
    }
    private void getViewIDs(){
        norwegian = findViewById(R.id.norskRadio);
        german = findViewById(R.id.tyskRadio);
        five = findViewById(R.id.femSpm);
        ten = findViewById(R.id.tiSpm);
        twentyFive = findViewById(R.id.tjueFem);
    }
    private void prepareSharedPrefs(){
        sf = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = sf.edit();
    }
    private void checkAndSetLanguage(){
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
        }else if(sf.getString(LANG_KEY,"").equals("de")){
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
    private void checkAndSetLangRadioButton(){
        if(sf.getString(LANG_KEY,"").equals("no")){
            norwegian.setChecked(true);
        }else if(sf.getString(LANG_KEY, "").equals("de")){
            german.setChecked(true);
        }
    }
    private void checkAndSetQuestionAmountRadioButton(){
        questionValue = sf.getInt(SPM_KEY, 5);
        switch (questionValue) {
            case 5:
                five.setChecked(true);
                break;
            case 10:
                ten.setChecked(true);
                break;
            case 25:
                twentyFive.setChecked(true);
                break;
        }
    }
    private void setOnCheckChangedListeners(){
        norwegian.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && sf.getString(LANG_KEY,"no").equals("de")){
                    setLanguageToNorwegian();
                }
            }
        });
        german.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && sf.getString(LANG_KEY, "no  ").equals("no")){
                    setLanguageToGerman();
                }
            }
        });
        five.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(five.isChecked()){
                    editor.putInt(SPM_KEY, 5).apply();
                }
            }
        });
        ten.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(ten.isChecked()){
                    editor.putInt(SPM_KEY, 10).apply();
                }
            }
        });
        twentyFive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(twentyFive.isChecked()){
                    editor.putInt(SPM_KEY,25).apply();
                }
            }
        });
    }
}
