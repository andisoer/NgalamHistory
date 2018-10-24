package com.soerja.ngalamhistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.soerja.ngalamhistory.article.sejarah.Artikel;
import com.soerja.ngalamhistory.article.sejarah.SejarahArtActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SejarahActivty extends AppCompatActivity {


    private RecyclerView listArt;
    private DatabaseReference artikelReference;

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

        artikelReference = FirebaseDatabase.getInstance().getReference().child("ArtikelSejarah");

        listArt = (RecyclerView) findViewById(R.id.recyclerview);
        listArt.setLayoutManager(new GridLayoutManager(this, 2));

        displayArtikel();

    }

    //Tampil Artikel Firebase
    private void displayArtikel(){
        FirebaseRecyclerAdapter<Artikel, ViewHolderArtikel> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Artikel, ViewHolderArtikel>(

                        Artikel.class,
                        R.layout.cardview_layout_article,
                        ViewHolderArtikel.class,
                        artikelReference
                )
        {
                    @Override
                    protected void populateViewHolder(ViewHolderArtikel viewHolder, Artikel model, int position) {

                        viewHolder.setJudul(model.getJudul());
                        viewHolder.setJudulImage(getApplicationContext(), model.getJudulImage());
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

        public void setJudul(String judul){
            TextView title = (TextView)mView.findViewById(R.id.judulart_id_list);
            title.setText(judul);
        }

        public void setJudulImage(Context ctx, String judulImage){
            ImageView titleIMG = (ImageView)mView.findViewById(R.id.gambarart_id_list);
            Picasso.with(ctx).load(judulImage).into(titleIMG);
        }

    }


}