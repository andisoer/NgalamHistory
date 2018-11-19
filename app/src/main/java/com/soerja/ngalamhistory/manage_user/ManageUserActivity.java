package com.soerja.ngalamhistory.manage_user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soerja.ngalamhistory.R;

public class ManageUserActivity extends AppCompatActivity{

    private RecyclerView recyclerView;
    private DatabaseReference userReference;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_manageuser);

        Toolbar toolbar = findViewById(R.id.toolbarmanageuser);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView = findViewById(R.id.recyclerviewuser);
        recyclerView.setLayoutManager(linearLayoutManager);

        userReference = FirebaseDatabase.getInstance().getReference().child("Users");

        displayUser();

    }

    private void displayUser() {
        FirebaseRecyclerAdapter<UserList, ViewHolderUser> firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<UserList, ViewHolderUser>(

                        UserList.class,
                        R.layout.cardview_manage_user,
                        ViewHolderUser.class,
                        userReference

                ) {
                    @Override
                    protected void populateViewHolder(ViewHolderUser viewHolder, UserList model, int position) {

                        viewHolder.setEmail(model.getEmail());
                        viewHolder.setPass(model.getPass());
                        viewHolder.setType(model.getType());
                        viewHolder.setUserName(model.getUserName());

                        final String email = model.getEmail();
                        final String UID = getRef(position).getKey();

                        viewHolder.mView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View view) {
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ManageUserActivity.this);
                                LayoutInflater layoutInflater = LayoutInflater.from(ManageUserActivity.this);
                                alertDialog.setTitle("Delete User?");
                                alertDialog.setMessage("Hapus Akun "+ email + "?");

                                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference("Users").child(UID).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(ManageUserActivity.this, "User Berhasil Dihapus!", Toast.LENGTH_SHORT).show();
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

                                return false;
                            }
                        });

                    }
                };

        recyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class ViewHolderUser extends RecyclerView.ViewHolder{
        View mView;

        public ViewHolderUser(View itemView){

            super(itemView);
            mView = itemView;
        }

        public void setEmail(String email){
            TextView eMail = mView.findViewById(R.id.emailuser);
            eMail.setText(email);

        }

        public void setPass(String pass){
            TextView Pass = mView.findViewById(R.id.passwordUser);
            Pass.setText(pass);

        }

        public void setType(String type){
            TextView Type = mView.findViewById(R.id.typeUser);
            Type.setText(type);

        }

        public void setUserName(String userName){
            TextView Username = mView.findViewById(R.id.username);
            Username.setText(userName);
        }

    }

}
