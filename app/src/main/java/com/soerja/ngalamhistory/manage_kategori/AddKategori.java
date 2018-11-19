package com.soerja.ngalamhistory.manage_kategori;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soerja.ngalamhistory.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddKategori extends AppCompatActivity {

    private DatabaseReference referenceKategori, userRef;
    private StorageReference imgReference;
    private EditText namaKategori, deskripsiKategori;
    private Button submitKategori;
    private ImageButton pilihGambarKategori;
    public static final int Gallery_Pick = 1;
    private Uri gambarUri;
    private String saveCurrentDate, namaGambar, downloadUrl, currentUSER, Judulkategori, Deskripsikategori;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_kategori);

        Toolbar toolbar = findViewById(R.id.toolbarTambahKategori);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgReference = FirebaseStorage.getInstance().getReference();
        referenceKategori = FirebaseDatabase.getInstance().getReference().child("Kategori");
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        firebaseAuth = FirebaseAuth.getInstance();
        currentUSER = firebaseAuth.getCurrentUser().getUid();

        namaKategori = findViewById(R.id.judul_kategori);
        deskripsiKategori = findViewById(R.id.deskripsi_kategori);
        submitKategori = findViewById(R.id.tambah_kategori);
        pilihGambarKategori = findViewById(R.id.gambar_kategori);

        pilihGambarKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ambilGambarKategori();
            }
        });

        submitKategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cekInputanKategori();
            }
        });
    }

    private void cekInputanKategori() {
        Judulkategori = namaKategori.getText().toString().trim();
        Deskripsikategori = deskripsiKategori.getText().toString().trim();

        if(gambarUri == null){
            Toast.makeText(AddKategori.this, "Pilih Gambar Untuk Kategori !",  Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Judulkategori)){
            Toast.makeText(AddKategori.this, "Masukkan Judul Kategori !", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Deskripsikategori)){
            Toast.makeText(AddKategori.this, "Masukkan Deskripsi Singkat !", Toast.LENGTH_SHORT).show();
        }
        else if(Deskripsikategori.length() > 50){
            Toast.makeText(AddKategori.this, "Deskripsi Terlalu Panjang !", Toast.LENGTH_SHORT).show();
        }
        else{
            showProgressDialg();
            saveImagekeStorage();
        }
    }

    private void saveImagekeStorage() {
        Calendar tanggal = Calendar.getInstance();
        SimpleDateFormat curentDate = new SimpleDateFormat("dd-MM-yyyy");
        saveCurrentDate = curentDate.format(tanggal.getTime());

        namaGambar = saveCurrentDate;

        final StorageReference pathFile = imgReference.child("Judul_Gambar_Kategori").child(gambarUri.getLastPathSegment()+namaGambar+".jpeg");

        pathFile.putFile(gambarUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if(!task.isSuccessful()){
                    throw  task.getException();
                }

                return  pathFile.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if(task.isSuccessful()){
                    Toast.makeText(AddKategori.this, "Upload Gambar Kategori Berhasil !", Toast.LENGTH_SHORT).show();
                    downloadUrl = task.getResult().toString();
                    simpanKategori();
                }
                else{
                    String error = task.getException().getMessage();
                    Toast.makeText(AddKategori.this, "Terjadi Kesalahan, Silahkan Coba Lagi !"+error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void simpanKategori() {
        userRef.child(currentUSER).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String uid = referenceKategori.push().getKey();
                    AddKategoriAdapter kategori = new AddKategoriAdapter(Judulkategori, downloadUrl, Deskripsikategori);
                    referenceKategori.child(uid).setValue(kategori)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(AddKategori.this, "Upload Kategori " +Judulkategori+ " Berhasil !",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddKategori.this, ManageKategori.class);
                                    startActivity(intent);
                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showProgressDialg() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCancelable(true);
    }

    private void ambilGambarKategori() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null){
            gambarUri = data.getData();
            pilihGambarKategori.setImageURI(gambarUri);
        }
    }
}
