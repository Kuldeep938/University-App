package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FacultyAnnouncementActivity extends AppCompatActivity {

    private TextView announcementTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_announcement);

        announcementTextView = findViewById(R.id.announcementTextView);
        Button backButton = findViewById(R.id.backButton);
        Button uploadButton = findViewById(R.id.uploadButton);

        backButton.setOnClickListener(v -> finish());

        uploadButton.setOnClickListener(v -> navigateToUploadAnnouncement());

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
                    Toast.makeText(FacultyAnnouncementActivity.this, "Failed to load announcements: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateToUploadAnnouncement() {
        Intent intent = new Intent(FacultyAnnouncementActivity.this, UploadAnnouncementActivity.class);
        startActivity(intent);
    }

}
