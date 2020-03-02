package com.example.sse.interfragmentcommratingbar;


import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrawableFragment extends Fragment {

    ArrayList<Drawable> drawables; //keeping track of our drawables

 //Boiler Plate Stuff.
    private ImageView imgRateMe;
    private RatingBar ratingBar;
    private float starArray[];
    private int drawableIndex;

//    public DrawableFragment() {
//        // Required empty public constructor
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_drawable, container, false);  //comment this out, it would return the default view, without our setup/amendments.
        View v = inflater.inflate(R.layout.fragment_drawable, container, false);   //MUST HAPPEN FIRST, otherwise components don't exist.

        ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        imgRateMe = (ImageView) v.findViewById(R.id.imgRateMe);
        getDrawables();         //Retrieves the drawables we want, ie, prefixed with "animal_"

        //sets the drawing to the first image
        imgRateMe.setImageDrawable(drawables.get(0));

        //set up array of ratings
        starArray = new float[drawables.size()];
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                float rating = ratingBar.getRating();
                starArray[drawableIndex] = rating;
            }
        });



        return v;
    }

    public void setPicture(int index){
        imgRateMe.setImageDrawable(drawables.get(index));
        drawableIndex = index;
        ratingBar.setRating(starArray[index]);
    }


//Quick and Dirty way to get drawable resources, we prefix with "animal_" to filter out just the ones we want to display.
//REF: http://stackoverflow.com/questions/31921927/how-to-get-all-drawable-resources
    public void getDrawables() {
        Field[] drawablesFields = com.example.sse.interfragmentcommratingbar.R.drawable.class.getFields();  //getting array of ALL drawables.
        drawables = new ArrayList<>();  //we prefer an ArrayList, to store the drawables we are interested in.  Why ArrayList and not an Array here? A: _________

        String fieldName;
        for (Field field : drawablesFields) {   //1. Looping over the Array of All Drawables...
            try {
                fieldName = field.getName();    //2. Identifying the Drawables Name, eg, "animal_bewildered_monkey"
                Log.i("LOG_TAG", "com.your.project.R.drawable." + fieldName);
                if (fieldName.startsWith("animals_"))  //3. Adding drawable resources that have our prefix, specifically "animal_".
                    drawables.add(getResources().getDrawable(field.getInt(null)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}