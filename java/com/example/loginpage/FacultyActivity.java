package com.example.loginpage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.text.Html;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class FacultyActivity extends AppCompatActivity {

    private LinearLayout facultyLayout;
    private Button backButton;

    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        firestore = FirebaseFirestore.getInstance();

        facultyLayout = findViewById(R.id.facultyLayout);
        backButton = findViewById(R.id.backButton);

        retrieveFacultyDetails();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void retrieveFacultyDetails() {
        firestore.collection("faculty").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        LinearLayout facultyLayout = findViewById(R.id.facultyLayout);
                        facultyLayout.removeAllViews(); // Clear existing views

                        for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String facultyName = documentSnapshot.getString("name");
                            String facultyEmail = documentSnapshot.getString("mail");
                            String facultyDescription = documentSnapshot.getString("desc");
                            String facultyImageUrl = documentSnapshot.getString("photo");
                            String emailLink = "<a href=\"mailto:" + facultyEmail + "\">" + facultyEmail + "</a>";
                            String  link = documentSnapshot.getString("link");
                            createFacultyItem(facultyName, emailLink, facultyDescription, facultyImageUrl, facultyLayout ,link);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("FacultyActivity", "Error retrieving faculty details: " + e.getMessage());
                        Toast.makeText(FacultyActivity.this, "Failed to retrieve faculty details", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void createFacultyItem(String facultyName, String emailLink, String facultyDescription, String imageUrl, LinearLayout facultyLayout ,String link) {
        View facultyItemView = getLayoutInflater().inflate(R.layout.faculty_item_layout, null);
        TextView nameTextView = facultyItemView.findViewById(R.id.nameTextView);
        TextView emailTextView = facultyItemView.findViewById(R.id.emailTextView);
        TextView descriptionTextView = facultyItemView.findViewById(R.id.descriptionTextView);
        ImageView facultyImageView = facultyItemView.findViewById(R.id.facultyImageView);

        nameTextView.setText(facultyName);
        emailTextView.setText(Html.fromHtml(emailLink));
        Linkify.addLinks(emailTextView, Linkify.EMAIL_ADDRESSES);

        descriptionTextView.setText(facultyDescription);
        Glide.with(this).load(imageUrl).into(facultyImageView);

        facultyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (link != null && !link.isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(intent);
                }
            }
        });

        facultyLayout.addView(facultyItemView);
    }

}