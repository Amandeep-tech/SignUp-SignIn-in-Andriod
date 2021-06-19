package com.example.myloginapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button registerButton;
    private TextView alreadyRegisteredSignIn;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = findViewById(R.id.emailAddressId);
        mPassword = findViewById(R.id.passwordId);
        registerButton = findViewById(R.id.registrationButtonId);
        alreadyRegisteredSignIn = findViewById(R.id.alreadyRegisteredTextViewId);

        firebaseAuth = FirebaseAuth.getInstance();

        alreadyRegisteredSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });
    }
    private void createUser(){
        String email = mEmail.getText().toString();
        String pasword = mPassword.getText().toString();

        // using regex to check email ka format :)
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pasword.isEmpty()){
                // if user enter correct email and password
                firebaseAuth.createUserWithEmailAndPassword(email, pasword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull  Task<AuthResult> task) {
                                Toast.makeText(getApplicationContext(), "Registered Successfully ! !", Toast.LENGTH_SHORT).show();
                                // since the user is registered successfully, send him to sign in activity
                                startActivity(new Intent(MainActivity.this, SignInActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(MainActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                    }
                });
            }else{
                mPassword.setError("Empty password not allowed");
            }
        }
        else if(email.isEmpty()){
            mEmail.setError("Email address is required");
        }
        else{
            mEmail.setError("Enter correct email");
        }
    }
}