package com.example.breeze.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.breeze.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.example.breeze.R;


public class HomeActivity extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void logout(View view) {
        auth.getInstance().signOut();
        Toast.makeText(HomeActivity.this,"Successfully Logged out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }
}