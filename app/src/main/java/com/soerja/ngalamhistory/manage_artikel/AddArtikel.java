package com.soerja.ngalamhistory.manage_artikel;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AddArtikel extends AppCompatActivity {

    private ImageButton TitleIMGArt;
    private Button uploadArtikel;
    private EditText titleArt, isiArt;
    private Spinner kategori;
    private String Judul, Isi, downloadUrl, current_user_uid, saveCurrentDate, saveCurrentTime, postRandomName;
    private Uri imageUri;
    private ArrayList<String> dataKategory = new ArrayList<String>();
    private static final int Gallery_Pick = 1;
    private StorageReference UploadIMGReference;
    private DatabaseReference userRef, artikelRef;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_add_artikel);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarTambahArtikel);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        current_user_uid = firebaseAuth.getCurrentUser().getUid();
        UploadIMGReference = FirebaseStorage.getInstance().getReference();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        artikelRef = FirebaseDatabase.getInstance().getReference().child("Artikel");

        kategori = (Spinner)findViewById(R.id.SpinnerkategoriArtikel);
        uploadArtikel = (Button)findViewById(R.id.TambahArtikel);
        TitleIMGArt = (ImageButton)findViewById(R.id.ImageTitleArtikel);
        titleArt = (EditText)findViewById(R.id.TitleArtikel);
        isiArt = (EditText)findViewById(R.id.ArticleContent);

        TitleIMGArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ambilGambar();
            }
        });

        uploadArtikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesUpload();
            }
        });

        //Get Data ke Spinner Kategori
        DatabaseReference referenceKategori = FirebaseDatabase.getInstance().getReference("Kategori");
        referenceKategori.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()){
                    String kategori = dataSnapshot2.child("Jenis_Kategori").getValue().toString();
                    dataKategory.add(kategori);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddArtikel.this, android.R.layout.simple_spinner_dropdown_item, dataKategory);
                kategori.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddArtikel.this, "Terjadi Kesalahan : " + databaseError, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void prosesUpload() {

        Judul = titleArt.getText().toString().trim();
        Isi = isiArt.getText().toString().trim();

        if(imageUri == null){
            Toast.makeText(AddArtikel.this, "Pilih Gambar Untuk Judul Artikel !", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Judul)){
            Toast.makeText(AddArtikel.this, "Isi Judul Artikel !", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Isi)){
            Toast.makeText(AddArtikel.this, "Isi Konten Artikel !", Toast.LENGTH_SHORT).show();
        }else{
            showProgressDialog();
            SaveImageToStorage();
        }

    }

    private void SaveImageToStorage() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat cuurentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = cuurentDate.format(calendar.getTime());

        Calendar time = Calendar.getInstance();
        SimpleDateFormat cuurentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = cuurentDate.format(calendar.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference pathFile = UploadIMGReference.child("Judul_Gambar_Artikel").child(imageUri.getLastPathSegment()+postRandomName+".jpeg");

        pathFile.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful()){

                    downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                    Toast.makeText(AddArtikel.this, "Gambar Berhasil Di-Upload !", Toast.LENGTH_SHORT).show();

                    simpanArtikel();
                }
                else{
                    String error = task.getException().getMessage();
                    Toast.makeText(AddArtikel.this, "Terjadi Error : "+ error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void simpanArtikel() {
        userRef.child(current_user_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){
                    String userName = dataSnapshot.child("userName").getValue().toString();
                    String kategoriArtikel = kategori.getSelectedItem().toString();

                    HashMap postMap = new HashMap();
                        postMap.put("uid", current_user_uid);
                        postMap.put("judul_artikel", Judul);
                        postMap.put("judul_gambar", downloadUrl);
                        postMap.put("kontent", isiArt);
                        postMap.put("Uploader", userName);
                        postMap.put("Kategori", kategoriArtikel);

                        artikelRef.child(postRandomName+current_user_uid).updateChildren(postMap).addOnCompleteListener(new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(AddArtikel.this, "Upload Artikel Berhasil !", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(AddArtikel.this, ManageArtikelActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(AddArtikel.this, "Terjadi Kesalahan + "+error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.show();
        dialog.setCancelable(true);
    }

    private void ambilGambar() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, Gallery_Pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            TitleIMGArt.setImageURI(imageUri);
        }
    }
}

