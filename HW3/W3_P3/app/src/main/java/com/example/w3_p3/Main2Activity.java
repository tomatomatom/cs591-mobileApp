package com.example.w3_p3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import java.util.Random;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    String welcomeMsg = "Welcome %s!";
    String userName;
    TextView num1;
    TextView num2;
    EditText answer;
    Button generate;
    Button submit;
    TextView prgs;
    int count = 0;
    int score = 0;
    int k;
    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    boolean isPreviouslyDisplayed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
        isPreviouslyDisplayed = mSharedPreferences.getBoolean("isPreviouslyDisplayed", false);

        userName = getIntent().getStringExtra("username");
        if (!isPreviouslyDisplayed) {
            Toast.makeText(getApplicationContext(), String.format(welcomeMsg, userName), Toast.LENGTH_SHORT).show();
            isPreviouslyDisplayed = true;
        }

        userName = getIntent().getStringExtra("username");
        Toast.makeText(getApplicationContext(), String.format(welcomeMsg, userName), Toast.LENGTH_SHORT).show();

        num1 = (TextView) findViewById(R.id.tvNum1);
        num2 = (TextView) findViewById(R.id.tvNum2);
        generate =(Button) findViewById(R.id.btnGenerate);
        submit = (Button) findViewById(R.id.btnSubmit);
        answer = (EditText) findViewById(R.id.etAnswer);
        prgs = (TextView) findViewById(R.id.tvProgress);

        generate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                count = 0;
                score = 0;
                Random rand = new Random();
                int rand2 = rand.nextInt(30) + 1;
                k = rand.nextInt(10) + 1;
                int rand1 = rand2 * k;

                num1.setText(Integer.toString(rand1));
                num2.setText(Integer.toString(rand2));
                prgs.setText("0/10");
            }

        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (count < 9) {
                    String isCorrect = answer.getText().toString();
                    String divisor = Integer.toString(k);

                    if (isCorrect.equals(divisor)) {
                        score++;
                    }
                    Random rand = new Random();
                    int rand2 = rand.nextInt(30) + 1;
                    k = rand.nextInt(10) + 1;
                    int rand1 = rand2 * k;

                    num1.setText(Integer.toString(rand1));
                    num2.setText(Integer.toString(rand2));

                    count ++;
                    prgs.setText(count + "/10");
                } else if (count == 9){
                    String isCorrect = answer.getText().toString();
                    String divisor = Integer.toString(k);

                    if (isCorrect.equals(divisor)) {
                        score++;
                    }
                    prgs.setText("10/10");
                    Toast.makeText(getApplicationContext(), "Your Score is: " + score, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mEditor.putBoolean("isPreviouslyDisplayed", isPreviouslyDisplayed).apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("num1", num1.getText().toString());
        outState.putString("num2", num2.getText().toString());
        outState.putString("answer", answer.getText().toString());
        outState.putString("prgs", prgs.getText().toString());
        outState.putInt("count", count);
        outState.putInt("score", score);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        num1.setText(savedInstanceState.getString("num1"));
        num2.setText(savedInstanceState.getString("num2"));
        answer.setText(savedInstanceState.getString("answer"));
        prgs.setText(savedInstanceState.getString("prgs"));
        count = savedInstanceState.getInt("count");
        score = savedInstanceState.getInt("score");
    }

}
