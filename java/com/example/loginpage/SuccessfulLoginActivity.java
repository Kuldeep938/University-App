package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Button ;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessfulLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_login);

        LinearLayout announcementLayout = findViewById(R.id.announcementLayout);
        LinearLayout attendanceLayout = findViewById(R.id.attendanceLayout);
        LinearLayout calendarLayout = findViewById(R.id.calendarLayout);
        LinearLayout courseLayout = findViewById(R.id.courseLayout);
        LinearLayout facultyLayout = findViewById(R.id.facultyLayout);
        LinearLayout performanceLayout = findViewById(R.id.performanceLayout);
        LinearLayout profileLayout = findViewById(R.id.profileLayout);
        LinearLayout doubtLayout = findViewById(R.id.doubtLayout);

        Button logoutButton = findViewById(R.id.logOutbutton);


        announcementLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessfulLoginActivity.this, AnnouncementActivity.class);
            startActivity(intent);
        });

        attendanceLayout.setOnClickListener(v -> {
                    Intent intent = new Intent(SuccessfulLoginActivity.this, AttendanceActivity.class);
                    startActivity(intent);
                });

        calendarLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessfulLoginActivity.this, CalendarActivity.class);
            startActivity(intent);
        });

        courseLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessfulLoginActivity.this, CourseActivity.class);
            startActivity(intent);
        });

        facultyLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessfulLoginActivity.this, FacultyActivity.class);
            startActivity(intent);
        });

        performanceLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessfulLoginActivity.this, PerformanceActivity.class);
            startActivity(intent);
        });

        profileLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessfulLoginActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        doubtLayout.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessfulLoginActivity.this, DoubtActivity.class);
            startActivity(intent);
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessfulLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}