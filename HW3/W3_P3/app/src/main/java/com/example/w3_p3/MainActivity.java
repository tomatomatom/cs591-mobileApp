package com.example.w3_p3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;


public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private Button login;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        info = (TextView) findViewById(R.id.tvInfo);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                validate(name.getText().toString(), password.getText().toString());
            }
        });
    }

    private void validate(String userName, String userPassword){
        if("1234".equals(userPassword)){
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            intent.putExtra("username", userName);
            startActivity(intent);

        }else{
            info.setText("Incorrect Password");
        }

    }
}