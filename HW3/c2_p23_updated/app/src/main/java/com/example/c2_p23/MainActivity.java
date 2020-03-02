package com.example.c2_p23;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends AppCompatActivity {

    private Button btn_change;
    private View traffic_light;



    int redId = Color.parseColor("#F93004");
    int grnId = Color.parseColor("#79E21C");
    int ylwId = Color.parseColor("#F7F413");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_change = (Button)findViewById(R.id.btn_change);
        traffic_light = (View)findViewById(R.id.traffic_light);

        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ColorDrawable viewColor = (ColorDrawable) traffic_light.getBackground();
                int colorId = viewColor.getColor();

                if (colorId == grnId){
                    traffic_light.setBackgroundColor(ylwId);
                } else if (colorId == redId){
                    traffic_light.setBackgroundColor(grnId);

                } else{
                    traffic_light.setBackgroundColor(redId);

                }
            }

        });

    }
}
