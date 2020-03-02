//Reference code: https://github.com/SueSmith/android-hangman-game

package com.example.w4_p5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.Random;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String[] words, hints;
    private Random rand = new Random();
    private int randomNum; //random index of word & hint
    private String currWord;
    private LinearLayout wordLayout; //the layout holding the answer
    private TextView[] charViews; //text views for each letter in the answer
    private TextView tvHint;
    private Button buttonNewGame, buttonHint;
    private GridView letters;
    private LetterAdapter ltrAdapt;
    private ImageView[] bodyParts;
    private int numParts = 6; //6 parts of body
    private int currPart;
    private int numChars; //num chars in word
    private int numCorr; //num correct so far
    private Toast toast;
    private int score = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //read in answers and hints
        Resources res = getResources();
        words = res.getStringArray(R.array.words);
        hints = res.getStringArray(R.array.hints);

        currWord="";
        wordLayout = (LinearLayout) findViewById(R.id.word);
        letters = (GridView)findViewById(R.id.letters);

        bodyParts = new ImageView[numParts];
        bodyParts[0] = (ImageView)findViewById(R.id.head);
        bodyParts[1] = (ImageView)findViewById(R.id.body);
        bodyParts[2] = (ImageView)findViewById(R.id.arm1);
        bodyParts[3] = (ImageView)findViewById(R.id.arm2);
        bodyParts[4] = (ImageView)findViewById(R.id.leg1);
        bodyParts[5] = (ImageView)findViewById(R.id.leg2);

        buttonNewGame = (Button)findViewById(R.id.buttonNewGame);
        buttonHint = (Button)findViewById(R.id.buttonHint);
        tvHint = (TextView)findViewById(R.id.tvHint);

        ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);

        playGame();
    }

    private void playGame(){
        //choose a word & hint
        randomNum = rand.nextInt(words.length);

        //make sure not same word as last time
        while(words[randomNum].equals(currWord)) {
            randomNum = rand.nextInt(words.length);
        }
        currWord = words[randomNum];

        //start new game
        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.playGame();
            }
        });

        //show hint
        buttonHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvHint.setText(hints[randomNum]);
                buttonHint.setEnabled(false);
            }
        });

        //create new array for character text views
        charViews = new TextView[currWord.length()];

        //remove any existing letters
        wordLayout.removeAllViews();

        //loop through characters
        for(int c=0; c<currWord.length(); c++){
            charViews[c] = new TextView(this);
            //set the current letter
            charViews[c].setText(""+currWord.charAt(c));
            //set layout
            charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setTextColor(Color.WHITE);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);
            //add to display
            wordLayout.addView(charViews[c]);
        }

        //reset adapter
        ltrAdapt=new LetterAdapter(this);
        letters.setAdapter(ltrAdapt);

        //start part at zero
        currPart=0;
        //set word length and correct choices
        numChars=currWord.length();
        numCorr=0;

        //hide all parts
        for(int p=0; p<numParts; p++){
            bodyParts[p].setVisibility(View.INVISIBLE);
        }

        score = 0;
        buttonHint.setEnabled(true);
        tvHint.setText("");
    }

    //letter pressed method
    public void letterPressed(View view){
        //find out which letter was pressed
        String ltr=((TextView)view).getText().toString();
        char letterChar = ltr.charAt(0);
        //disable view
        view.setEnabled(false);
        view.setBackgroundResource(R.drawable.letter_down);
        //check if correct
        boolean correct=false;
        for(int k=0; k<currWord.length(); k++){
            if(currWord.charAt(k)==letterChar){
                correct=true;
                numCorr++;
                if (letterChar == 'A' || letterChar == 'E' || letterChar == 'I' || letterChar == 'O' || letterChar == 'U'){
                    score += 5;
                }
                else{
                    score += 2;
                }
                charViews[k].setTextColor(Color.BLACK);
            }
        }

        if(correct){
            //user win
            if(numCorr == numChars){
                disableBtns();
                displayToast(true, currWord, score);
            }
        }
        //user still has guesses
        else if(currPart < numParts){
            //show next part
            bodyParts[currPart].setVisibility(View.VISIBLE);
            currPart++;
        }
        //user lose
        else{
            disableBtns();
            displayToast(false, currWord, score);
        }
    }

    //disable letter buttons
    public void disableBtns(){
        int numLetters = letters.getChildCount();
        for(int l=0; l<numLetters; l++){
            letters.getChildAt(l).setEnabled(false);
        }
    }

    //display toast when game ends
    public void displayToast(boolean correct, String answer, int score) {
        String winMessage = "You win. The answer is: %s. Your score is %d.";
        String loseMessage = "You lose. The answer is: %s. Your score is %d.";
        if (correct){
            toast = Toast.makeText(getApplicationContext(), String.format(winMessage, answer, score), Toast.LENGTH_LONG);
            toast.show();
        }
        else{
            toast = Toast.makeText(getApplicationContext(), String.format(loseMessage, answer, score), Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("score", score);
        outState.putInt("randomNum", randomNum);
        outState.putBoolean("hintEnable", buttonHint.isEnabled());
        outState.putString("currWord", currWord);
        outState.putInt("currPart", currPart);
        outState.putInt("numChars", numChars); //num chars in word
        outState.putInt("numCorr", numCorr); //num correct so far

        boolean[] visibleBodyParts = new boolean[numParts]; //default to false
        for (int i=0;i<numParts;i++){
            if (bodyParts[i].getVisibility() == View.VISIBLE) {visibleBodyParts[i] = true;}
            else if (bodyParts[i].getVisibility() == View.INVISIBLE) {visibleBodyParts[i] = false;}
        }
        outState.putSerializable("visibleBodyParts", visibleBodyParts);

        boolean[] visibleTextview = new boolean[currWord.length()]; //default to false
        for (int i=0;i<currWord.length();i++){
            if (charViews[i].getCurrentTextColor() == Color.BLACK) {visibleTextview[i] = true;}
            else if (charViews[i].getCurrentTextColor() == Color.WHITE) {visibleTextview[i] = false;}
        }
        outState.putSerializable("visibleTextview", visibleTextview);

        boolean[] visibleLetters = new boolean[letters.getChildCount()]; //default to false
        for (int i=0;i<letters.getChildCount();i++){
            if (letters.getChildAt(i).isEnabled()){visibleLetters[i] = true;}
            else {visibleLetters[i] = false;}
        }
        outState.putSerializable("visibleLetters", visibleLetters);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        randomNum = savedInstanceState.getInt("randomNum");
        currWord = words[randomNum];
        //Log.i("debug", currWord);
        buttonHint.setEnabled(savedInstanceState.getBoolean("hintEnable"));
        if (!savedInstanceState.getBoolean("hintEnable")) {
            tvHint.setText(hints[randomNum]);
        }

        score = savedInstanceState.getInt("score");
        currWord = savedInstanceState.getString("currWord");
        currPart = savedInstanceState.getInt("currPart");
        numChars = savedInstanceState.getInt("numChars");
        numCorr = savedInstanceState.getInt("numCorr");

        boolean[] visibleBodyParts = (boolean[]) savedInstanceState.getSerializable("visibleBodyParts");
        for (int i = 0; i < numParts; i++) {
            if (visibleBodyParts[i]) {
                bodyParts[i].setVisibility(View.VISIBLE);
            } else {
                bodyParts[i].setVisibility(View.INVISIBLE);
            }
        }

        boolean[] visibleTextview = (boolean[]) savedInstanceState.getSerializable("visibleTextview");
        wordLayout.removeAllViews();
        charViews = new TextView[currWord.length()];
        for (int c = 0; c < visibleTextview.length; c++) {
            charViews[c] = new TextView(this);
            //set the current letter
            charViews[c].setText("" + currWord.charAt(c));
            //set layout
            charViews[c].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            charViews[c].setGravity(Gravity.CENTER);
            charViews[c].setBackgroundResource(R.drawable.letter_bg);

            if (visibleTextview[c]) {
                charViews[c].setTextColor(Color.BLACK);
            } else {
                charViews[c].setTextColor(Color.WHITE);
            }

            //add to display
            wordLayout.addView(charViews[c]);
        }

        boolean[] visibleLetters = (boolean[]) savedInstanceState.getSerializable("visibleLetters");
        for(int i=0; i<letters.getChildCount(); i++){
            if (!visibleLetters[i]) {letters.getChildAt(i).setEnabled(false);}
        }
    }


}
