package com.soerja.ngalamhistory;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout DL;
    private ActionBarDrawerToggle swipe;
    private NavigationView NV;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Deklarasi Button
        Button butHis = (Button)findViewById(R.id.sejarah);

        DL = (DrawerLayout)findViewById(R.id.activity_main);
        swipe = new ActionBarDrawerToggle(MainActivity.this, DL,R.string.Open, R.string.Close);

        DL.addDrawerListener(swipe);
        swipe.syncState();

        NV = (NavigationView)findViewById(R.id.navbar);

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
        //Firebase
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null){

                }
            }
        };

        // Ke Artikel
        butHis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inthis = new Intent(MainActivity.this, SejarahActivty.class);
                startActivity(inthis);
            }
        });

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
