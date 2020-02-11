package com.example.c2_p29;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView lblChicken, lblBeef, lblEgg, lblSbeans, lblBroccoli,
            lblTofu, lblRice, lblPotato, lblOrange, lblBanana,
            lblTotal, TotalInt;

    private EditText inputChicken, inputBeef, inputEgg, inputSbeans, inputBroccoli,
            inputTofu, inputRice, inputPotato, inputOrange, inputBanana;

    public float chicken, beef, egg, sbeans, broccoli, tofu, rice, potato, orange, banana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        lblChicken = (TextView) findViewById(R.id.lblChicken);
        lblBeef = (TextView) findViewById(R.id.lblBeef);
        lblEgg = (TextView) findViewById(R.id.lblEgg);
        lblSbeans = (TextView) findViewById(R.id.lblSbeans);
        lblBroccoli = (TextView) findViewById(R.id.lblBroccoli);
        lblTofu = (TextView) findViewById(R.id.lblTofu);
        lblPotato = (TextView) findViewById(R.id.lblPotato);
        lblRice = (TextView) findViewById(R.id.lblRice);
        lblOrange = (TextView) findViewById(R.id.lblOrange);
        lblBanana = (TextView) findViewById(R.id.lblBanana);
        lblTotal = (TextView) findViewById(R.id.lblTotal);
        TotalInt = (TextView) findViewById(R.id.TotalInt);
        inputChicken = (EditText) findViewById(R.id.inputChicken);
        inputBeef = (EditText) findViewById(R.id.inputBeef);
        inputEgg = (EditText) findViewById(R.id.inputEgg);
        inputSbeans = (EditText) findViewById(R.id.inputSbeans);
        inputBroccoli = (EditText) findViewById(R.id.inputBroccoli);
        inputTofu = (EditText) findViewById(R.id.inputTofu);
        inputPotato = (EditText) findViewById(R.id.inputPotato);
        inputRice = (EditText) findViewById(R.id.inputRice);
        inputOrange = (EditText) findViewById(R.id.inputOrange);
        inputBanana = (EditText) findViewById(R.id.inputBanana);

        chicken = 239;
        beef = 250;
        egg = 155;
        sbeans = 31;
        broccoli = 34;
        tofu = 76;
        potato = 77;
        rice = 151;
        banana = 89;
        orange = 47;

        TextChangeHandler tch = new TextChangeHandler();

        inputChicken.addTextChangedListener(tch);
        inputBeef.addTextChangedListener(tch);
        inputEgg.addTextChangedListener(tch);
        inputSbeans.addTextChangedListener(tch);
        inputBroccoli.addTextChangedListener(tch);
        inputTofu.addTextChangedListener(tch);
        inputPotato.addTextChangedListener(tch);
        inputRice.addTextChangedListener(tch);
        inputBanana.addTextChangedListener(tch);
        inputOrange.addTextChangedListener(tch);
    }

    public void calculate(){
        float amountChicken;
        if(inputChicken == null || inputChicken.getText().toString().equals("") ){
            amountChicken = 0;
        }
        else {
            amountChicken = Float.parseFloat(inputChicken.getText().toString());
        }

        float amountBeef;
        if(inputBeef == null || inputBeef.getText().toString().equals("") ){
            amountBeef = 0;
        }
        else {
            amountBeef = Float.parseFloat(inputBeef.getText().toString());
        }

        float amountEgg;
        if(inputEgg == null || inputEgg.getText().toString().equals("") ){
            amountEgg = 0;
        }
        else {
            amountEgg = Float.parseFloat(inputEgg.getText().toString());
        }

        float amountSbeans;
        if(inputSbeans == null || inputSbeans.getText().toString().equals("") ){
            amountSbeans = 0;
        }
        else {
            amountSbeans = Float.parseFloat(inputSbeans.getText().toString());
        }

        float amountBroccoli;
        if(inputBroccoli == null || inputBroccoli.getText().toString().equals("") ){
            amountBroccoli = 0;
        }
        else {
            amountBroccoli = Float.parseFloat(inputBroccoli.getText().toString());
        }

        float amountTofu;
        if(inputTofu == null || inputTofu.getText().toString().equals("") ){
            amountTofu = 0;
        }
        else {
            amountTofu = Float.parseFloat(inputTofu.getText().toString());
        }

        float amountPotato;
        if(inputPotato == null || inputPotato.getText().toString().equals("") ){
            amountPotato = 0;
        }
        else {
            amountPotato = Float.parseFloat(inputPotato.getText().toString());
        }

        float amountRice;
        if(inputRice == null || inputRice.getText().toString().equals("") ){
            amountRice = 0;
        }
        else {
            amountRice = Float.parseFloat(inputRice.getText().toString());
        }

        float amountBanana;
        if(inputBanana == null || inputBanana.getText().toString().equals("") ){
            amountBanana = 0;
        }
        else {
            amountBanana = Float.parseFloat(inputBanana.getText().toString());
        }

        float amountOrange;
        if(inputOrange == null || inputOrange.getText().toString().equals("") ){
            amountOrange = 0;
        }
        else {
            amountOrange = Float.parseFloat(inputOrange.getText().toString());
        }

        float totalCalories = (amountChicken * chicken) + (amountBeef * beef) + (amountEgg * egg) + (amountSbeans * sbeans) + (amountBroccoli * broccoli)
                + (amountTofu * tofu) + (amountPotato * potato) + (amountRice * rice) + (amountBanana * banana) + (amountOrange * orange);
        TotalInt.setText(totalCalories + " calories");
    }

    private class TextChangeHandler implements TextWatcher {
        public void afterTextChanged(Editable e) {
            calculate();
        }

        public void beforeTextChanged( CharSequence s, int start, int count, int after){

        }

        public void onTextChanged(CharSequence s, int start, int before, int after){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
