package com.soerja.ngalamhistory.article.sejarah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soerja.ngalamhistory.R;
import com.squareup.picasso.Picasso;

public class AllArtActivity extends AppCompatActivity{

    private DatabaseReference artikelReference;
    private RecyclerView listArtAll;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allartc);

        Toolbar toolbar = findViewById(R.id.toolbarallart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        artikelReference = FirebaseDatabase.getInstance().getReference().child("Artikel");

        listArtAll = findViewById(R.id.recyclerviewAllArt);
        listArtAll.setLayoutManager(new LinearLayoutManager(this));

        displayArtikel();

    }

    private void displayArtikel() {
        FirebaseRecyclerAdapter<ArtikelAdapter, ViewHolderAllArtikel> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ArtikelAdapter, ViewHolderAllArtikel>(
                        ArtikelAdapter.class,
                        R.layout.cardview_layout_all_article,
                        ViewHolderAllArtikel.class,
                        artikelReference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderAllArtikel viewHolder, ArtikelAdapter model, int position) {
                        final String id_artikel = getRef(position).getKey();

                        viewHolder.setJudul_artikel(model.getJudul_artikel());
                        viewHolder.setKategori(model.getKategori());
                        viewHolder.setJudul_gambar(getApplicationContext(), model.getJudul_gambar());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(AllArtActivity.this, ArtikelActivity.class);
                                intent.putExtra("id_artikel", id_artikel);
                                startActivity(intent);
                            }
                        });
                    }
                };

        listArtAll.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ViewHolderAllArtikel extends RecyclerView.ViewHolder{
        View mView;

        public ViewHolderAllArtikel(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setJudul_artikel(String judul_artikel){
            TextView judulArtikel = mView.findViewById(R.id.titleArtikelAll);
            judulArtikel.setText(judul_artikel);
        }

        public void setKategori(String kategori){
            TextView Kategori = mView.findViewById(R.id.KategoriAllArtikel);
            Kategori.setText(kategori);
        }

        public void setJudul_gambar(Context ctx, String judul_gambar){
            ImageView gambar = mView.findViewById(R.id.titleArtikelAllGambar);
            Picasso.with(ctx).load(judul_gambar).into(gambar);
        }
    }
}
