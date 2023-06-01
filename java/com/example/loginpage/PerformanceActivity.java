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

public class PerformanceActivity extends AppCompatActivity {

    private List<String> gradeList;
    private ArrayAdapter<String> gradeListAdapter;
    private FirebaseFirestore firestorm;
    private String userId;





    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance);

        TextView performanceTextView = findViewById(R.id.performanceTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        performanceTextView.setText("Grades");

        firestorm = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(); // Get the current authenticated user's UID


        ListView gradeListView = findViewById(R.id.gradeListView);
        gradeList = new ArrayList<>();
        gradeListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gradeList);
        gradeListView.setAdapter(gradeListAdapter);

        retrieveGrade();

    }
    private void retrieveGrade(){
        if (userId != null) {
            CollectionReference courseRef = firestorm.collection("student")
                    .document(userId)
                    .collection("grades");

            courseRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    gradeList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String semId = document.getString("sem");
                        String gradeScored = document.getString("grade");

                        if (semId != null && gradeScored != null ) {
                            String userDetails = "\n\n\nSemester: " + semId +
                                    "\nGPA Scored: " + gradeScored ;
                            gradeList.add(userDetails);
                        }
                    }
                    gradeListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("PerformanceActivity", "Error getting grades: " + task.getException());
                    Toast.makeText(PerformanceActivity.this, "Failed to retrieve grades", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("PerformanceActivity", "Student ID is null");
            Toast.makeText(this, "Failed to retrieve grades", Toast.LENGTH_SHORT).show();
        }
    }

}
