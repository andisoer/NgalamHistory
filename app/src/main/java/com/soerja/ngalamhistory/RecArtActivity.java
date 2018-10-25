package com.soerja.ngalamhistory;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class RecArtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recart);

        Toolbar toolbarhisart = (Toolbar) findViewById(R.id.toolbarhisart);
        setSupportActionBar(toolbarhisart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapstoolhisart = (CollapsingToolbarLayout) findViewById(R.id.coollapshisart);
        collapstoolhisart.setTitle("Judul ArtikelAdapterSejarah");

        collapstoolhisart.setCollapsedTitleTextColor(ContextCompat.getColor(RecArtActivity.this, R.color.colorWhite));
        collapstoolhisart.setExpandedTitleColor(ContextCompat.getColor(RecArtActivity.this, R.color.colorWhite));
    }
}
