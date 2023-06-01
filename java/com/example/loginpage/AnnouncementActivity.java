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


public class AnnouncementActivity extends AppCompatActivity {


    private TextView announcementTextView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        announcementTextView = findViewById(R.id.announcementTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());



        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("announcements")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    StringBuilder announcementsBuilder = new StringBuilder();
                    for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                        String facultyName = snapshot.getString("facultyName");
                        String announcementDescription = snapshot.getString("announcementDescription");
                        if (facultyName != null && announcementDescription != null) {
                            String announcementText = "Faculty Name: " + facultyName + "\nDescription: " + announcementDescription + "\n\n";
                            announcementsBuilder.append(announcementText);
                        }
                    }
                    String announcements = announcementsBuilder.toString();
                    announcementTextView.setText(announcements);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(AnnouncementActivity.this, "Failed to load announcements: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}