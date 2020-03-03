package com.example.w5_p3;


import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    private TextView txt_instructions;
    private TextView txt_word;
    private Button btn_clear;
    private Button btn_submit;
    private Button[] btn_letters;
    private String[] letters;
    private Map<Integer, List<Integer>> letters_neighbours = new HashMap<Integer, List<Integer>>(); //neighbour indices of a letter index

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

        btn_letters = new Button[16];
        letters = new String[16];
        btn_letters[0] = (Button) view.findViewById(R.id.btnLetter1);
        btn_letters[1] = (Button) view.findViewById(R.id.btnLetter2);
        btn_letters[2] = (Button) view.findViewById(R.id.btnLetter3);
        btn_letters[3] = (Button) view.findViewById(R.id.btnLetter4);
        btn_letters[4] = (Button) view.findViewById(R.id.btnLetter5);
        btn_letters[5] = (Button) view.findViewById(R.id.btnLetter6);
        btn_letters[6] = (Button) view.findViewById(R.id.btnLetter7);
        btn_letters[7] = (Button) view.findViewById(R.id.btnLetter8);
        btn_letters[8] = (Button) view.findViewById(R.id.btnLetter9);
        btn_letters[9] = (Button) view.findViewById(R.id.btnLetter10);
        btn_letters[10] = (Button) view.findViewById(R.id.btnLetter11);
        btn_letters[11] = (Button) view.findViewById(R.id.btnLetter12);
        btn_letters[12] = (Button) view.findViewById(R.id.btnLetter13);
        btn_letters[13] = (Button) view.findViewById(R.id.btnLetter14);
        btn_letters[14] = (Button) view.findViewById(R.id.btnLetter15);
        btn_letters[15] = (Button) view.findViewById(R.id.btnLetter16);

        List<Integer> buttonList1 = Arrays.asList(1,4,5);
        letters_neighbours.put(0, buttonList1);
        List<Integer> buttonList2 = Arrays.asList(0,2,4,5,6);
        letters_neighbours.put(1, buttonList2);
        List<Integer> buttonList3 = Arrays.asList(1,3,5,6,7);
        letters_neighbours.put(2, buttonList3);
        List<Integer> buttonList4 = Arrays.asList(2,6,7);
        letters_neighbours.put(3, buttonList4);
        List<Integer> buttonList5 = Arrays.asList(0,1,5,8,9);
        letters_neighbours.put(4, buttonList5);
        List<Integer> buttonList6 = Arrays.asList(0,1,2,4,6,8,9,10);
        letters_neighbours.put(5, buttonList6);
        List<Integer> buttonList7 = Arrays.asList(1,2,3,5,7,9,10,11);
        letters_neighbours.put(6, buttonList7);
        List<Integer> buttonList8 = Arrays.asList(2,3,6,10,11);
        letters_neighbours.put(7, buttonList8);
        List<Integer> buttonList9 = Arrays.asList(4,5,9,12,13);
        letters_neighbours.put(8, buttonList9);
        List<Integer> buttonList10 = Arrays.asList(4,5,6,8,10,12,13,14);
        letters_neighbours.put(9, buttonList10);
        List<Integer> buttonList11 = Arrays.asList(5,6,7,9,11,13,14,15);
        letters_neighbours.put(10, buttonList11);
        List<Integer> buttonList12 = Arrays.asList(6,7,10,14,15);
        letters_neighbours.put(11, buttonList12);
        List<Integer> buttonList13 = Arrays.asList(8,9,13);
        letters_neighbours.put(12, buttonList13);
        List<Integer> buttonList14 = Arrays.asList(8,9,10,12,14);
        letters_neighbours.put(13, buttonList14);
        List<Integer> buttonList15 = Arrays.asList(9,10,11,13,15);
        letters_neighbours.put(14, buttonList15);
        List<Integer> buttonList16 = Arrays.asList(10,11,14);
        letters_neighbours.put(15, buttonList16);


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
        Random r = new Random();
        for (int i=0;i<btn_letters.length;i++) {
            Button button = btn_letters[i];
            char c = (char) (r.nextInt(26) + 'a');
            String s = Character.toString(c);
            button.setText(s);
            letters[i] = s;
            button.setEnabled(true);
            txt_word.setText("");
        }

        for (int i=0;i<btn_letters.length;i++) {
            final Button button = btn_letters[i];
            final String s = letters[i];
            final List<Integer> neighbours = letters_neighbours.get(i);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //txt_word.setText(txt_word.getText() + s);
                    //button.setEnabled(false);
                    for (int j=0;j<btn_letters.length;j++) {
                        Button button_other = btn_letters[j];
                        final String s_other = letters[j];

                        // first disable all buttons
                        button_other.setEnabled(false);

                        // then enable the neighbours that do not appear in the current text
                        if (neighbours.contains(j) && !txt_word.getText().toString().contains(s_other)){
                            button_other.setEnabled(true);
                        }
                    }
                    txt_word.setText(txt_word.getText() + s);
                }
            });
        }
    }


}