package com.example.checkemail;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
public class MainActivity extends AppCompatActivity {
   private Button myCheckbtn;
   private EditText etEnter;
   private String flag="myflag";
   private TextView tvAnswer;
   @Override
   protected void onCreate(Bundle savedInstanceState) {
       Log.i(flag,"oncreat!");
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
       myCheckbtn = (Button) findViewById(R.id.myCheckbtn);
       etEnter = (EditText) findViewById(R.id.etEnter);
       tvAnswer = (TextView) findViewById(R.id.tvAnswer);

       myCheckbtn.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               String str=etEnter.getText().toString();
               if(str.contains("@")){
                   tvAnswer.setText("VALID");
                   tvAnswer.setBackgroundColor(0xFF6DD072);
               }
               else {
                   tvAnswer.setText("INVALID");
                   tvAnswer.setBackgroundColor(0xfff00000);
               }

           }
       });
   }
   @Override
   protected void onPause() {
       super.onPause();
       Log.i(flag,"pausing");
   }
   @Override
   protected void onDestroy() {
       super.onDestroy();
       Log.i(flag,"destroying");
   }
   @Override
   protected void onResume() {
       super.onResume();
       Log.i(flag,"resuming");
   }
}
