package com.soerja.ngalamhistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.soerja.ngalamhistory.manage_artikel.ManageArtikelActivity;
import com.soerja.ngalamhistory.manage_kategori.ManageKategori;

public class ManageContent extends AppCompatActivity {

    Button manageArtikel, manageKategori;

    @Override
    protected void onCreate(Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        setContentView(R.layout.admin_managecontent);

       Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarmanageart);
       setSupportActionBar(toolbar);

       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       getSupportActionBar().setDisplayShowTitleEnabled(false);

       manageArtikel = (Button)findViewById(R.id.manageArtikel);
       manageKategori = (Button)findViewById(R.id.manageKategori);

       manageArtikel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(ManageContent.this, ManageArtikelActivity.class);
               startActivity(intent);
           }
       });

       manageKategori.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(ManageContent.this, ManageKategori.class);
               startActivity(intent);
           }
       });
    }
}
