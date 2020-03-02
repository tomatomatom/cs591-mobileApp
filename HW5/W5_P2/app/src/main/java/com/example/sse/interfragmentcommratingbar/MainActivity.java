package com.example.sse.interfragmentcommratingbar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends Activity implements ControlFragment.ControlFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void sendIndex(int index) {
        DrawableFragment receivefrag =(DrawableFragment)getFragmentManager().findFragmentById(R.id.drawable_fragment);
        receivefrag.setPicture(index);
    }

}
