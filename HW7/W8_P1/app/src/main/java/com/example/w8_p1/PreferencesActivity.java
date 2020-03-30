package com.example.w8_p1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PreferencesActivity extends AppCompatActivity {

    private Button btnClear, btnSave;
    private EditText txtPhone, txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);

        btnClear = (Button)findViewById(R.id.btnClear);
        btnSave = (Button)findViewById(R.id.btnSave);
        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtText = (EditText) findViewById(R.id.txtText);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtPhone.setText("");
                txtText.setText("");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSharedPreferenceInfo();
            }
        });

        retrieveSharedPreferenceInfo();
    }

    void saveSharedPreferenceInfo(){
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityPreferencesInfo", Context.MODE_PRIVATE);  //Private means no other Apps can access this.
        SharedPreferences.Editor editor = simpleAppInfo.edit();

        editor.putString("txtPhone", txtPhone.getText().toString());
        editor.putString("txtText", txtText.getText().toString());
        editor.apply();
    }

    void retrieveSharedPreferenceInfo(){
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityPreferencesInfo", Context.MODE_PRIVATE);
        txtPhone.setText(simpleAppInfo.getString("txtPhone", ""));
        txtText.setText(simpleAppInfo.getString("txtText",""));
    }
}
