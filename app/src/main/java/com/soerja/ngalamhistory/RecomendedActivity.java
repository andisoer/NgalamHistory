package com.soerja.ngalamhistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class RecomendedActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekomended);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarrecomend);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button bacaArt = (Button)findViewById(R.id.bacaRecArt);

        bacaArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecomendedActivity.this, RecArtActivity.class );
                startActivity(intent);
            }
        });

    }
}