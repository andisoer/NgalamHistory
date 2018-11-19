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
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
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

        Toolbar toolbar = findViewById(R.id.toolbarmanageKategori);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referenceKategori = FirebaseDatabase.getInstance().getReference().child("Kategori");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ManageKategori.this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.recyclerviewManageKategori);
        recyclerView.setLayoutManager(linearLayoutManager);

        fab = findViewById(R.id.fab_add_category);

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
                    protected void populateViewHolder(final ViewHolderKategori viewHolder, final ListKategori model, int position) {
                        viewHolder.setJenis_kategori(model.getJenis_kategori());
                        viewHolder.setGambar_kategori(getApplicationContext(), model.getGambar_kategori());
                        viewHolder.setDeskripsi_kategori(model.getDeskripsi_kategori());

                        final String judulKategori = model.getJenis_kategori();
                        final String id_kategori = getRef(position).getKey();

                        viewHolder.menu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                PopupMenu popupMenu = new PopupMenu(ManageKategori.this, viewHolder.menu);
                                popupMenu.inflate(R.menu.menu_cardview_manage_kategori);
                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem menuItem) {
                                        switch (menuItem.getItemId()){
                                            case R.id.kategori_edit:
                                                Toast.makeText(ManageKategori.this, "Edit", Toast.LENGTH_SHORT).show();
                                                break;
                                            case R.id.kategori_delete:
                                                tampilDialogDelete(judulKategori, id_kategori);
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

    private void tampilDialogDelete(final String judulKategori, final String id_kategori) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageKategori.this);
        LayoutInflater layoutInflater = LayoutInflater.from(ManageKategori.this);
        alertDialog.setTitle("Delete Kategori");
        alertDialog.setMessage("Delete Kategori "+judulKategori+" ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                referenceKategori.child(id_kategori).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(ManageKategori.this, "Kategori "+judulKategori+" Berhasil Dihapus !", Toast.LENGTH_SHORT).show();
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

    public static class ViewHolderKategori extends RecyclerView.ViewHolder{
        View mView;
        TextView menu;

        public ViewHolderKategori(View itemView){
            super(itemView);
            mView = itemView;

            menu = mView.findViewById(R.id.menu_kategori);
        }

        public void setJenis_kategori(String jenis_kategori){
            TextView judul_kategori = mView.findViewById(R.id.nama_kategori);
            judul_kategori.setText(jenis_kategori);
        }

        public void setGambar_kategori(Context ctx, String gambar_kategori){
            ImageView kategori_gambar = mView.findViewById(R.id.gambar_kategori);
            Picasso.with(ctx).load(gambar_kategori).into(kategori_gambar);
        }

        public void setDeskripsi_kategori(String deskripsi_kategori){
            TextView deskripsiKategori = mView.findViewById(R.id.deskripsi_kategori_list);
            deskripsiKategori.setText(deskripsi_kategori);
        }
    }
}
