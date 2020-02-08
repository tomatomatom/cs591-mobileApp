package com.example.sse.lect2_activitylifecycle_logging_savingstate;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
   private static final String MyFlag = "KOBE";  //this will be our trail of breadcrumbs for logging events.
   private static int eventCount = 0;
   private Button btnCtoF;
   private EditText txtC;
   private EditText txtF;
   private Button btnFtoC
  @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       eventCount++;
       Log.i(MyFlag, intToStr(eventCount) + ": Activity onCreate State Transition");        setContentView(R.layout.activity_main);

       btnCtoF = (Button) findViewById(R.id.btnCtoF);
       txtC = (EditText) findViewById(R.id.txtC);
       txtF = (EditText) findViewById(R.id.txtF);
       btnFtoC = (Button) findViewById(R.id.btn_ftc);
       btnCtoF.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View v) {
               Toast.makeText(getBaseContext(),"This is an On Long Click Event", Toast.LENGTH_SHORT).show();
               return false;
           }
       });

       btnCtoF.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String C, F;
               Double DegC, DegF;
               C = txtC.getText().toString();
               DegC = Double.parseDouble(C);
               DegF = DegC*9.0/5.0 + 32;   //todo, dblcheck formula.
               F = DegF.toString();
               txtF.setText(F);
           }
       });

       btnFtoC.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String C, F;
               Double DegC, DegF;
               F = txtF.getText().toString();
               DegF = Double.parseDouble(F);
               DegC = (DegF - 32) * (5.0 / 9.0);
               C = DegC.toString();
               txtC.setText(C);
           }
       });
   }
   public void GeneralClick2(View v){
       Toast.makeText(getBaseContext(), "This is Early Bound", Toast.LENGTH_LONG).show();
   }
   @Override
   protected void onDestroy() {
       eventCount++;
       Log.i(MyFlag, intToStr(eventCount) + ": Activity onDestroy State Transition");
       super.onDestroy();
   }

   @Override
   protected void onResume() {
       eventCount++;
       Log.i(MyFlag, intToStr(eventCount) + ": Activity onResume State Transition");
       super.onResume();
   }
   @Override
   protected void onStart() {
       Log.i(MyFlag,"Logging the OnStart Activity.");
       super.onStart();
   }
   @Override
   protected void onPause() {
       Log.i(MyFlag,"Logging the OnPause Activity.");
       super.onPause();
   }
   public String intToStr(Integer i)
   {
       Log.i(MyFlag, "converting: " + i.toString());
       return i.toString();
   }
   public int strToInt(String S)
   {
      return Integer.parseInt(S);
   }
}
