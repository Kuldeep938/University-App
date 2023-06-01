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

public class FacultyAttendance extends AppCompatActivity {

    private List<String> attendanceList;
    private ArrayAdapter<String> attendanceListAdapter;
    private FirebaseFirestore firestore;
    private String userId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        TextView attendanceTextView = findViewById(R.id.attendanceTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        attendanceTextView.setText("Attendance section");

        firestore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(); // Get the current authenticated user's UID

        ListView profileListView = findViewById(R.id.attendanceListView);
        attendanceList = new ArrayList<>();
        attendanceListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, attendanceList);
        profileListView.setAdapter(attendanceListAdapter);
        retrieveAttendance();
    }

    private void retrieveAttendance(){
        if (userId != null) {
            CollectionReference profileRef = firestore.collection("facultydb")
                    .document(userId)
                    .collection("attendance");

            profileRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    attendanceList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String courseName = document.getString("month");
                        String percentage = document.getString("percentage");

                        if (courseName != null  && percentage != null) {
                            String userDetails = "\n\nMonth: " + courseName +
                                    "\nPercentage: " + percentage + "\n\n";
                            attendanceList.add(userDetails);
                        }
                    }
                    attendanceListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("AttendanceActivity", "Error getting details: " + task.getException());
                    Toast.makeText(FacultyAttendance.this, "Failed to retrieve details  ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("AttendanceActivity", "Student ID is null");
            Toast.makeText(this, "Failed to retrieve details", Toast.LENGTH_SHORT).show();
        }
    }

}
