package com.example.indecisiveeater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Allergies extends AppCompatActivity {

    private CheckBox cb_peanut;
    private CheckBox cb_treenut;
    private CheckBox cb_soy;
    private CheckBox cb_wheat;
    private CheckBox cb_lactose;
    private CheckBox cb_egg;
    private CheckBox cb_fish;
    private CheckBox cb_shellfish;

    private Button bttn_menu;

//    private TextView txt_title;
//    private TextView txt_subtitle;

    private Button btn_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allergy);

        cb_peanut = findViewById(R.id.cb_peanut);
        cb_treenut = findViewById(R.id.cb_treenut);
        cb_soy = findViewById(R.id.cb_soy);
        cb_wheat = findViewById(R.id.cb_wheat);
        cb_lactose = findViewById(R.id.cb_lactose);
        cb_egg = findViewById(R.id.cb_egg);
        cb_fish = findViewById(R.id.cb_fish);
        cb_shellfish = findViewById(R.id.cb_shellfish);

//        txt_title = findViewById(R.id.txt_title);
//        txt_subtitle = findViewById(R.id.txt_subtitle);

        btn_submit = findViewById(R.id.btn_submit);
        bttn_menu = findViewById(R.id.bttn_menu);

        loadDatabase();

        bttn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(Allergies.this, menu.class);
                startActivity(intent_menu);
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //when submit is clicked, check which checkboxes are selected and update the database accordingly
                boolean peanut = cb_peanut.isChecked();
                updateDatabase("peanut_allergy", peanut);

                boolean treenut = cb_treenut.isChecked();
                updateDatabase("treenut_allergy", treenut);

                boolean soy = cb_soy.isChecked();
                updateDatabase("soy_allergy", soy);

                boolean wheat = cb_wheat.isChecked();
                updateDatabase("wheat_allergy", wheat);

                boolean fish = cb_fish.isChecked();
                updateDatabase("fish_allergy", fish);

                boolean shellfish = cb_shellfish.isChecked();
                updateDatabase("shellfish_allergy", shellfish);

                boolean lactose = cb_lactose.isChecked();
                updateDatabase("lactose_allergy", lactose);

                boolean eggs = cb_egg.isChecked();
                updateDatabase("egg_allergy", eggs);

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
                //peanut: reads database & sets value
                if (dataSnapshot.child("peanut_allergy").exists() == true) {
                    boolean peanut = dataSnapshot.child("peanut_allergy").getValue(boolean.class);
                    if (peanut == true) {
                        cb_peanut.setChecked(true);
                    } else {
                        cb_peanut.setChecked(false);
                    }
                }
                //treenut: reads database & sets value
                if(dataSnapshot.child("treenut_allergy").exists() == true) {
                    boolean treenut = dataSnapshot.child("treenut_allergy").getValue(boolean.class);
                    if (treenut == true) {
                        cb_treenut.setChecked(true);
                    } else {
                        cb_treenut.setChecked(false);
                    }
                }
                //lactose: reads database & sets value
                if(dataSnapshot.child("lactose_allergy").exists() == true) {
                    boolean lactose = dataSnapshot.child("lactose_allergy").getValue(boolean.class);
                    if (lactose == true) {
                        cb_lactose.setChecked(true);
                    } else {
                        cb_lactose.setChecked(false);
                    }
                }
                //eggs: reads database & sets value
                if(dataSnapshot.child("egg_allergy").exists() == true) {
                    boolean egg = dataSnapshot.child("egg_allergy").getValue(boolean.class);
                    if (egg == true) {
                        cb_egg.setChecked(true);
                    } else {
                        cb_egg.setChecked(false);
                    }
                }
                //fish: reads database & sets value
                if(dataSnapshot.child("fish_allergy").exists() == true) {
                    boolean fish = dataSnapshot.child("fish_allergy").getValue(boolean.class);
                    if (fish == true) {
                        cb_fish.setChecked(true);
                    } else {
                        cb_fish.setChecked(false);
                    }
                }
                //shellfish: reads database & sets value
                if(dataSnapshot.child("shellfish_allergy").exists() == true) {
                    boolean shellfish = dataSnapshot.child("shellfish_allergy").getValue(boolean.class);
                    if (shellfish == true) {
                        cb_shellfish.setChecked(true);
                    } else {
                        cb_shellfish.setChecked(false);
                    }
                }
                //wheat: reads database & sets value
                if(dataSnapshot.child("wheat_allergy").exists() == true) {
                    boolean wheat = dataSnapshot.child("wheat_allergy").getValue(boolean.class);
                    if (wheat == true) {
                        cb_wheat.setChecked(true);
                    } else {
                        cb_wheat.setChecked(false);
                    }
                }
                //soy: reads database & sets value
                if(dataSnapshot.child("soy_allergy").exists() == true) {
                    boolean soy = dataSnapshot.child("soy_allergy").getValue(boolean.class);
                    if (soy == true) {
                        cb_soy.setChecked(true);
                    } else {
                        cb_soy.setChecked(false);
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
        outState.putBoolean("peanut", cb_peanut.isChecked());
        outState.putBoolean("treenut", cb_treenut.isChecked());
        outState.putBoolean("soy", cb_soy.isChecked());
        outState.putBoolean("wheat", cb_wheat.isChecked());
        outState.putBoolean("milk", cb_lactose.isChecked());
        outState.putBoolean("egg", cb_egg.isChecked());
        outState.putBoolean("fish", cb_fish.isChecked());
        outState.putBoolean("shellfish", cb_shellfish.isChecked());


        super.onSaveInstanceState(outState);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        cb_peanut.setChecked(savedInstanceState.getBoolean("peanut"));
        cb_treenut.setChecked(savedInstanceState.getBoolean("treenut"));
        cb_soy.setChecked(savedInstanceState.getBoolean("soy"));
        cb_wheat.setChecked(savedInstanceState.getBoolean("wheat"));
        cb_lactose.setChecked(savedInstanceState.getBoolean("milk"));
        cb_egg.setChecked(savedInstanceState.getBoolean("egg"));
        cb_fish.setChecked(savedInstanceState.getBoolean("fish"));
        cb_shellfish.setChecked(savedInstanceState.getBoolean("shellfish"));


    }

}
