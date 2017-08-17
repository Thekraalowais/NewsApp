package com.example.thekra.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public static class NewsPreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.setting);
            Preference section = findPreference(getString(R.string.settings_section_key));
            bind(section);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
         String value=newValue.toString();
            if(preference instanceof ListPreference){
                ListPreference listPreference=(ListPreference) preference;
                int index=listPreference.findIndexOfValue(value);
                if(index>=0){
                    CharSequence[] labels=listPreference.getEntries();
                    preference.setSummary(labels[index]);
                }
            }else{
                preference.setSummary(value);
            }

            return true;
        }


        private void bind(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
