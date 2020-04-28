package com.example.indecisiveeater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class EndResults extends AppCompatActivity {

    private TextView lbl_noresults;
    private Button bttn_menu;
    private Button bttn_newSearch;
    private Button bttn_liked;
    private Button bttn_searchAgain;
    private Button bttn_dietary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.endresults);

        lbl_noresults = findViewById(R.id.lbl_noresults);
        bttn_menu = findViewById(R.id.bttn_menu);
        bttn_newSearch = findViewById(R.id.bttn_newSearch);
        bttn_liked = findViewById(R.id.bttn_liked);
        bttn_searchAgain = findViewById(R.id.bttn_searchAgain);
        bttn_dietary = findViewById(R.id.bttn_dietary);

        bttn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(EndResults.this, menu.class);
                startActivity(intent_menu);
            }
        });

        bttn_dietary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_dietary = new Intent(EndResults.this, DR.class);
                startActivity(intent_dietary);
            }
        });

        bttn_liked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_liked = new Intent(EndResults.this, swipe.class);
                intent_liked.putExtra("type", "liked");
                startActivity(intent_liked);
            }
        });

        bttn_newSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_new = new Intent(EndResults.this, meal_pref.class);
                startActivity(intent_new);
            }
        });

        bttn_searchAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_again = new Intent(EndResults.this, swipe.class);
                intent_again.putExtra("type", "searchAgain");
                startActivity(intent_again);
            }
        });
    }
}
