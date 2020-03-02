package com.example.w2p2;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.EditText;

///////  Set of imports for Gesture Detection ////////
import android.view.MotionEvent;
import android.view.GestureDetector;
import java.lang.String;
//import android.support.v4.view.GestureDetector;
//////////////////////////////////////////////////////

public class MainActivity extends Activity
        implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    private EditText euro;
    private TextView usd;
    private TextView won;
    private TextView yuan;
    private TextView peso;
    private GestureDetector GD;    //must instantiate the gesture detector

    private TextView test;

    //private static final int SWIPE_MIN_DISTANCE = 120;
    //private static final int SWIPE_MAX_OFF_PATH = 250;
    //private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GD = new GestureDetector(this);   //Context, Listener as per Constructor Doc.
        //GD.setOnDoubleTapListener(this);   //DoubleTaps implemented a bit differently, must be bound like this.

        euro = (EditText) findViewById(R.id.etEuro);
        usd = (TextView) findViewById(R.id.tvDollar);
        won = (TextView) findViewById(R.id.tvWon);
        yuan = (TextView) findViewById(R.id.tvYuan);
        peso = (TextView) findViewById(R.id.tvPeso);

        test = (TextView) findViewById(R.id.test);


    }

    /*/////////////////////////////////////////////////////////////////////////
    //very important step, otherwise we won't be able to capture our touches//
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);               //Our GD will not automatically receive Android Framework Touch notifications.
        // Insert this line to consume the touch event locally by our GD,
        // IF YOU DON'T insert this before the return, our GD will not receive the event, and therefore won't do anything.
        convertEuro();
        return super.onTouchEvent(event);          // Do this last, why?
    }
    /////////////////////////////////////////////////////////////////////////*/
/*
    public void convertEuro(){
        String temp = euro.getText().toString();
        double value = 0;
        if (!"".equals(temp)){
            value = Double.parseDouble(temp);
        }
        double w = value * 1301.01;
        double u = value * 1.08;
        double p = value * 20.15;
        double y = value * 7.58;
        usd.setText(String.format("$ %.2f", u));
        won.setText(String.format("₩ %.2f", w));
        yuan.setText(String.format("¥ %.2f", y));
        peso.setText(String.format("$ %.2f", p));
        //String.format("Value of a: %.2f", a)
    }
*/
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return true;
    }


    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
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

  /*  @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2,
                            float distanceX, float distanceY) {

        boolean result = false;
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();
        // which was greater? movement across Y or X?
        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(distanceX) > SWIPE_VELOCITY_THRESHOLD) {
            result = true;
        } else {
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(distanceY) > SWIPE_VELOCITY_THRESHOLD) {
                onScrollBottom();
            } else {
                onScrollTop();
            }
            result = true;
        }
        return result;
    }

   @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        float diffY = e2.getY() - e1.getY();
        float diffX = e2.getX() - e1.getX();
        // which was greater? movement across Y or X?
        if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
            //return result;
            result = true;
        } else {
            if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                onSwipeBottom();
            } else {
                onSwipeTop();
            }
            result = true;
        }
        return result;
    }

  */

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float v, float v1) {
        float y1 = e1.getY();
        float y2 = e2.getY();
        float velocity = Math.abs(v - v1);

        test.setText(velocity + "");

        if(velocity > SWIPE_VELOCITY_THRESHOLD){
            return false;
        }

        if(y1 > y2){
            onScrollTop();
        }
        else{
            onScrollBottom();
        }

        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        float y1 = e1.getY();
        float y2 = e2.getY();
        float velocity = Math.abs(v - v1);

        if(y1 > y2 && velocity > SWIPE_VELOCITY_THRESHOLD){
            onSwipeTop();
        }
        else if(y1 < y2 && velocity > SWIPE_VELOCITY_THRESHOLD){
            onSwipeBottom();
        }
        else{
            return false;
        }
        return true;
    }

    private void onSwipeBottom() {
        String temp = euro.getText().toString();
        double value = 0;
        if (!"".equals(temp)){
            value = Double.parseDouble(temp) - 1;
        }
        double w = value * 1301.01;
        double u = value * 1.08;
        double p = value * 20.15;
        double y = value * 7.58;

        euro.setText(String.format("%.2f",value));
        usd.setText(String.format("$ %.2f", u));
        won.setText(String.format("₩ %.2f", w));
        yuan.setText(String.format("¥ %.2f", y));
        peso.setText(String.format("$ %.2f", p));
    }

    private void onSwipeTop() {
        String temp = euro.getText().toString();
        double value = 0;
        if (!"".equals(temp)){
            value = Double.parseDouble(temp) + 1;
        }
        double w = value * 1301.01;
        double u = value * 1.08;
        double p = value * 20.15;
        double y = value * 7.58;

        euro.setText(String.format("%.2f",value));
        usd.setText(String.format("$ %.2f", u));
        won.setText(String.format("₩ %.2f", w));
        yuan.setText(String.format("¥ %.2f", y));
        peso.setText(String.format("$ %.2f", p));
    }
    private  void onScrollTop(){
        String temp = euro.getText().toString();
        double value = 0;
        if (!"".equals(temp)){
            value = Double.parseDouble(temp) + 0.05;
        }
        double w = value * 1301.01;
        double u = value * 1.08;
        double p = value * 20.15;
        double y = value * 7.58;

        euro.setText(String.format("%.2f",value));
        usd.setText(String.format("$ %.2f", u));
        won.setText(String.format("₩ %.2f", w));
        yuan.setText(String.format("¥ %.2f", y));
        peso.setText(String.format("$ %.2f", p));
    }
    private void onScrollBottom(){
        String temp = euro.getText().toString();
        double value = 0;
        if (!"".equals(temp)){
            value = Double.parseDouble(temp) - 0.05;
        }
        double w = value * 1301.01;
        double u = value * 1.08;
        double p = value * 20.15;
        double y = value * 7.58;

        euro.setText(String.format("%.2f",value));
        usd.setText(String.format("$ %.2f", u));
        won.setText(String.format("₩ %.2f", w));
        yuan.setText(String.format("¥ %.2f", y));
        peso.setText(String.format("$ %.2f", p));

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        GD.onTouchEvent(event);               //Our GD will not automatically receive Android Framework Touch notifications.
        // Insert this line to consume the touch event locally by our GD,
        // IF YOU DON'T insert this before the return, our GD will not receive the event, and therefore won't do anything.

        //convertEuro();
        return super.onTouchEvent(event);          // Do this last, why?
    }

}