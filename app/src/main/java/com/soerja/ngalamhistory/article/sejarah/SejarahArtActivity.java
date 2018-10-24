package com.soerja.ngalamhistory.article.sejarah;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.soerja.ngalamhistory.ComentActivity;
import com.soerja.ngalamhistory.DuluSekActivity;
import com.soerja.ngalamhistory.R;
import com.soerja.ngalamhistory.article.sejarah.photodesc.PhotoDescActivity;

public class SejarahArtActivity extends AppCompatActivity {

    private TextView  tvDesc;
    private ImageView ivCOL;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sejarahart);

        Toolbar toolbarhisart = (Toolbar)findViewById(R.id.toolbarhisart);
        setSupportActionBar(toolbarhisart);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapstoolhisart = (CollapsingToolbarLayout)findViewById(R.id.coollapshisart);
        collapstoolhisart.setTitle("Judul Artikel");

        collapstoolhisart.setCollapsedTitleTextColor(ContextCompat.getColor(SejarahArtActivity.this, R.color.colorWhite ));
        collapstoolhisart.setExpandedTitleColor(ContextCompat.getColor(SejarahArtActivity.this, R.color.colorWhite));

        tvDesc = (TextView)findViewById(R.id.Artic_desc);
        ivCOL = (ImageView)findViewById(R.id.imgcollaps);

        Intent intent = getIntent();
        String Judul = intent.getExtras().getString("Judul");
        int Deskrips = intent.getExtras().getInt("Deskripsi");
        int Gambar = intent.getExtras().getInt("Gambar");

        tvDesc.setText(Deskrips);
        ivCOL.setImageResource(Gambar);

        //Deklarasi Button
        Button btnPD = (Button)findViewById(R.id.btPhotoDesc);
        Button btnCMNT = (Button)findViewById(R.id.comment);
        Button btnTN = (Button)findViewById(R.id.thennow);

        //Foto Deskripsi
        btnPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SejarahArtActivity.this, PhotoDescActivity.class);
                startActivity(intent);
            }
        });

        //Komment
        btnCMNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SejarahArtActivity.this, ComentActivity.class);
                startActivity(intent);
            }
        });

        //Dulu Sekarang
        btnTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SejarahArtActivity.this, DuluSekActivity.class);
                startActivity(intent);
            }
        });
    }
}
