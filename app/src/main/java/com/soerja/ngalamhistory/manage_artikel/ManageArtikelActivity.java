package com.soerja.ngalamhistory.manage_artikel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

import org.w3c.dom.Text;

public class ManageArtikelActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private DatabaseReference artikelAdminReference;

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

        artikelAdminReference = FirebaseDatabase.getInstance().getReference().child("Artikel");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageArtikelActivity.this, AddArtikel.class);
                startActivity(intent);
            }
        });

        displayArtikelAdmin();
    }

    private void displayArtikelAdmin() {
        FirebaseRecyclerAdapter<ListArtikelAdmin, ViewHolderArtikelAdmin> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ListArtikelAdmin, ViewHolderArtikelAdmin>(

                        ListArtikelAdmin.class,
                        R.layout.cardview_article_manage_admin,
                        ViewHolderArtikelAdmin.class,
                        artikelAdminReference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderArtikelAdmin viewHolder, ListArtikelAdmin model, int position) {
                        viewHolder.setJudul_artikel(getApplicationContext(), model.getJudul_gambar());
                        viewHolder.setJudul_artikel(model.getJudul_artikel());
                        viewHolder.setKategori(model.getKategori());
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ViewHolderArtikelAdmin extends RecyclerView.ViewHolder{

        View mView;

        public ViewHolderArtikelAdmin(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setJudul_artikel(Context ctx, String judul_artikel){
            ImageView judul_gambar = (ImageView)mView.findViewById(R.id.titleArtikelAdminGambar);
            Picasso.with(ctx).load(judul_artikel).into(judul_gambar);
        }

        public void setJudul_artikel(String judul_artikel){
            TextView judul = (TextView)mView.findViewById(R.id.titleArtikelAdmin);
            judul.setText(judul_artikel);
        }

        public void setKategori(String kategori){
            TextView kategori_artikel_admin = (TextView)mView.findViewById(R.id.kategoriArtikelAdmin);
            kategori_artikel_admin.setText(kategori);
        }

    }
}
