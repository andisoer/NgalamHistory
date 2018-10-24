package com.soerja.ngalamhistory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.soerja.ngalamhistory.manage_user.ManageUserActivity;

public class DashboardActivity extends AppCompatActivity {

    Button btnManageArt, btnManageUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activitydashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbardash);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btnManageArt = (Button)findViewById(R.id.manageContent);
        btnManageUser = (Button)findViewById(R.id.manageUser);

        btnManageArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ManageContent.class);
                startActivity(intent);
            }
        });

        btnManageUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this, ManageUserActivity.class);
                startActivity(intent);
            }
        });

    }
}