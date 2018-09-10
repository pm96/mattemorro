package com.domene.tilfeldig.s305471mapp1;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;
import java.util.Locale;

public class SetPreferencesActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();
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

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            final ArrayList<CheckBoxPreference> språk = new ArrayList<>();
            Preference.OnPreferenceClickListener listener = new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    for(CheckBoxPreference cbp : språk){
                        if(!cbp.getKey().equals(preference.getKey()) && cbp.isChecked()){
                            cbp.setChecked(false);
                            Resources res = getContext().getResources();
                            DisplayMetrics dm = res.getDisplayMetrics();
                            Configuration conf = new Configuration(res.getConfiguration());
                            conf.setLocale(new Locale("no"));
                            res.updateConfiguration(conf, dm);
                        }else if(cbp.getKey().equals(preference.getKey()) && !cbp.isChecked()){
                            cbp.setChecked(true);
                            cbp.setChecked(false);
                            Resources res = getContext().getResources();
                            DisplayMetrics dm = res.getDisplayMetrics();
                            Configuration conf = new Configuration(res.getConfiguration());
                            conf.setLocale(new Locale("de"));
                            res.updateConfiguration(conf, dm);
                        }
                    }
                    return false;
                }
            };
            CheckBoxPreference cbpSpråkNorsk = (CheckBoxPreference)getPreferenceManager().findPreference("språkNorsk");
            cbpSpråkNorsk.setOnPreferenceClickListener(listener);

            CheckBoxPreference cbpSpråkTysk = (CheckBoxPreference)getPreferenceManager().findPreference("språkTysk");
            cbpSpråkTysk.setOnPreferenceClickListener(listener);

            språk.add(cbpSpråkNorsk);
            språk.add(cbpSpråkTysk);

            final ArrayList<CheckBoxPreference> spørsmål = new ArrayList<>();
            Preference.OnPreferenceClickListener listenerForSpørsmål = new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    for(CheckBoxPreference cbp : spørsmål){
                        if(!cbp.getKey().equals(preference.getKey()) && cbp.isChecked()){
                            cbp.setChecked(false);
                        }else if(cbp.getKey().equals(preference.getKey()) && !cbp.isChecked()){
                            cbp.setChecked(true);

                        }
                    }
                    return false;
                }
            };
            CheckBoxPreference cbp5spm = (CheckBoxPreference)getPreferenceManager().findPreference("femSpørsmål");
            cbp5spm.setOnPreferenceClickListener(listenerForSpørsmål);

            CheckBoxPreference cbp10spm= (CheckBoxPreference)getPreferenceManager().findPreference("tiSpørsmål");
            cbp10spm.setOnPreferenceClickListener(listenerForSpørsmål);

            CheckBoxPreference cbp25spm= (CheckBoxPreference)getPreferenceManager().findPreference("tjueFemSpørsmål");
            cbp25spm.setOnPreferenceClickListener(listenerForSpørsmål);

            spørsmål.add(cbp5spm);
            spørsmål.add(cbp10spm);
            spørsmål.add(cbp25spm);

        }

    }
}
