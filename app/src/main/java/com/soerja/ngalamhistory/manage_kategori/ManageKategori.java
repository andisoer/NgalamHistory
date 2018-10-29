package com.soerja.ngalamhistory.manage_kategori;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soerja.ngalamhistory.R;
import com.squareup.picasso.Picasso;

public class ManageKategori extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference referenceKategori;
    private FloatingActionButton fab;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.admin_manage_kategori);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarmanageKategori);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referenceKategori = FirebaseDatabase.getInstance().getReference().child("Kategori");

        recyclerView = (RecyclerView)findViewById(R.id.recyclerviewManageKategori);
        recyclerView.setLayoutManager(new LinearLayoutManager(ManageKategori.this));

        fab = (FloatingActionButton)findViewById(R.id.fab_add_category);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageKategori.this, AddKategori.class);
                startActivity(intent);
            }
        });

        displayKategori();
    }

    private void displayKategori() {
        FirebaseRecyclerAdapter<ListKategori, ViewHolderKategori> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ListKategori, ViewHolderKategori>(

                        ListKategori.class,
                        R.layout.cardview_manage_kategori,
                        ViewHolderKategori.class,
                        referenceKategori
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderKategori viewHolder, ListKategori model, int position) {
                        viewHolder.setJenis_kategori(model.getJenis_kategori());
                        viewHolder.setGambar_kategori(getApplicationContext(), model.getGambar_kategori());
                        viewHolder.setDeskripsi_kategori(model.getDeskripsi_kategori());
                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ViewHolderKategori extends RecyclerView.ViewHolder{
        View mView;

        public ViewHolderKategori(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setJenis_kategori(String jenis_kategori){
            TextView judul_kategori = (TextView)mView.findViewById(R.id.nama_kategori);
            judul_kategori.setText(jenis_kategori);
        }

        public void setGambar_kategori(Context ctx, String gambar_kategori){
            ImageView kategori_gambar = (ImageView)mView.findViewById(R.id.gambar_kategori);
            Picasso.with(ctx).load(gambar_kategori).into(kategori_gambar);
        }

        public void setDeskripsi_kategori(String deskripsi_kategori){
            TextView deskripsiKategori = (TextView)mView.findViewById(R.id.deskripsi_kategori_list);
            deskripsiKategori.setText(deskripsi_kategori);
        }
    }
}
