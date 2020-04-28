package com.example.indecisiveeater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class menu extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private TextView tv_menu;
    private Button bttn_dr;
    private Button bttn_allergies;
    private Button bttn_startSearch;
    private Button bttn_current;
    private Button bttn_signOut;
    private Button bttn_signIn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        mAuth = FirebaseAuth.getInstance();

        tv_menu = findViewById(R.id.tv_menu);
        bttn_dr = findViewById(R.id.bttn_dr);
        bttn_allergies = findViewById(R.id.bttn_allergies);
        bttn_startSearch = findViewById(R.id.bttn_startSearch);
        bttn_current = findViewById(R.id.bttn_current);
        bttn_signOut = findViewById(R.id.bttn_signOut);
        bttn_signIn = findViewById(R.id.bttn_signIn);

        bttn_dr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_account = new Intent(menu.this, DR.class);
                startActivity(intent_account);
            }
        });

        bttn_allergies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_allergies = new Intent(menu.this, Allergies.class);
                startActivity(intent_allergies);
            }
        });
        bttn_startSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_startSearch = new Intent(menu.this, meal_pref.class);
                startActivity(intent_startSearch);
            }
        });

        bttn_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_current = new Intent(menu.this, swipe.class);
                intent_current.putExtra("type", "back");
                startActivity(intent_current);
            }
        });

        bttn_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user == null) {
                    Toast.makeText(getBaseContext(), "You've been signed out!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(getBaseContext(), "Error. Please Try Again Later.", Toast.LENGTH_LONG).show();
                }
            }
        });

        bttn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    Toast.makeText(getBaseContext(), "You are already signed in.", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent_login = new Intent(menu.this, login.class);
                    startActivity(intent_login);
                }
            }
        });
    }
}
