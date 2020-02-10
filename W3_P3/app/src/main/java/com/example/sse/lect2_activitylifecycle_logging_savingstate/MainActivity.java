package com.example.sse.lect2_activitylifecycle_logging_savingstate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellSignalStrength;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends AppCompatActivity {



    private static SeekBar seekBar_top;
    private static SeekBar seekBar_bot;
    private static TextView Cel_name;
    private static TextView Fah_name;
    private static TextView Cel_temp;
    private static TextView Fah_temp;
    private static TextView interesting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//Let's find our view references
        seekBar_top = (SeekBar) findViewById(R.id.seekBar_top);
        seekBar_bot = (SeekBar) findViewById(R.id.seekBar_bot);
        Cel_name = (TextView) findViewById(R.id.Celsius_name);
        Cel_temp = (TextView) findViewById(R.id.Celsius_temp);
        Fah_name = (TextView) findViewById(R.id.Fahrenheit_name);
        Fah_temp = (TextView) findViewById(R.id.Fahrenheit_temp);
        interesting = (TextView) findViewById(R.id.txt_interesting);

        Cel_temp.setText("Celsius = " + 0);
        Fah_temp.setText("Fahrenheit = " + 32);
        seekBar_bot.setProgress(32);


        seekBar_top.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int Celsius_num;


                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        Celsius_num = i;
//                        Cel_temp.setText("Celsius = " +   i);
//                        Fah_temp.setText("Fahrenheit = " +  (i *9/5+32));
////                        seekBar_bot.setProgress(Math.round(i *9/5+32));
                        if (Celsius_num <= 12){
                            interesting.setText(R.string.cold);
                        }
                        else{
                            interesting.setText(R.string.not_cold);
                        }

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        seekBar_bot.setProgress(Math.round(Celsius_num *9/5+32));
                        Cel_temp.setText("Celsius = " +   Celsius_num);
                        Fah_temp.setText("Fahrenheit = " +  (Celsius_num *9/5+32));
//                        seekBar_bot.setProgress(Math.round(i *9/5+32));


                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Cel_temp.setText("Celsius = " +  Celsius_num);
                        Fah_temp.setText("Fahrenheit = " +  (Celsius_num * 9/5 + 32));
                        seekBar_bot.setProgress(Math.round(Celsius_num *9/5+32));

                    }
                }
        );

        seekBar_bot.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int Fahrenheit_num;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                        Fahrenheit_num = i;
//                        Fah_temp.setText("Fahrenheit = " + i);
//                        Cel_temp.setText("Celsius = " +  ((i-32)*5/9));
//                        System.out.println(Math.round((i-32)*5/9));
//                        seekBar_top.setProgress(Math.round((i-32)*5/9));

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        seekBar_top.setProgress(Math.round((Fahrenheit_num-32)*5/9));
                        Fah_temp.setText("Fahrenheit = " + Fahrenheit_num);
                        Cel_temp.setText("Celsius = " +  ((Fahrenheit_num-32)*5/9));

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        Fah_temp.setText("Fahrenheit = " +  Fahrenheit_num);
                        Cel_temp.setText("Celsius = " +  ((Fahrenheit_num-32)*5/9));
                        seekBar_top.setProgress(Math.round((Fahrenheit_num-32)*5/9));

                    }
                }
        );


    }
}






