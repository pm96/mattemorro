package com.domene.tilfeldig.s305471mapp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import java.util.Locale;

public class PreferencesActivity extends Activity {
    RadioButton norsk, tysk, fem, ti, tjueFem;
    int counter;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);


        norsk = findViewById(R.id.norskRadio);
        tysk = findViewById(R.id.tyskRadio);
        fem = findViewById(R.id.femSpm);
        ti = findViewById(R.id.tiSpm);
        tjueFem = findViewById(R.id.tjueFem);
        norsk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tilNorsk();
                }
            }
        });
        tysk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    tilTysk();
                }
            }
        });

        if (fem.isChecked()){
            counter = 5;
        }else if (ti.isChecked()){
            counter = 10;
        }else if (tjueFem.isChecked()) {
            counter = 25;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent i = new Intent(this, StartActivity.class);
        i.putExtra("Counter",counter);
        startActivity(i);
    }




    public void tilTysk(){
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
    public void tilNorsk(){
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
}
