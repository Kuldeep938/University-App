package com.example.loginpage;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;

import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DoubtUpload extends AppCompatActivity {


    private EditText f_announcementDescriptionEditText;

    private String facultyName ;
    private FirebaseFirestore firestore ;
    private String userId;
    private CollectionReference f_announcementsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doubt_upload);

        TextView facultyNameTextView = findViewById(R.id.facultyNameTextView);

        firestore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        firestore.collection("student")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        facultyName = documentSnapshot.getString("name");
                        if (facultyName != null) {
                            facultyNameTextView.setText(facultyName);
                        }
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DoubtUpload.this, "Failed to retrieve Student Name", Toast.LENGTH_SHORT).show();
                });

        f_announcementDescriptionEditText = findViewById(R.id.announcementDescriptionEditText);
        Button uploadButton = findViewById(R.id.uploadButton);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        f_announcementsRef = FirebaseFirestore.getInstance().collection("doubts");

        uploadButton.setOnClickListener(v -> {
            String announcementDescription = f_announcementDescriptionEditText.getText().toString().trim();

            if (facultyName.isEmpty() || announcementDescription.isEmpty()) {
                Toast.makeText(DoubtUpload.this, "Please enter doubt description", Toast.LENGTH_SHORT).show();
            } else {
                uploadAnnouncement(facultyName, announcementDescription);

            }
        });
    }

    private void uploadAnnouncement(String facultyName, String announcementDescription) {
        Map<String, Object> announcementData = new HashMap<>();
        announcementData.put("studentName", facultyName);
        announcementData.put("doubtDescription", announcementDescription);

        f_announcementsRef.add(announcementData)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(DoubtUpload.this, "Doubt uploaded successfully", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(DoubtUpload.this, "Failed to upload doubt", Toast.LENGTH_SHORT).show();
                });
    }
}
