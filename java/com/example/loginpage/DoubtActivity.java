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

public class DoubtActivity extends AppCompatActivity {

    private TextView announcementTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt);

        announcementTextView = findViewById(R.id.doubtTextView);
        Button backButton = findViewById(R.id.backButton);
        Button uploadButton = findViewById(R.id.uploadButton);

        backButton.setOnClickListener(v -> finish());

        uploadButton.setOnClickListener(v -> navigateToUploadAnnouncement());

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
                    Toast.makeText(DoubtActivity.this, "Failed to doubts: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void navigateToUploadAnnouncement() {
        Intent intent = new Intent(DoubtActivity.this, DoubtUpload.class);
        startActivity(intent);
    }

}
