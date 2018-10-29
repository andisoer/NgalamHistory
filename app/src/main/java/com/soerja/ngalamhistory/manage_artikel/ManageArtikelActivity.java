package com.soerja.ngalamhistory.manage_artikel;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soerja.ngalamhistory.R;
import com.soerja.ngalamhistory.manage_kategori.ManageKategori;
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
                    protected void populateViewHolder(final ViewHolderArtikelAdmin viewHolder, ListArtikelAdmin model, int position) {
                        viewHolder.setJudul_artikel(getApplicationContext(), model.getJudul_gambar());
                        viewHolder.setJudul_artikel(model.getJudul_artikel());
                        viewHolder.setKategori(model.getKategori());

                        final String judul_artikel = model.getJudul_artikel();
                        final String id_artikel = getRef(position).getKey();

                        viewHolder.menu_artikel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PopupMenu popupMenu = new PopupMenu(ManageArtikelActivity.this, viewHolder.menu_artikel);
                                popupMenu.inflate(R.menu.menu_cardview_manage_artikel);
                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()){
                                            case R.id.menu_artikel_admin_edit:
                                                Toast.makeText(ManageArtikelActivity.this, "Edit Artikel", Toast.LENGTH_SHORT).show();
                                                break;
                                            case R.id.menu_artikel_admin_delete:
                                                tampilDialogDelete(judul_artikel, id_artikel);
                                                break;
                                            default:
                                                break;
                                        }
                                        return false;
                                    }
                                });
                                popupMenu.show();
                            }
                        });
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void tampilDialogDelete(final String judul_artikel, final String id_artikel) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageArtikelActivity.this);
        LayoutInflater layoutInflater = LayoutInflater.from(ManageArtikelActivity.this);
        alertDialog.setTitle("Delete Artikel");
        alertDialog.setMessage("Delete Artikel "+judul_artikel+" ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                artikelAdminReference.child(id_artikel).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ManageArtikelActivity.this, "Artikel "+judul_artikel+" Dihapus !", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();

    }

    public static class ViewHolderArtikelAdmin extends RecyclerView.ViewHolder{

        View mView;
        TextView menu_artikel;

        public ViewHolderArtikelAdmin(View itemView){
            super(itemView);
            mView = itemView;

            menu_artikel = (TextView)mView.findViewById(R.id.menu_artikel_admin);
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
