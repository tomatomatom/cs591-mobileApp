package com.example.sse.fragmenttransactions_sse;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity{

//Step-By-Step, Fragment Transactions

    //Two basic ways of working with fragments.
    //
    //1. Just include them in the Activity's layout.
    //
    //2. Instantatiate and work with them in code.
    // in code you have much more control.

    //3. create objects to reference the views, including fragments.
private
    Frag_One  f1;
    Frag_Two  f2;
    Frag_Three  f3;

    FragmentManager fm;  // we will need this later.

    private Button btnFrag1;
    private Button btnFrag2;
    private Button btnFrag3;
    private LinearLayout FragLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    //4. get references for our views.

        btnFrag1 = (Button) findViewById(R.id.btnFrag1);
        btnFrag2 = (Button) findViewById(R.id.btnFrag2);
        btnFrag3 = (Button) findViewById(R.id.btnFrag3);
        FragLayout = (LinearLayout) findViewById(R.id.FragLayout);

        //f1 = (Frag_One) findViewById(R.id.frag1);  //Q: Why won't this work for fragments?  Does the fragment even exist in R.java? _____________

    //5a.  We actually have to create the fragments ourselves.  We left R behind when we took control of rendering.
        f1 = new Frag_One();
        f2 = new Frag_Two();
        f3 = new Frag_Three();

    //5b. Grab a reference to the Activity's Fragment Manager, Every Activity has one!
       fm = getFragmentManager ();  //that was easy.
//         fm = getSupportFragmentManager();  // **When would you use this instead?? A: __________________


    //5c. Now we can "plop" fragment(s) into our container.
        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.
        ft.add(R.id.FragLayout, f1, "tag1");  //now we have added our fragement to our Activity programmatically.  The other fragments exist, but have not been added yet.
        ft.addToBackStack ("myFrag1");  //why do we do this?
        ft.commit ();  //don't forget to commit your changes.  It is a transaction after all.

        btnFrag1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showFrag1();
        }
    });

        btnFrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag2();
            }
        });

        btnFrag3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFrag3();
            }
        });

    }

public void showFrag1() {
    FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction.

    //Check if f1 exists
    f1 = (Frag_One) fm.findFragmentByTag("tag1");
    if (f1 == null) {f1 = new Frag_One();}

    //remove others and add f1
    ft.replace(R.id.FragLayout, f1, "tag1");
    ft.attach(f1);
    ft.commit();
}

    public void showFrag2() {
        f2 = (Frag_Two) fm.findFragmentByTag("tag2");
        if (f2 == null) {f2 = new Frag_Two();}

        FragmentTransaction ft = fm.beginTransaction ();  //Create a reference to a fragment transaction and start the transaction.
        ft.replace(R.id.FragLayout, f2, "tag2"); //remove previous fragment and add f2
        ft.addToBackStack ("myFrag2");  //Q: What is the back stack and why do we do this? A: Using back stack we can retrice the previous state
        ft.attach(f2);
        ft.commit();
    }


    public void showFrag3() {
        //check if f3 exists
        f3 = (Frag_Three) fm.findFragmentByTag("tag3");
        if (f3 == null) {f3 = new Frag_Three();}

        FragmentTransaction ft = fm.beginTransaction ();
        //remove other fragments and add f3 by tag
        ft.replace(R.id.FragLayout, f3, "tag3");
        ft.commit();
    }
}
