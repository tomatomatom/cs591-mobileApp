package com.example.indecisiveeater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class meal_pref extends AppCompatActivity {


    private ToggleButton tb_p1;
    private ToggleButton tb_p2;
    private ToggleButton tb_p3;
    private ToggleButton tb_p4;

    private MultiAutoCompleteTextView ac_categories;

    private Button btn_submit;
    private Button btn_menu;

    private EditText ed_location;

    private SeekBar sb_distance;
    private TextView tv_distance;

    private Switch sw_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meal_pref);

        //make a new autocomplete adapter that takes in the YELP API categories
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, Cats.DB_CATS);

        tb_p1 = findViewById(R.id.tb_p1);
        tb_p2 = findViewById(R.id.tb_p2);
        tb_p3 = findViewById(R.id.tb_p3);
        tb_p4 = findViewById(R.id.tb_p4);

        ac_categories = findViewById(R.id.ac_categories);

        btn_submit = findViewById(R.id.btn_submit);
        btn_menu = findViewById(R.id.btn_menu);

        ed_location = findViewById(R.id.ed_location);

        sb_distance = findViewById(R.id.sb_distance);
        tv_distance = findViewById(R.id.tv_distance);

        sw_open = findViewById(R.id.sw_open);

        //connect autocomplete box to the adapter that we made
        ac_categories.setAdapter(adapter);

        //make a tokenizer to denote that the categories are separated by commas
        ac_categories.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(meal_pref.this, menu.class);
                startActivity(intent_menu);
            }
        });

        sb_distance.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser == true){
                    tv_distance.setText(progress + " miles");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ed_location.getText().toString().isEmpty() || ed_location.getText().toString().equals("")){
                    Toast.makeText(meal_pref.this, "Please enter a location", Toast.LENGTH_SHORT).show();
                }
                else {
                    addToDatabase();
                    Intent intent = new Intent(meal_pref.this, swipe.class);
                    intent.putExtra("type", "newSearch");
                    startActivity(intent);
                }
            }
        });

    }

    //adds the search criteria to the database
    public void addToDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference root = database.getReference();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //adds location to search criteria
        String location = ed_location.getText().toString();
        root.child("search_criteria").child(userID).child("location").setValue(location);

        //adds the distance search criteria
        int distance = sb_distance.getProgress();
        root.child("search_criteria").child(userID).child("distance").setValue(distance);

        //adds the open search criteria
        boolean open = sw_open.isChecked();
        root.child("search_criteria").child(userID).child("open").setValue(open);

        //adds the price ranges search criteria
        String price = "";
        if(tb_p1.isChecked()){
            price = price + "1,";
        }
        if(tb_p2.isChecked()){
            price = price + "2,";
        }
        if(tb_p3.isChecked()){
            price = price + "3,";
        }
        if(tb_p4.isChecked()){
            price = price + "4";
        }
        root.child("search_criteria").child(userID).child("price").setValue(price);

        //adds cuisines to database
        String CSV = ac_categories.getText().toString();
        root.child("search_criteria").child(userID).child("categories").setValue(CSV);
    }

    //save and restore instance state so you don't lose checked boxes when you rotate your phone, etc.

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("tb_p1", tb_p1.isChecked());
        outState.putBoolean("tb_p2", tb_p2.isChecked());
        outState.putBoolean("tb_p3", tb_p3.isChecked());


        super.onSaveInstanceState(outState);

    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        tb_p1.setChecked(savedInstanceState.getBoolean("tb_p1"));
        tb_p2.setChecked(savedInstanceState.getBoolean("tb_p2"));
        tb_p3.setChecked(savedInstanceState.getBoolean("tb_p3"));

    }


}
