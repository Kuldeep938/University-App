package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AdminHomePageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home_page);

        Button btnEditStudentProfile = findViewById(R.id.btnEditStudentProfile);
        Button btnLogout = findViewById(R.id.btnLogout);

        btnEditStudentProfile.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomePageActivity.this, EditStudentProfileActivity.class);
            startActivity(intent);
        });

        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(AdminHomePageActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
