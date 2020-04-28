package com.example.indecisiveeater;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DR extends AppCompatActivity {

    private CheckBox cb_vegetarian;
    private CheckBox cb_vegan;
    private CheckBox cb_gluten;

//    private TextView txt_title;
//    private TextView txt_subtitle;

    private Button btn_submit;
    private Button bttn_menu;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr);

        mAuth = FirebaseAuth.getInstance();

        cb_vegetarian = findViewById(R.id.cb_vegetarian);
        cb_vegan = findViewById(R.id.cb_vegan);
        cb_gluten = findViewById(R.id.cb_gluten);

//        txt_title = findViewById(R.id.txt_title);
//        txt_subtitle = findViewById(R.id.txt_subtitle);

        btn_submit = findViewById(R.id.btn_submit);
        bttn_menu = findViewById(R.id.bttn_menu);

        loadDatabase();

        bttn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(DR.this, menu.class);
                startActivity(intent_menu);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when submit is clicked, check which checkboxes are selected and update the database accordingly

                boolean is_vegetarian = cb_vegetarian.isChecked();
                updateDatabase("vegetarian", is_vegetarian);

                boolean is_vegan = cb_vegan.isChecked();
                updateDatabase("vegan", is_vegan);

                boolean is_gluten = cb_gluten.isChecked();
                updateDatabase("gluten-free", is_gluten);

                Toast.makeText(getBaseContext(),"Your settings have been updated.",Toast.LENGTH_LONG).show();
            }
        });

    }

    private void updateDatabase(String item, boolean value){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference root = database.getReference();

        root.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child(item).setValue(value);

    }

    private void loadDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference root = database.getReference();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //gets the dataSnapShot
        root.child("users").child(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child("vegetarian").exists() == true) {
                    //vegetarian: reads database & sets value
                    boolean vegetarian = dataSnapshot.child("vegetarian").getValue(boolean.class);
                    if (vegetarian == true) {
                        cb_vegetarian.setChecked(true);
                    } else {
                        cb_vegetarian.setChecked(false);
                    }
                }
                if(dataSnapshot.child("vegan").exists() == true) {
                    //vegan: reads database & sets value
                    boolean vegan = dataSnapshot.child("vegan").getValue(boolean.class);
                    if (vegan == true) {
                        cb_vegan.setChecked(true);
                    } else {
                        cb_vegan.setChecked(false);
                    }
                }
                if(dataSnapshot.child("gluten-free").exists() == true){
                    //gluten: reads database & sets value
                        boolean gluten = dataSnapshot.child("gluten-free").getValue(boolean.class);
                        if (gluten == true) {
                            cb_gluten.setChecked(true);
                        } else {
                            cb_gluten.setChecked(false);
                        }
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    //save and restore instance state so you don't lose checked boxes when you rotate your phone, etc.

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("vegetarian", cb_vegetarian.isChecked());
        outState.putBoolean("vegan", cb_vegan.isChecked());
        outState.putBoolean("gluten", cb_gluten.isChecked());


        super.onSaveInstanceState(outState);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        cb_vegetarian.setChecked(savedInstanceState.getBoolean("vegetarian"));
        cb_vegan.setChecked(savedInstanceState.getBoolean("vegan"));
        cb_gluten.setChecked(savedInstanceState.getBoolean("gluten"));

    }
}
