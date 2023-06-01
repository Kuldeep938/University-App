package com.example.loginpage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {


    private List<String> profileList;
    private ArrayAdapter<String> profileListAdapter;
    private FirebaseFirestore firestore;
    private String userId;

    private String userPic;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView profileTextView = findViewById(R.id.profileTextView);

        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        profileTextView.setText("Student Profile");
        firestore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(); // Get the current authenticated user's UID

        ListView profileListView = findViewById(R.id.profileListView);
        profileList = new ArrayList<>();
        profileListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, profileList);

        retrieveProfile();
        profileListView.setAdapter(profileListAdapter);
/*
        ImageView profileImageView = findViewById(R.id.imageView);
        Glide.with(this).load(userPic).into(profileImageView);

*/

        TextView forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this, "Forgot Password clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, ForgetPassActivity.class);
                startActivity(intent);
            }
        });

    }
    private void retrieveProfile(){
        if (userId != null) {
            CollectionReference profileRef = firestore.collection("student")
                    .document(userId)
                    .collection("details");

            profileRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    profileList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String userName = document.getString("name");
                        String userId = document.getString("id");
                        String userPh = document.getString("ph");
                        userPic = document.getString("photo");

                        if (userName != null && userId != null && userPh != null) {
                            String userDetails = "\n\n\nStudent Name: " + userName +
                                    "\n\n\nStudent ID: " + userId +
                                    "\n\n\nPhone Number: " + userPh;
                            profileList.add(userDetails);
                        }
                    }
                    profileListAdapter.notifyDataSetChanged();

                } else {
                    Log.d("ProfileActivity", "Error getting details: " + task.getException());
                    Toast.makeText(ProfileActivity.this, "Failed to retrieve details ck ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("ProfileActivity", "Student ID is null");
            Toast.makeText(this, "Failed to retrieve details", Toast.LENGTH_SHORT).show();
        }
    }




}
