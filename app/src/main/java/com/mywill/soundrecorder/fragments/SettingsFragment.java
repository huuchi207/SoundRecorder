package com.mywill.soundrecorder.fragments;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;

import com.mywill.soundrecorder.BuildConfig;
import com.mywill.soundrecorder.MySharedPreferences;
import com.mywill.soundrecorder.R;
import com.mywill.soundrecorder.activities.SettingsActivity;

/**
 * Created by Daniel on 5/22/2017.
 */

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        CheckBoxPreference highQualityPref = (CheckBoxPreference) findPreference(getResources()
            .getString(R.string.pref_high_quality_key));
        highQualityPref.setChecked(MySharedPreferences.getPrefHighQuality(getActivity()));
        highQualityPref.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                MySharedPreferences.setPrefHighQuality(getActivity(), (boolean) newValue);
                return true;
            }
        });

        Preference aboutPref = findPreference(getString(R.string.pref_about_key));
        aboutPref.setSummary(getString(R.string.pref_about_desc, BuildConfig.VERSION_NAME));
        aboutPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                LicensesFragment licensesFragment = new LicensesFragment();
                licensesFragment.show(getActivity().getFragmentManager().beginTransaction(), "dialog_licenses");
                return true;
            }
        });

      Preference upgrade = findPreference(getString(R.string.pref_upgrade));
      upgrade.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
          getFragmentManager()
              .beginTransaction()
              .replace(R.id.container, new PurchaseFragment())
              .commit();
          return true;
        }
      });
    }

}
