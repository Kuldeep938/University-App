package com.example.loginpage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/*
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

 */

public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private FirebaseAuth firebaseAuth;
    private CheckBox facultySwitch ;
    private CheckBox studentSwitch ;
    private CheckBox adminSwitch ;

    private String attribute ;
    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        /*
        if(firebaseAuth.getCurrentUser() != null){
            navigateToSuccessfulPage(); // user is already logged in
        }
        */

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button loginButton = findViewById(R.id.loginButton);
        TextView forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView);
        facultySwitch = findViewById(R.id.facultyCheckBox);
        studentSwitch = findViewById(R.id.studentCheckBox);
        adminSwitch = findViewById(R.id.adminCheckBox);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(username, password);
                }
            }
        });
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Forgot Password clicked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, ForgetPassActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                // User is logged in successfully
                                Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                navigateToSuccessfulPage();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void navigateToSuccessfulPage() {

/*
        FirebaseFirestore firestorm = FirebaseFirestore.getInstance();
        String userId = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        DocumentReference profileRef = firestorm.collection("role")
                .document(userId) ;



        profileRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                attribute =documentSnapshot.getString("role");
            } else {
                attribute= "student";
            }
        }).addOnFailureListener(e -> {
            attribute= "student";
        });


 */

        boolean isFaculty = facultySwitch.isChecked();
        boolean isStudent = studentSwitch.isChecked();
        boolean isAdmin = adminSwitch.isChecked();


        if (isStudent) {
            Intent intent;
            intent = new Intent(MainActivity.this, SuccessfulLoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if(isFaculty) {
            Intent intent;
            intent = new Intent(MainActivity.this, FacultySuccessfulLoginActivity.class);
            startActivity(intent);
            finish();

        }
        else if(isAdmin){
            Intent intent;
            intent = new Intent(MainActivity.this, AdminHomePageActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(MainActivity.this, "Tick appropriate box", Toast.LENGTH_SHORT).show();
        }
    }
}
