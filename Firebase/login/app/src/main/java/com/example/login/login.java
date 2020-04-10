package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
    private FirebaseDatabase database;

    private TextView tv_head;
    private EditText input_email;
    private TextView lbl_email;
    private TextView lbl_password;
    private EditText input_password;
    private ImageView img_login;
    private Button bttn_login;
    private Button bttn_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        mAuth = FirebaseAuth.getInstance();

        tv_head = findViewById(R.id.tv_head);
        input_email = findViewById(R.id.input_email);
        lbl_email = findViewById(R.id.lbl_email);
        lbl_password = findViewById(R.id.lbl_password);
        input_password = findViewById(R.id.input_password);
        img_login = findViewById(R.id.img_login);
        bttn_login = findViewById(R.id.bttn_login);
        bttn_signup = findViewById(R.id.bttn_signup);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("server/saving-data/indecisive_eater");

        myRef.setValue("test123");

        bttn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                signIn(email, password);
            }
        });

        bttn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();

                signUp(email, password);
            }
        });
    }

    public void signIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                }
                else{
                    Toast.makeText(getBaseContext(), "Email/Password is incorrect.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                }
                else{
                    Toast.makeText(getBaseContext(), "Email/Password is invalid. Try entering a new one.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}

