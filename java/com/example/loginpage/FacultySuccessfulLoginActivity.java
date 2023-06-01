package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class FacultySuccessfulLoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_sucessful_login);

        LinearLayout announcementLayout = findViewById(R.id.announcementLayout);
        LinearLayout attendanceLayout = findViewById(R.id.attendanceLayout);
        LinearLayout calendarLayout = findViewById(R.id.calendarLayout);
        LinearLayout facultyLayout = findViewById(R.id.facultyLayout);
        LinearLayout performanceLayout = findViewById(R.id.performanceLayout);
        LinearLayout profileLayout = findViewById(R.id.profileLayout);
        LinearLayout doubtLayout = findViewById(R.id.doubtLayout);
        Button logoutButton = findViewById(R.id.logOutbutton);
        // Set onClickListener to each TextView
        announcementLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FacultySuccessfulLoginActivity.this, FacultyAnnouncementActivity.class);
            startActivity(intent);
        });

        attendanceLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FacultySuccessfulLoginActivity.this, FacultyAttendance.class);
            startActivity(intent);
        });

        calendarLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FacultySuccessfulLoginActivity.this, CalendarActivity.class);
            startActivity(intent);
        });

        facultyLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FacultySuccessfulLoginActivity.this, FacultyActivity.class);
            startActivity(intent);
        });

        performanceLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FacultySuccessfulLoginActivity.this, FacultyGrades.class);
            startActivity(intent);
        });

        profileLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FacultySuccessfulLoginActivity.this, FacultyProfile.class);
            startActivity(intent);
        });

        doubtLayout.setOnClickListener(v -> {
            Intent intent = new Intent(FacultySuccessfulLoginActivity.this, FacultyDoubtActivity.class);
            startActivity(intent);
        });
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(FacultySuccessfulLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });



    }
}