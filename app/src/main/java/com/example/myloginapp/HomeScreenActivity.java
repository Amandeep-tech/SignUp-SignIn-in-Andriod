package com.example.myloginapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeScreenActivity extends AppCompatActivity {

    Button signOutButton;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        signOutButton = findViewById(R.id.logOutButtonId);
        firebaseAuth = FirebaseAuth.getInstance();

        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreenActivity.this, MainActivity.class));
                        finish();
            }
        });
    }
}