package com.soerja.ngalamhistory;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soerja.ngalamhistory.article.sejarah.ArtikelActivity;
import com.soerja.ngalamhistory.article.sejarah.ArtikelAdapter;
import com.squareup.picasso.Picasso;

public class LikedActivity extends AppCompatActivity {

    private DatabaseReference likedReference;
    private FirebaseAuth mAuth;
    private String userUID;
    private RecyclerView listArtLiked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_liked);

        Toolbar toolbar = findViewById(R.id.toolbarliked);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        likedReference = FirebaseDatabase.getInstance().getReference().child("Likes");
        userUID = mAuth.getCurrentUser().getUid();

        listArtLiked = findViewById(R.id.likedArt);
        listArtLiked.setLayoutManager(new LinearLayoutManager(this));

        displayArtikel();

        /* Liked Post
        mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getUid();
        likedReference = FirebaseDatabase.getInstance().getReference().child("Likes");

        likedReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String disukai = dataSnapshot.getValue(String.class);
                    if(userUID.)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        })
        */

    }

    private void displayArtikel() {
        FirebaseRecyclerAdapter<ArtikelAdapter, ViewHolderLiked> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ArtikelAdapter, ViewHolderLiked>(
                        ArtikelAdapter.class,
                        R.layout.cardview_likedart,
                        ViewHolderLiked.class,
                        likedReference.orderByChild(userUID)
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderLiked viewHolder, ArtikelAdapter model, int position) {
                        final String id_artikel = getRef(position).getKey();

                        viewHolder.setJudul_artikel(model.getJudul_artikel());
                        viewHolder.setJudul_gambar(getApplicationContext(), model.getJudul_gambar());

                        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(LikedActivity.this, ArtikelActivity.class);
                                intent.putExtra("id_artikel", id_artikel);
                                startActivity(intent);
                            }
                        });
                    }
                };
        listArtLiked.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ViewHolderLiked extends RecyclerView.ViewHolder{
        View mView;

        public ViewHolderLiked(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setJudul_artikel(String judul_artikel){
            TextView judulArtikel = mView.findViewById(R.id.titleArtikelLiked);
            judulArtikel.setText(judul_artikel);
        }

        public void setJudul_gambar(Context ctx,String judul_gambar){
            ImageView judulGambar = mView.findViewById(R.id.titleArtikelLikedGambar);
            Picasso.with(ctx).load(judul_gambar).into(judulGambar);
        }
    }
}