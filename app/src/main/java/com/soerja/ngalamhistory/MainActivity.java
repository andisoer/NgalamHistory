package com.soerja.ngalamhistory;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soerja.ngalamhistory.article.sejarah.ArtikelListActivity;
import com.soerja.ngalamhistory.manage_kategori.ListKategori;
import com.soerja.ngalamhistory.article.sejarah.AllArtActivity;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout DL;
    private ActionBarDrawerToggle swipe;
    private NavigationView NV;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference referenceKategori;
    private RecyclerView  recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        referenceKategori = FirebaseDatabase.getInstance().getReference().child("Kategori");

        recyclerView = findViewById(R.id.recyclerviewMainActivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        DL = findViewById(R.id.activity_main);
        swipe = new ActionBarDrawerToggle(MainActivity.this, DL,R.string.Open, R.string.Close);

        DL.addDrawerListener(swipe);
        swipe.syncState();

        NV = findViewById(R.id.navbar);

        firebaseAuth = FirebaseAuth.getInstance();

        NV.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.allartic:
                        Intent intall = new Intent(MainActivity.this, AllArtActivity.class);
                        startActivity(intall);
                        break;
                    case R.id.recomended:
                        Intent intrecomend = new Intent(MainActivity.this, RecomendedActivity.class);
                        startActivity(intrecomend);
                        break;
                    case R.id.liked:
                        Intent intliked = new Intent(MainActivity.this, LikedActivity.class);
                        startActivity(intliked);
                        break;
                    case R.id.about:
                        Intent intbout = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intbout);
                        break;
                    case R.id.setting:
                        Intent intset = new Intent(MainActivity.this, SettingActivity.class);
                        startActivity(intset);
                        break;
                    case R.id.LogOut:
                        showDialog();


                }
                return true;
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user == null){

                }
            }
        };

        displayKategoriMain();
    }

    private void displayKategoriMain() {
        FirebaseRecyclerAdapter<ListKategori, ViewHolderKategoriMain> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ListKategori, ViewHolderKategoriMain>(
                        ListKategori.class,
                        R.layout.cardview_activity_main,
                        ViewHolderKategoriMain.class,
                        referenceKategori

                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderKategoriMain viewHolder, ListKategori model, int position) {
                        viewHolder.setJenis_kategori(model.getJenis_kategori());
                        viewHolder.setGambar_kategori(getApplicationContext(), model.getGambar_kategori());
                        viewHolder.setDeskripsi_kategori(model.getDeskripsi_kategori());

                        final String kategori = model.getJenis_kategori();

                        viewHolder.button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(MainActivity.this, ArtikelListActivity.class);
                                intent.putExtra("kategori", kategori);
                                startActivity(intent);
                            }
                        });

                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ViewHolderKategoriMain extends RecyclerView.ViewHolder{
        View mView;
        Button button;

        public ViewHolderKategoriMain(View itemView){
            super(itemView);
            mView = itemView;

            button = mView.findViewById(R.id.button);

        }

        public void setJenis_kategori(String jenis_kategori){
            TextView judul_kategori = mView.findViewById(R.id.judul_kategori_main);
            judul_kategori.setText(jenis_kategori);
        }

        public void setGambar_kategori(Context ctx, String gambar_kategori){
            ImageView gambar_kategori_home = mView.findViewById(R.id.kategoriGambarMainActivity);
            Picasso.with(ctx).load(gambar_kategori).into(gambar_kategori_home);
        }

        public void setDeskripsi_kategori(String deskripsi_kategori){
            TextView deskripsi_kategori_main = mView.findViewById(R.id.deskripsi_singkat_kategori_main);
            deskripsi_kategori_main.setText(deskripsi_kategori);
        }
    }

    private void showDialog(){
        AlertDialog.Builder Dialog = new AlertDialog.Builder(this);
        Dialog.setTitle("Sign Out")
                .setMessage("Anda Ingin Keluar?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.signOut();
                        finish();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                Dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK){
            showDialog();
        }
        return true;
    }

    @Override
    protected void onStart(){
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop(){
        super.onStop();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem Item){
        if(swipe.onOptionsItemSelected(Item))
            return true;

        return super.onOptionsItemSelected(Item);
    }


}
