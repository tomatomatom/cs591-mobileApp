package com.example.w5_p3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity  implements TopFragment.TopFragmentListener, BottomFragment.BottomFragmentListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void sendAlertToTop() {
        TopFragment receivingFragment = (TopFragment)getFragmentManager().findFragmentById(R.id.topFragment);
        receivingFragment.newGame();

    }

    @Override
    public void sendWordToBottom(String word) {
        BottomFragment receivingFragment = (BottomFragment)getFragmentManager().findFragmentById(R.id.bottomFragment);
        receivingFragment.setScore(word);
    }
}
