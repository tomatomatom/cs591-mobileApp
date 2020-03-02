package com.example.c2_p23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.graphics.drawable.ColorDrawable;

public class MainActivity extends AppCompatActivity {

    private Button btn_change;
    private View traffic_light;


    //ColorDrawable ylw = new ColorDrawable(getResources().getColor(R.color.YELLOW));

    int redId = getResources().getColor(R.color.RED);
    int grnId = getResources().getColor(R.color.GREEN);

    //int ylwId = ylw.getColor();

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
                int colorId = ((ColorDrawable)traffic_light.getBackground()).getColor();

                if (colorId == grnId){
                    traffic_light.setBackgroundColor(getResources().getColor(R.color.YELLOW));

                } else if (colorId == redId){
                    traffic_light.setBackgroundColor(getResources().getColor(R.color.GREEN));

                } else{
                    traffic_light.setBackgroundColor(getResources().getColor(R.color.RED));

                }
            }

        });

    }
}
