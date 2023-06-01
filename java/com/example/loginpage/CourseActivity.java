package com.example.loginpage;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


import androidx.appcompat.app.AppCompatActivity;

public class CourseActivity extends AppCompatActivity {


    private List<String> courseList;
    private ArrayAdapter<String> courseListAdapter;
    private FirebaseFirestore firestorm;
    private String userId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course);

        TextView courseTextView = findViewById(R.id.courseTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        courseTextView.setText("This is the Course section");

        firestorm = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(); // Get the current authenticated user's UID

        ListView courseListView = findViewById(R.id.courseListView);
        courseList = new ArrayList<>();
        courseListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, courseList);
        courseListView.setAdapter(courseListAdapter);

        retrieveCourse();


    }
    private void retrieveCourse(){
        if (userId != null) {
            CollectionReference courseRef = firestorm.collection("student")
                    .document(userId)
                    .collection("courses");

            courseRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    courseList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String courseName = document.getString("name");
                        String courseId = document.getString("id");
                        String facultyName = document.getString("faculty");

                        if (courseName != null && courseId != null && facultyName != null) {
                            String userDetails = "\n\n\nCourse Name: " + courseName +
                                    "\nFaculty Name: " + courseId +
                                    "\nFaculty: " + facultyName;
                            courseList.add(userDetails);
                        }
                    }
                    courseListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("CourseActivity", "Error getting courses: " + task.getException());
                    Toast.makeText(CourseActivity.this, "Failed to retrieve courses ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("CourseActivity", "Student ID is null");
            Toast.makeText(this, "Failed to retrieve courses", Toast.LENGTH_SHORT).show();
        }
    }












































}
