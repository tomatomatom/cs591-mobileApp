package com.example.w5_p3;


import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    private TextView txt_instructions;
    private TextView txt_word;
    private Button btn_clear;
    private Button btn_submit;


    public TopFragment() {
        // Required empty public constructor
    }

    //create the interface
    public interface TopFragmentListener{
        public void sendWordToBottom(String word);

    }

    //make an instance of the interface
    TopFragmentListener TFL;

    //attach interface instance to context (MainActivity)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        TFL= (TopFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view =  inflater.inflate(R.layout.fragment_top, container, false);


        txt_instructions = (TextView) view.findViewById(R.id.txt_instructions);
        txt_word = (TextView) view.findViewById(R.id.txt_word);
        btn_clear = (Button) view.findViewById(R.id.btn_clear);
        btn_submit = (Button) view.findViewById(R.id.btn_submit);

        //when submit is clicked, we send the word to the bottom fragment to calculate the score
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = (String) txt_word.getText();
                TFL.sendWordToBottom(word);

            }
        });

        //when clear is clicked, we reset the current letters selected
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_word.setText("");
            }
        });

        newGame();



        return view;
    }

    public void newGame(){

    }

}
