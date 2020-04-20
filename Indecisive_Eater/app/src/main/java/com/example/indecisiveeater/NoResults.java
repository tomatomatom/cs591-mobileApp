package com.example.indecisiveeater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NoResults extends AppCompatActivity {
    private TextView lbl_none;
    private Button bttn_menu;
    private Button bttn_newSearch;
    private Button bttn_dietary;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noresults);

        lbl_none = findViewById(R.id.lbl_none);
        bttn_menu = findViewById(R.id.bttn_menu);
        bttn_newSearch = findViewById(R.id.bttn_newSearch);
        bttn_dietary = findViewById(R.id.bttn_dietary);

        bttn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(NoResults.this, menu.class);
                startActivity(intent_menu);
            }
        });

        bttn_dietary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_dietary = new Intent(NoResults.this, DR.class);
                startActivity(intent_dietary);
            }
        });
        //TODO: edit intent accordingly
        bttn_newSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent_new = new Intent(NoResults.this, )
                //startActivity(intent_new);
            }
        });
    }
}
