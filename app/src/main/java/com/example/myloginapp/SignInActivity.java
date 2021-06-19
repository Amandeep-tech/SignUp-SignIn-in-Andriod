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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button signInButton;
    private TextView notYetRegisteredTextView;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmail = findViewById(R.id.emailAddressIdOfSignInPage);
        mPassword = findViewById(R.id.passwordIdOfSignInPage);
        signInButton = findViewById(R.id.signInButtonId);
        notYetRegisteredTextView = findViewById(R.id.notRegisteredYetTextViewId);

        firebaseAuth = FirebaseAuth.getInstance();

        notYetRegisteredTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, MainActivity.class));
            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loggedInUser();
            }
        });

    }

    private void loggedInUser(){
        String email = mEmail.getText().toString();
        String pasword = mPassword.getText().toString();

        // using regex to check email ka format :)
        if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if(!pasword.isEmpty()){
                // if user enter correct email and password
                firebaseAuth.signInWithEmailAndPassword(email, pasword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getApplicationContext(), "Log in Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignInActivity.this, HomeScreenActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
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