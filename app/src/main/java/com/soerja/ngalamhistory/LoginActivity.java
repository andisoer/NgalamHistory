package com.soerja.ngalamhistory;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {

    private Button forget, regis, login;
    private TextInputEditText formEmail, formPass;
    private ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference reference;
    private String getEmail, getPass;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Toolbar As ActionBar
        Toolbar toolbar = findViewById(R.id.toolbarlog);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        formEmail = findViewById(R.id.emaillog);
        formPass = findViewById(R.id.passlog);

        firebaseAuth = FirebaseAuth.getInstance();

        //Button Deklarasi
        forget = findViewById(R.id.forgetpass);
        regis = findViewById(R.id.regis);
        login = findViewById(R.id.btLogin);


        //Lupa Password
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intfor = new Intent(LoginActivity.this, ForgetActivity.class);
                startActivity(intfor);
            }
        });

        //Daftar
        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intregis = new Intent(LoginActivity.this, RegisActivity.class);
                startActivity(intregis);
                finish();
            }
        });

        //Tombol Untuk Login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getEmail = formEmail.getText().toString().trim(); //get Email dari TextInputEditText formEmail
                getPass = formPass.getText().toString().trim(); //get Email dari TextInputEditText formPass

                if(TextUtils.isEmpty(getEmail)){
                    Toast.makeText(LoginActivity.this, "Masukkan Email!", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(getPass)){
                    Toast.makeText(LoginActivity.this, "Masukkan Password", Toast.LENGTH_SHORT).show();
                }
                else{
                    showProgressDialog();
                    firebaseAuth.signInWithEmailAndPassword(getEmail, getPass)// Sign in/Autentikasi Dengan email dan pass (Login Seperti Biasa)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        onAuthSuccess(task.getResult().getUser());
                                    }
                                    else{
                                        Toast.makeText(LoginActivity.this, "User Tidak Ditemukan!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });

    }

    //Method onAuthSuccess()
    private void onAuthSuccess(FirebaseUser user) {//Memanggil Data User yang login
        if(user != null){//Jika User Tidak Kosong
            reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user.getUid()).child("type");//Membuat Reference dengan isi tabel "Users" dan
                                                                                                                        //kolomnya "type"//type berisi user atau admin
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String type = dataSnapshot.getValue(String.class);//DataSnapshot (Memanggil Data Dari Database)
                        if(type.equals("admin") ){//Jika type sama dengan Admin maka ke halaman Admin
                            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                            Toast.makeText(LoginActivity.this, "Selamat Datang Admin ", Toast.LENGTH_SHORT).show();
                            finish();
                        }else{//Lainnya, jika type tidak sama dengan Admin, Ke halaman user
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            Toast.makeText(LoginActivity.this, "Anda Login Sebagai User", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                }
                //Lek Bingung Tanya Saia
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, databaseError.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void showProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCancelable(true);
    }


    // Tombol Home
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_toolbar_btngreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.Home){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
