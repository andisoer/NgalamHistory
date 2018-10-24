package com.soerja.ngalamhistory.manage_artikel;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.soerja.ngalamhistory.R;
import com.soerja.ngalamhistory.manage_kategori.ManageKategori;

public class ManageArtikelActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manage_artikel);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarmanageArtikel);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerviewManageArtikel);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageArtikelActivity.this));

        fab = (FloatingActionButton)findViewById(R.id.fab_add_artikel);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageArtikelActivity.this, AddArtikel.class);
                startActivity(intent);
            }
        });
    }
}
