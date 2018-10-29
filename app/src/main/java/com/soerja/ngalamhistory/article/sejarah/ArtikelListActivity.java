package com.soerja.ngalamhistory.article.sejarah;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
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

public class ArtikelListActivity extends AppCompatActivity {

    private RecyclerView listArt;
    private DatabaseReference artikelReference;
    private String kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sejarah);

        //Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarhis);
        setSupportActionBar(toolbar);

        //Toolbar as Actionbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        kategori = intent.getExtras().getString("kategori");

        artikelReference = FirebaseDatabase.getInstance().getReference().child("Artikel");

        listArt = (RecyclerView) findViewById(R.id.recyclerview);
        listArt.setLayoutManager(new GridLayoutManager(this, 2));

        displayArtikel();

    }

    //Tampil ArtikelAdapterSejarah Firebase
    private void displayArtikel(){
        FirebaseRecyclerAdapter<ArtikelAdapter, ViewHolderArtikel> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ArtikelAdapter, ViewHolderArtikel>(

                        ArtikelAdapter.class,
                        R.layout.cardview_layout_article,
                        ViewHolderArtikel.class,
                        artikelReference.orderByChild("kategori").equalTo(kategori)
                )
        {
                    @Override
                    protected void populateViewHolder(ViewHolderArtikel viewHolder, ArtikelAdapter model, int position) {
                        final String id_artikel = getRef(position).getKey();

                        viewHolder.setJudul_artikel(model.getJudul_artikel());
                        viewHolder.setJudul_gambar(getApplicationContext(), model.getJudul_gambar());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(ArtikelListActivity.this, ArtikelActivity.class);
                                intent.putExtra("id_artikel", id_artikel);
                                startActivity(intent);
                            }
                        });

                    }
                };

        listArt.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ViewHolderArtikel extends RecyclerView.ViewHolder{
        View mView;

        public ViewHolderArtikel(View itemView){

            super(itemView);
            mView = itemView;

        }

        public void setJudul_artikel(String judul_artikel){
            TextView judulArtikel = (TextView)mView.findViewById(R.id.judulart_id_list);
            judulArtikel.setText(judul_artikel);
        }

        public void setJudul_gambar(Context ctx, String judul_gambar){
            ImageView judulGambar = (ImageView)mView.findViewById(R.id.gambarart_id_list);
            Picasso.with(ctx).load(judul_gambar).into(judulGambar);

        }



    }

}