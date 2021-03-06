package com.mywill.soundrecorder.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.mywill.soundrecorder.R;
import com.mywill.soundrecorder.fragments.PurchaseFragment;
import com.mywill.soundrecorder.fragments.SettingsFragment;

/**
 * Created by Daniel on 5/22/2017.
 */

public class SettingsActivity extends android.support.v7.app.AppCompatActivity {


  @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.action_settings);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        if (getIntent()!= null && getIntent().getBooleanExtra("openPurchaseFragment", false)){
          getFragmentManager()
              .beginTransaction()
              .replace(R.id.container, new PurchaseFragment())
              .commit();
        } else {
          getFragmentManager()
              .beginTransaction()
              .replace(R.id.container, new SettingsFragment())
              .commit();
        }


    }

}
