package com.example.a2fragments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import java.util.Random;

public class MainActivity extends AppCompatActivity implements FragmentA.FragmentAListener, FragmentB.FragmentBListener {
    private FragmentA fragmentA;
    private FragmentB fragmentB;
    Random r = new Random();
    final int i = r.nextInt(4);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentA = new FragmentA();
        fragmentB = new FragmentB();




        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_a, fragmentA)
                .replace(R.id.container_b, fragmentB)
                .commit();
    }

    @Override
    public void onInputASent(CharSequence input) {
        fragmentB.updateEditText(input);
    }

    @Override
    public void onInputBSent(CharSequence input) {

        //fragmentA.updateEditText(Integer.toString(i+1));
//        System.out.println(i+1);

        if (input.toString().equals(Integer.toString(i+1))){
            fragmentA.updateEditText("Success");
        }else{
            fragmentA.updateEditText("YOU FAILED");
        }
    }
}
