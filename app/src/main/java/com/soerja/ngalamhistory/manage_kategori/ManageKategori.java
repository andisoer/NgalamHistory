package com.soerja.ngalamhistory.manage_kategori;

import android.content.DialogInterface;
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
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soerja.ngalamhistory.R;

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
                addKategori();
            }
        });

        displayKategori();
    }

    private void addKategori() {
        AlertDialog.Builder InputKategori = new AlertDialog.Builder(ManageKategori.this);
        LayoutInflater layoutInflater = LayoutInflater.from(ManageKategori.this);
        InputKategori.setTitle("Tambah Kategori");

        input = new EditText(this);
        InputKategori.setView(input);

        InputKategori.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String kategori = input.getText().toString().trim();
                FirebaseDatabase.getInstance().getReference("Kategori").push().child("Jenis_Kategori").setValue(kategori);
            }
        });
        InputKategori.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = InputKategori.create();
        alertDialog.show();
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
                        viewHolder.setJenis_Kategori(model.getJenis_Kategori());
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

        public void setJenis_Kategori(String jenis_Kategori){
            TextView Kategori = (TextView)mView.findViewById(R.id.nama_kategori);
            Kategori.setText(jenis_Kategori);
        }
    }
}
