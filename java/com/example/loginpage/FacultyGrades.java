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

public class FacultyGrades extends AppCompatActivity {

    private List<String> calendarList;
    private ArrayAdapter<String> calendarListAdapter;
    private FirebaseFirestore firestore;
    private String userId;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        TextView calendarTextView = findViewById(R.id.calendarTextView);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(v -> finish());

        calendarTextView.setText("Student Grades ");


        firestore = FirebaseFirestore.getInstance();
        userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid(); // Get the current authenticated user's UID

        ListView profileListView = findViewById(R.id.facultyListView);
        calendarList = new ArrayList<>();
        calendarListAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, calendarList);
        profileListView.setAdapter(calendarListAdapter);

        retrieveCalendar();
    }

    private void retrieveCalendar(){
        if (userId != null) {
            CollectionReference profileRef = firestore.collection("grades");

            profileRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    calendarList.clear();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String event = document.getString("studentId");
                        String date = document.getString("grade");


                        if (event != null && date != null ) {
                            String userDetails = "\n\n Student Id :" + event +
                                    "\n CGPA: " + date + "\n\n" +
                                    "";
                            calendarList.add(userDetails);
                        }
                    }
                    calendarListAdapter.notifyDataSetChanged();
                } else {
                    Log.d("CalendarActivity", "Error getting details: " + task.getException());
                    Toast.makeText(FacultyGrades.this, "Failed to retrieve event details  ", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.d("CalendarActivity", "Student ID is null");
            Toast.makeText(this, "Failed to retrieve Faculty details", Toast.LENGTH_SHORT).show();
        }
    }

}
