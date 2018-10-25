package com.soerja.ngalamhistory;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisActivity extends AppCompatActivity {

    private Button btnReg;
    private TextInputEditText passReg, emailReg, userReg;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private String getEmail, getPass, getUserName, type;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis);

        Toolbar tbREG = (Toolbar)findViewById(R.id.toolbarreg);
        setSupportActionBar(tbREG);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Inisialisasi btn, TIET, PB
        btnReg = (Button)findViewById(R.id.btnReg);
        passReg = (TextInputEditText)findViewById(R.id.passreg);
        emailReg = (TextInputEditText)findViewById(R.id.emailreg);
        userReg = (TextInputEditText)findViewById(R.id.username);

        auth = FirebaseAuth.getInstance();

        //tombol registrasi
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInputanUser();
            }
        });
    }

    //Method cekInputanUser()
    private void cekInputanUser(){

        getUserName = userReg.getText().toString();
        getEmail = emailReg.getText().toString();
        getPass = passReg.getText().toString();
        type = "user";

        if(TextUtils.isEmpty(getUserName)){
            Toast.makeText(this, "Username Harus Diisi !", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(getEmail) || TextUtils.isEmpty(getPass)){
            Toast.makeText(this, "Email / Sandi Harus Di Isi !", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(getEmail)){
            Toast.makeText(this, "Email Harus Di isi!", Toast.LENGTH_SHORT).show();
        }
        else{
            if(getPass.length() < 8 ){
                Toast.makeText(this, "Password Minimal 8 Karakter !", Toast.LENGTH_SHORT).show();
            }
            else{

                showProgressDialog();
                createUserAcc();
            }
        }
    }

    private void showProgressDialog() {

        ProgressDialog loading = new ProgressDialog(this);
        loading.setMessage("Please Wait...");
        loading.setCancelable(true);
        loading.show();

    }

    private void createUserAcc(){
        auth.createUserWithEmailAndPassword(getEmail, getPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UserReg user = new UserReg(getUserName, getEmail, getPass, type);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(RegisActivity.this, "Registrasi Sukses !", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else
                                        {
                                            Toast.makeText(RegisActivity.this, "Terjadi Kesalahan, Silahkan Coba Lagi", Toast.LENGTH_SHORT).show();
                                        }

                                }
                            });

                        }
                        else{
                            Toast.makeText(RegisActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            showProgressDialog();
                        }
                    }
                });
    }
}
