package com.example.loginpage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditStudentProfileActivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private EditText nameEditText, idEditText, phoneEditText;
    private String userEmail, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);

        firestore = FirebaseFirestore.getInstance();

        userEmail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        userId = ""; // Placeholder, will be replaced with ID from Firestore

        nameEditText = findViewById(R.id.nameEditText);
        idEditText = findViewById(R.id.idEditText);
        phoneEditText = findViewById(R.id.phoneEditText);

        Button saveButton = findViewById(R.id.saveButton);

        retrieveUserId();

        saveButton.setOnClickListener(v -> {
            updateStudentProfile();
        });
    }

    private void retrieveUserId() {
        DocumentReference userRef = firestore.collection("map").document(userEmail);
        userRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userId = task.getResult().getString("userId");
                retrieveStudentProfile();
            } else {
                Toast.makeText(EditStudentProfileActivity.this, "Failed to retrieve user ID", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void retrieveStudentProfile() {
        DocumentReference studentRef = firestore.collection("student").document(userId);
        studentRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                String name = task.getResult().getString("name");
                String id = task.getResult().getString("id");
                String phone = task.getResult().getString("phone");

                nameEditText.setText(name);
                idEditText.setText(id);
                phoneEditText.setText(phone);

            } else {
                Toast.makeText(EditStudentProfileActivity.this, "Failed to retrieve student profile", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateStudentProfile() {
        String newName = nameEditText.getText().toString();
        String newId = idEditText.getText().toString();
        String newPhone = phoneEditText.getText().toString();

        DocumentReference studentRef = firestore.collection("student").document(userId);

        studentRef.update(
                "name", newName,
                "id", newId,
                "phone", newPhone
        ).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(EditStudentProfileActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(EditStudentProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
