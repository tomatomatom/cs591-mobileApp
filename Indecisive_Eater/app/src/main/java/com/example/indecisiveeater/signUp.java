package com.example.indecisiveeater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signUp extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    private TextView tv_head;
    private TextView tv_signup;
    private EditText input_email;
    private TextView lbl_email;
    private TextView lbl_password;
    private EditText input_password;
    private Button bttn_createAccount;
    private Button bttn_returnLogin;
    private TextView tv_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        mAuth = FirebaseAuth.getInstance();

        tv_head = findViewById(R.id.tv_head);
        tv_signup = findViewById(R.id.tv_signup);
        tv_password = findViewById(R.id.tv_password);
        input_email = findViewById(R.id.input_email);
        lbl_email = findViewById(R.id.lbl_email);
        lbl_password = findViewById(R.id.lbl_password);
        input_password = findViewById(R.id.input_password);
        bttn_createAccount = findViewById(R.id.bttn_createAccount);
        bttn_returnLogin = findViewById(R.id.bttn_returnLogin);

        bttn_returnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_login = new Intent(signUp.this, login.class);
                startActivity(intent_login);
            }
        });

        bttn_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = input_email.getText().toString();
                String password = input_password.getText().toString();
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Please enter an email & password", Toast.LENGTH_LONG).show();
                } else {
                    createAccount(email, password);
                }
            }
        });
    }

    public void createAccount(final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    writeNewUser(email, password);
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(getBaseContext(), "Thanks for signing up!", Toast.LENGTH_LONG).show();
                    Intent intent_menu = new Intent(signUp.this, menu.class);
                    startActivity(intent_menu);
                } else {
                    Toast.makeText(getBaseContext(), "Please enter a valid email/password.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void writeNewUser(String email, String password){
        //create new User
        User user = new User(email, password);

        //creates a new user in the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference root = database.getReference();
        String uID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        root.child("users").child(uID).setValue(user);
    }
}
