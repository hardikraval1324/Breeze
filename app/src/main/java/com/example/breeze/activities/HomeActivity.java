package com.example.breeze.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.breeze.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.example.breeze.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toolbar;

import org.jetbrains.annotations.NotNull;


public class HomeActivity extends AppCompatActivity {
    TextView authname;
    public DrawerLayout drawerLayout; // for navigation menu
    public NavigationView navigationView;
    public ActionBarDrawerToggle actionBarDrawerToggle; // for navigation menu
    private FirebaseAuth auth;
    // Getting User info via firebase
    private FirebaseUser users;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        navigationView = findViewById(R.id.navigationview);
        View header = navigationView.getHeaderView(0);
        authname = header.findViewById(R.id.U_teranam);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        // getting user info here
        auth = FirebaseAuth.getInstance();
        users = auth.getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = users.getUid();


//        reference.child(users.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(HomeActivity.this, " Someting went wrong !", Toast.LENGTH_LONG);
//                }
//                else {
//                    authname.setText(String.valueOf(users.getDisplayName()));
//                    Log.d(" ",String.valueOf(authname));
//                    Log.d("firebase !!!!!!!!!!!!!!!!!!!!!!!!!!!!!", String.valueOf(task.getResult().getValue()));
//                }
//            }
//        });

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userprofile = snapshot.getValue(User.class);
                if(userprofile != null){
                    String name = userprofile.name; // from the user class
                    String email = userprofile.email;
                    authname.setText("Welcome " + name);
                    Log.d(" ",String.valueOf(authname));
                    Log.d("Cehckin firebase !!!!!!!!!!!!!!!!!!!!!!!!!!!!!", name +" "+email);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(HomeActivity.this, " Someting went wrong !", Toast.LENGTH_LONG);

            }
        });

    }

    // override the onOptionsItemSelected()
    // function to implement
    // the item click listener callback
    // to open and close the navigation
    // drawer when the icon is clicked
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logout(MenuItem item) {
        auth.getInstance().signOut();
        Toast.makeText(HomeActivity.this,"Successfully Logged out",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(HomeActivity.this, MainActivity.class));
    }

}