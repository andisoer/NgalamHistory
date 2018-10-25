package com.soerja.ngalamhistory.article.sejarah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soerja.ngalamhistory.ComentActivity;
import com.soerja.ngalamhistory.DuluSekActivity;
import com.soerja.ngalamhistory.R;
import com.soerja.ngalamhistory.article.sejarah.photodesc.PhotoDescActivity;
import com.squareup.picasso.Picasso;

public class SejarahArtActivity extends AppCompatActivity {

    private TextView  tvDesc;
    private ImageView ivCOL;
    private String Judul = null, id_artikel = null, userUID;
    private DatabaseReference artReference, likeReference, userRef;
    private FirebaseAuth mAuth;
    private ImageButton like;
    private boolean cekLike = false;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sejarahart);

        Toolbar toolbarhisart = (Toolbar)findViewById(R.id.toolbarhisart);
        setSupportActionBar(toolbarhisart);

        mAuth = FirebaseAuth.getInstance();
        artReference = FirebaseDatabase.getInstance().getReference().child("Artikel");
        likeReference = FirebaseDatabase.getInstance().getReference().child("Likes");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        userUID = mAuth.getCurrentUser().getUid();

        Intent intent = getIntent();
        id_artikel = intent.getExtras().getString("id_artikel");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final CollapsingToolbarLayout collapstoolhisart = (CollapsingToolbarLayout)findViewById(R.id.coollapshisart);

        collapstoolhisart.setCollapsedTitleTextColor(ContextCompat.getColor(SejarahArtActivity.this, R.color.colorWhite ));
        collapstoolhisart.setExpandedTitleColor(ContextCompat.getColor(SejarahArtActivity.this, R.color.colorWhite));

        tvDesc = (TextView)findViewById(R.id.Artic_desc);
        ivCOL = (ImageView)findViewById(R.id.imgcollaps);

        //Deklarasi Button
        Button btnPD = (Button)findViewById(R.id.btPhotoDesc);
        Button btnCMNT = (Button)findViewById(R.id.comment);
        Button btnTN = (Button)findViewById(R.id.thennow);
        like = (ImageButton)findViewById(R.id.likeButton);

        artReference.child(id_artikel).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String judul_artikel = (String) dataSnapshot.child("judul_artikel").getValue();
                String kontent = (String) dataSnapshot.child("kontent").getValue();
                String judul_gambar = (String) dataSnapshot.child("judul_gambar").getValue();

                collapstoolhisart.setTitle(judul_artikel);
                Picasso.with(SejarahArtActivity.this).load(judul_gambar).into(ivCOL);
                tvDesc.setText(kontent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(userUID != null){
                    cekLike = true;
                    likeReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(cekLike){
                                if(dataSnapshot.child(id_artikel).hasChild(userUID)){
                                    likeReference.child(id_artikel).child(userUID).removeValue();
                                    cekLike = false;
                                }
                                else{
                                    likeReference.child(id_artikel).child(userUID).setValue(true);
                                    cekLike = false;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    Toast.makeText(SejarahArtActivity.this, "Anda Harus Login Untuk Menggunakan Fitur Ini !", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //Foto Deskripsi
        btnPD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SejarahArtActivity.this, PhotoDescActivity.class);
                startActivity(intent);
            }
        });

        //Komment
        btnCMNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SejarahArtActivity.this, ComentActivity.class);
                startActivity(intent);
            }
        });

        //Dulu Sekarang
        btnTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SejarahArtActivity.this, DuluSekActivity.class);
                startActivity(intent);
            }
        });
    }
}
