package com.example.w5_p3;


import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


/**
 * A simple {@link Fragment} subclass.
 */
public class BottomFragment extends Fragment {

    private int currScore = 0;
    private Set<String> words_used = new HashSet<>();
    private Set<String> dict = new HashSet<>();

    private TextView txt_score;
    private Button btn_new_game;


    public BottomFragment() {
        // Required empty public constructor
    }

    //initialize the interface
    public interface BottomFragmentListener{
        public void sendAlertToTop();

    }

    //make an instance of the interface
    BottomFragmentListener BFL;

    //attach the interface to context (MainActivity)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BFL= (BottomFragmentListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);

        txt_score=(TextView) view.findViewById(R.id.txt_score);
        btn_new_game=(Button) view.findViewById(R.id.btn_new_game);

        //put dictionary words into a set
        Scanner scan = new Scanner("words.txt");
        while(scan.hasNextLine()){
            dict.add(scan.nextLine());
        }
        scan.close();

        //when new game is clicked, we need to send an alert to the top fragment using the interface
        btn_new_game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currScore=0;
                txt_score.setText("Score: "+currScore);
                BFL.sendAlertToTop();

            }
        });




        return view;
    }

    //when we get a word from the TopFragment, we need to change the score
    public void setScore (String word){
        int newScore = currScore;

        if (!words_used.contains(word) && word.length()>3){

            int numVowels =countVowels(word);

            if (numVowels>1 && dict.contains(word)){

                int numConsonants= word.length()-numVowels;
                newScore+=numConsonants;
                newScore+=numVowels*5;

                if(word.matches("S|Z|P|X|Q")){
                    newScore*=2;
                }

                Toast.makeText(getActivity(), "Correct! +"+(newScore-currScore), Toast.LENGTH_SHORT).show();

                currScore=newScore;
                txt_score.setText("Score: "+currScore);

                return;


            }
        }

        Toast.makeText(getActivity(), "Incorrect, -10", Toast.LENGTH_SHORT).show();

        currScore-=10;
        txt_score.setText("Score: "+currScore);



    }

    private int countVowels(String word){
        int numVowels=0;
        String vowels = "aeiouy";
        for(int i=0;i<word.length();i++){
            if (vowels.contains(Character.toString(word.charAt(i)))){
                numVowels++;
            }
        }
        return numVowels;
    }

}
