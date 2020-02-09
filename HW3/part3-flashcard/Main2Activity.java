package com.example.flashcard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import java.util.Random;
import android.widget.Toast;


public class Main2Activity extends AppCompatActivity {

    String welcomeMsg = "Welcome Admin!";
    TextView num1;
    TextView num2;
    EditText answer;
    Button generate;
    Button submit;
    int count = 0;
    int score = 0;
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Toast.makeText(getApplicationContext(), welcomeMsg, Toast.LENGTH_SHORT).show();

        num1 = (TextView) findViewById(R.id.tvNum1);
        num2 = (TextView) findViewById(R.id.tvNum2);
        generate =(Button) findViewById(R.id.btnGenerate);
        submit = (Button) findViewById(R.id.btnSubmit);
        answer = (EditText) findViewById(R.id.etAnswer);

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
                        } else {
                            String isCorrect = answer.getText().toString();
                            String divisor = Integer.toString(k);

                            if (isCorrect.equals(divisor)) {
                                score++;

                            }

                            Toast.makeText(getApplicationContext(), "Your Score is: " + score, Toast.LENGTH_SHORT).show();
                        }



            }

        });

    }
}
