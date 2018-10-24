package com.soerja.ngalamhistory.article.sejarah.photodesc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.soerja.ngalamhistory.PhotoDLActivity;
import com.soerja.ngalamhistory.R;

public class PhotoDescActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photosdesc);

        Toolbar toolPhotoDesc = (Toolbar)findViewById(R.id.toolbarpictdesc);
        setSupportActionBar(toolPhotoDesc);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        
        Button btMore = (Button)findViewById(R.id.more);

        btMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhotoDescActivity.this, PhotoDLActivity.class);
                startActivity(intent);
            }
        });

    }
}
