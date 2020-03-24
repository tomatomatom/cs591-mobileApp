package com.example.w6_p2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button btn1, btn2;
    private TextView txtView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn1 = (Button) findViewById(R.id.button);
        btn2 = (Button) findViewById(R.id.button2);
        txtView = (TextView) findViewById(R.id.textView);
        imageView = (ImageView) findViewById(R.id.imageView);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtView.setText("Image of Cat");
                imageView.setImageResource(R.drawable.cat);
                saveSharedPreferenceInfo(1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtView.setText("Image of Dog");
                imageView.setImageResource(R.drawable.dog);
                saveSharedPreferenceInfo(2);
            }
        });

        retrieveSharedPreferenceInfo();
    }

    void saveSharedPreferenceInfo(int button_choice){
        //Refer to the SharedPreference Object.
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);

        //Create an Shared Preferences Editor for Editing Shared Preferences.
        SharedPreferences.Editor editor = simpleAppInfo.edit();

        editor.putString("txtView", txtView.getText().toString());

        if (button_choice == 1) {
            editor.putInt("image", R.drawable.cat);
        }
        else if (button_choice == 2) {
            editor.putInt("image", R.drawable.dog);
        }

        //Save the stored information.
        editor.apply();
    }

    void retrieveSharedPreferenceInfo(){
        SharedPreferences simpleAppInfo = getSharedPreferences("ActivityOneInfo", Context.MODE_PRIVATE);

        //Retrieving data from shared preferences hashmap.
        txtView.setText(simpleAppInfo.getString("txtView", "No image this time"));
        imageView.setImageResource(simpleAppInfo.getInt("image",R.drawable.empty));
    }


}
