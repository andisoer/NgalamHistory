package com.soerja.ngalamhistory.article.sejarah;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soerja.ngalamhistory.R;
import com.soerja.ngalamhistory.article.sejarah.photodesc.ListKomentAdapter;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ComentActivity extends AppCompatActivity {

    private EditText textKomen;
    private ImageButton submitKomen;
    private RecyclerView recyclerView;
    private String id_artikel_komen, userUID, waktuKomen;
    private FirebaseAuth mAuth;
    private DatabaseReference userRef, postRef;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarcomment);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id_artikel_komen = intent.getExtras().getString("id_artikel");

        textKomen = (EditText)findViewById(R.id.textKomen);
        submitKomen = (ImageButton)findViewById(R.id.submitKomen);
        recyclerView = (RecyclerView)findViewById(R.id.commentRecylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ComentActivity.this));

        mAuth = FirebaseAuth.getInstance();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        postRef = FirebaseDatabase.getInstance().getReference().child("Artikel").child(id_artikel_komen).child("Koment");
        userUID = mAuth.getCurrentUser().getUid();

        submitKomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userRef.child(userUID).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()){
                            String username = dataSnapshot.child("userName").getValue().toString();
                            inputComment(username);

                            textKomen.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        tampilKomen();
    }

    private void tampilKomen() {
        FirebaseRecyclerAdapter<ListKomentAdapter, ViewHolderKomen> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<ListKomentAdapter, ViewHolderKomen>(
                        ListKomentAdapter.class,
                        R.layout.cardview_comment_list,
                        ViewHolderKomen.class,
                        postRef

                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderKomen viewHolder, ListKomentAdapter model, int position) {
                        viewHolder.setUserName(model.getUserName());
                        viewHolder.setKomentar(model.getKomentar());
                        viewHolder.setWaktu(model.getWaktu());
                    }
                };
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ViewHolderKomen extends RecyclerView.ViewHolder{

        View mView;

        public ViewHolderKomen(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setUserName(String userName){
            TextView username = (TextView)mView.findViewById(R.id.usernameKomen);
            username.setText(userName);
        }

        public void setKomentar(String komentar){
            TextView Komentar = (TextView)mView.findViewById(R.id.komenUser);
            Komentar.setText(komentar);
        }

        public void setWaktu(String waktu){
            TextView Waktukomen = (TextView)mView.findViewById(R.id.timeKomen);
            Waktukomen.setText(waktu);
        }



    }

    private void inputComment(String username) {
        String koment = textKomen.getText().toString();
        String userName = username;

        if(TextUtils.isEmpty(koment)){
            Toast.makeText(ComentActivity.this, "Silahkan Ketikkan Komentar !", Toast.LENGTH_SHORT).show();
        }
        else{
            Calendar waktukomen =  Calendar.getInstance();
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            waktuKomen = format.format(waktukomen.getTime());

            String uid = postRef.push().getKey();

            AddKomenAdapter komenAdapter = new AddKomenAdapter(userName, koment, waktuKomen);
            postRef.child(uid).setValue(komenAdapter);

        }
    }
}
