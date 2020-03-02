package com.example.c7_p28;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Scroller;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private TextView txt_hello;
    private GestureDetector GD;
    private Scroller scroller;
    private DisplayMetrics dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GD = new GestureDetector(this, this);

        scroller=new Scroller(this);

        dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        txt_hello = (TextView)findViewById(R.id.txt_hello);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);

        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {

        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {

        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        if (velocityX >300 || velocityY>300){
            Random R = new Random();
            float dx = R.nextFloat() * dm.widthPixels;
            float dy = R.nextFloat() * dm.heightPixels;

            txt_hello.animate()
                    .x(dx)
                    .y(dy)
                    .setDuration(0)
                    .start();

        } else{
            scroller.fling(txt_hello.getScrollX(), txt_hello.getScrollY(), (int) velocityX/4, (int) velocityY/4, 0, 1000, 0, 1000);
            txt_hello.postInvalidate();
        }

        return true;
    }
}
