package com.example.w4_p4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import java.lang.Math;

public class HomeActivity extends AppCompatActivity {

    private ImageView img_home;
    private TextView lbl_home;
    private GestureDetector GD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        lbl_home = findViewById(R.id.lbl_home);
        img_home = findViewById(R.id.img_home);

        GD = new GestureDetector(this, new MyGestureListener());
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY) {
            int min = 120;

            float x1 = e1.getX();
            float x2 = e2.getX();
            float y1 = e1.getY();
            float y2 = e2.getY();

            float x_distance = Math.abs(x1 - x2);
            float y_distance = Math.abs(y1 - y2);

            if (y_distance < min && x_distance > min) {
                //right to left
                if (x1 < x2) {
                    Intent east = new Intent(HomeActivity.this, EastActivity.class);
                    startActivity(east);
                    finish();
                    return true;
                }
                //left to right
                else {
                    Intent west = new Intent(HomeActivity.this, WestActivity.class);
                    startActivity(west);
                    finish();
                    return true;
                }
            } else {
                //down to up
                if (y1 > y2) {
                    Intent north = new Intent(HomeActivity.this, NorthActivity.class);
                    startActivity(north);
                    finish();
                    return true;
                }
                //up to down
                else {
                    Intent south = new Intent(HomeActivity.this, SouthActivity.class);
                    startActivity(south);
                    finish();
                    return true;
                }
            }
        }
    }
}
