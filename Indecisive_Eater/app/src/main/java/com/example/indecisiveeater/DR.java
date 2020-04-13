package com.example.indecisiveeater;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DR extends AppCompatActivity {

    private CheckBox cb_vegetarian;
    private CheckBox cb_vegan;
    private CheckBox cb_gluten;

//    private TextView txt_title;
//    private TextView txt_subtitle;

    private Button btn_submit;
    private Button bttn_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dr);

        cb_vegetarian = findViewById(R.id.cb_vegetarian);
        cb_vegan = findViewById(R.id.cb_vegan);
        cb_gluten = findViewById(R.id.cb_gluten);

//        txt_title = findViewById(R.id.txt_title);
//        txt_subtitle = findViewById(R.id.txt_subtitle);

        btn_submit = findViewById(R.id.btn_submit);
        bttn_menu = findViewById(R.id.bttn_menu);

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

                if (cb_vegetarian.isChecked()){
                    //TODO:update database
                }

                if (cb_vegan.isChecked()){
                    //TODO:update database
                }

                if (cb_gluten.isChecked()){
                    //TODO:update database
                }


                //TODO:then, go on to the next activity

            }
        });

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
