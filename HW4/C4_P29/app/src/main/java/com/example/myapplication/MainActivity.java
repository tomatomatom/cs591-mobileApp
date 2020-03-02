package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myapplication.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Button btn1 = findViewById(R.id.Chinese);
//        Button btn2 = findViewById(R.id.Korean);
//        Button btn3 = findViewById(R.id.Japanese);
//        Button btn4 = findViewById(R.id.Spanish);
//        Button btn5 = findViewById(R.id.French);
    }
    public void sendChinese(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
//        TextView textView = (TextView) findViewById(R.id.txt1);
//        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "你好世界");
        startActivity(intent);
    }
    public void sendKorean(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
//        TextView textView = (TextView) findViewById(R.id.txt1);
//        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "안녕 세상");
        startActivity(intent);
    }
    public void sendJapanese(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
//        TextView textView = (TextView) findViewById(R.id.txt1);
//        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "こんいちわ、世界！");
        startActivity(intent);
    }
    public void sendSpanish(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
//        TextView textView = (TextView) findViewById(R.id.txt1);
//        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "Hola Mundo");
        startActivity(intent);
    }
    public void sendFrench(View view) {
        Intent intent = new Intent(this, Main2Activity.class);
//        TextView textView = (TextView) findViewById(R.id.txt1);
//        String message = textView.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, "Bonjour le monde");
        startActivity(intent);
    }



}