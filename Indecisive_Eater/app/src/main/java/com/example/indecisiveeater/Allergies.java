package com.example.indecisiveeater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class Allergies extends AppCompatActivity {

    private CheckBox cb_peanut;
    private CheckBox cb_treenut;
    private CheckBox cb_soy;
    private CheckBox cb_wheat;
    private CheckBox cb_milk;
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
        cb_milk = findViewById(R.id.cb_milk);
        cb_egg = findViewById(R.id.cb_egg);
        cb_fish = findViewById(R.id.cb_fish);
        cb_shellfish = findViewById(R.id.cb_shellfish);

//        txt_title = findViewById(R.id.txt_title);
//        txt_subtitle = findViewById(R.id.txt_subtitle);

        btn_submit = findViewById(R.id.btn_submit);
        bttn_menu = findViewById(R.id.bttn_menu);

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

                if (cb_peanut.isChecked()){
                    //TODO:update database
                }

                if (cb_treenut.isChecked()){
                    //TODO:update database
                }

                if (cb_soy.isChecked()){
                    //TODO:update database
                }

                if (cb_wheat.isChecked()){
                    //TODO:update database
                }

                if (cb_milk.isChecked()){
                    //TODO:update database
                }

                if (cb_egg.isChecked()){
                    //TODO:update database
                }

                if (cb_fish.isChecked()){
                    //TODO:update database
                }

                if (cb_shellfish.isChecked()){
                    //TODO:update database
                }



                //then, go on to the next activity, dietary restrictions
                Intent i = new Intent(getApplicationContext(), DR.class);
                startActivity(i);
            }
        });
    }


    //save and restore instance state so you don't lose checked boxes when you rotate your phone, etc.

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("peanut", cb_peanut.isChecked());
        outState.putBoolean("treenut", cb_treenut.isChecked());
        outState.putBoolean("soy", cb_soy.isChecked());
        outState.putBoolean("wheat", cb_wheat.isChecked());
        outState.putBoolean("milk", cb_milk.isChecked());
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
        cb_milk.setChecked(savedInstanceState.getBoolean("milk"));
        cb_egg.setChecked(savedInstanceState.getBoolean("egg"));
        cb_fish.setChecked(savedInstanceState.getBoolean("fish"));
        cb_shellfish.setChecked(savedInstanceState.getBoolean("shellfish"));


    }

}
