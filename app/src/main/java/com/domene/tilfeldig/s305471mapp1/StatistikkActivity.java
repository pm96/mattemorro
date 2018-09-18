package com.domene.tilfeldig.s305471mapp1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class StatistikkActivity extends Activity {
    private static final String ANT_RIKTIG = "ant_riktig";
    private static final String ANT_FEIL = "ant_feil";
    private static final String TOTAL_ANT_RIKTIG = "total_ant_riktig";
    private static final String TOTAL_ANT_FEIL = "total_ant_feil";
    private float percentRight, percentRightMax;
    private SharedPreferences sf;
    private SharedPreferences.Editor editor;
    private TextView lastGameStat, allGameStats, deleteStats;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistikk);
        hideStatusBar();
        findViewIDs();
        setUpSharedPrefs();
        setStatsORUpdateFromSharedPrefs();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Intent i = new Intent(this, StartActivity.class);
        startActivity(i);
        finish();
        return super.onKeyDown(keyCode, event);
    }
    private void findViewIDs(){
        lastGameStat = findViewById(R.id.lastGameStats);
        allGameStats = findViewById(R.id.allGamesStats);
        deleteStats = findViewById(R.id.deleteStats);
    }
    private void hideStatusBar(){
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions); //fjerner status bar
    }
    private void setUpSharedPrefs(){
        sf = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = sf.edit();
    }
    private void setStatsORUpdateFromSharedPrefs(){
        percentRight = (float)sf.getInt(ANT_RIKTIG,0)/ (sf.getInt(ANT_RIKTIG,0)+(float)sf.getInt(ANT_FEIL,0)) *100;
        percentRightMax = (float)sf.getInt(TOTAL_ANT_RIKTIG,0)/ (sf.getInt(TOTAL_ANT_RIKTIG,0)+(float)sf.getInt(TOTAL_ANT_FEIL,0)) *100;
        if (Float.isNaN(percentRight)){
            lastGameStat.setText(getString(R.string.ingen_statistikk));
            allGameStats.setText(R.string.ingen_statistikk);
            deleteStats.setClickable(false);

        }else {
            deleteStats.setClickable(true);
            lastGameStat.setText(sf.getInt(ANT_RIKTIG, 0) + " riktige svar.\n" + sf.getInt(ANT_FEIL, 0) + " feil svar.\n"
                    + new DecimalFormat("#.#").format(percentRight) + " % riktig");
            allGameStats.setText(sf.getInt(TOTAL_ANT_RIKTIG, 0)+ " riktige svar. \n" + sf.getInt(TOTAL_ANT_FEIL, 0) + " feil svar.\n"
                    + new DecimalFormat("#.#").format(percentRightMax) + " % riktig");
            //designvalg å slette all statistikk. her vil 'percentRightMax' alltid være NaN
            //om 'percentRight' er det, så jeg sjekker ikke opp mot begge.
        }
        editor.putInt(TOTAL_ANT_RIKTIG,sf.getInt(TOTAL_ANT_RIKTIG,0)).apply();
        editor.putInt(TOTAL_ANT_FEIL, sf.getInt(TOTAL_ANT_FEIL, 0)).apply();
    }
    public void deleteStatistics(View view){
        editor.putInt(ANT_RIKTIG, 0).apply();
        editor.putInt(ANT_FEIL, 0).apply();
        editor.putInt(TOTAL_ANT_FEIL, 0).apply();
        editor.putInt(TOTAL_ANT_RIKTIG, 0).apply();
        recreate();
    }
}
