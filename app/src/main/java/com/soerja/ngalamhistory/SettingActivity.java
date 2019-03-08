package com.soerja.ngalamhistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class SettingActivity extends AppCompatActivity{

    private Spinner spinner;
    private AdView adView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = findViewById(R.id.toolbarset);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerSelectionTheme();

        /////Test adMob
        adView = findViewById(R.id.adViewBannerFooterSettings);

        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("A3PFG17A14004363")
                .build();

        adView.loadAd(adRequest);
        /////
    }

    private void spinnerSelectionTheme() {
        spinner = findViewById(R.id.spinnerTema);
        spinner.setSelection(ThemeApplication.currentPosition);
        ThemeApplication.currentPosition = spinner.getSelectedItemPosition();

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(ThemeApplication.currentPosition != i){
                    Utils.changeToTheme(SettingActivity.this, i);
                }
                ThemeApplication.currentPosition = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent intent = new Intent(SettingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return true;
    }
}
