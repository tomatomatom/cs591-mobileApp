package com.example.hw1_221;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
   TextView number1;
   TextView number2;
   Button Add_button;
   TextView result;
   int ans = 0;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       number1=(TextView) findViewById(R.id.textView_rand1);
       number2=(TextView) findViewById(R.id.textView_rand2);
       Add_button=(Button) findViewById(R.id.add_button);
       result = (TextView) findViewById(R.id.textView_answer);
       Add_button.setOnClickListener(new View.OnClickListener() {
           public void onClick(View v) {
               Random rand = new Random();
               int num1 = rand.nextInt(10) + 1;
               int num2 = rand.nextInt(10) + 1;
               number1.setText(Integer.toString(num1));
               number2.setText(Integer.toString(num2));
               int sum = num1+num2;
               result.setText(Integer.toString(sum));
           }
       });
   }
}
