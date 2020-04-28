package com.example.indecisiveeater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class login extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private TextView tv_head;
    private EditText input_email;
    private TextView lbl_email;
    private TextView lbl_password;
    private EditText input_password;
    //private ImageView img_login;
    private Button bttn_login;
    private Button bttn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        mAuth = FirebaseAuth.getInstance();

        tv_head = findViewById(R.id.tv_head);
        input_email = findViewById(R.id.input_email);
        lbl_email = findViewById(R.id.lbl_email);
        lbl_password = findViewById(R.id.lbl_password);
        input_password = findViewById(R.id.input_password);
        //img_login = findViewById(R.id.img_login);
        bttn_login = findViewById(R.id.bttn_login);
        bttn_signup = findViewById(R.id.bttn_signup);

        bttn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_signUp = new Intent(login.this, signUp.class);
                startActivity(intent_signUp);
            }
        });

        bttn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(getBaseContext(), "Please enter an email & password",Toast.LENGTH_LONG).show();
                }
                else {
                    signIn(email, password);
                }
            }
        });
    }

    public void signIn(final String email, final String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getBaseContext(), "You're all signed in!", Toast.LENGTH_LONG).show();
                    Intent intent_menu = new Intent(login.this, menu.class);
                    startActivity(intent_menu);
                }
                else{
                    Toast.makeText(getBaseContext(), "Email/Password is incorrect", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

