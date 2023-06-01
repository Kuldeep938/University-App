package com.example.loginpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class FacultyDoubtActivity extends AppCompatActivity {


    private TextView announcementTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_doubt);

        announcementTextView = findViewById(R.id.doubtTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("doubts")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    StringBuilder announcementsBuilder = new StringBuilder();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        String facultyName = snapshot.getString("studentName");
                        String announcementDescription = snapshot.getString("doubtDescription");
                        if (facultyName != null && announcementDescription != null) {
                            String announcementText = "Student Name: " + facultyName + "\nDescription: " + announcementDescription + "\n\n";
                            announcementsBuilder.append(announcementText);
                        }
                    }
                    String announcements = announcementsBuilder.toString();
                    announcementTextView.setText(announcements);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(FacultyDoubtActivity.this, "Failed to load doubts: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}