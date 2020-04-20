package com.example.indecisiveeater;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class swipe extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private GestureDetector GD;

    private Button bttn_menu;
    private TextView lbl_no;
    private TextView tv_no;
    private TextView lbl_remaining;
    private TextView tv_remain;
    private TextView lbl_yes;
    private TextView tv_yes;
    private TextView lbl_name;
    private RatingBar restaurant_rating;
    private ImageView img_restaurant;
    private TextView tv_distance;
    private TextView tv_priceRange;
    private TextView lbl_cuisines;
    private TextView tv_cuisines;
    private TextView lbl_reviews;
    private TextView tv_review1;
    private TextView tv_review2;
    private TextView tv_review3;
    private ImageButton bttn_yes;
    private ImageButton bttn_no;
    private ImageButton bttn_star;
    private TextView lbl_restrictions;
    private TextView tv_dietary;

    public List<String> yesList = new ArrayList<>();
    public List<String> noList = new ArrayList<>();
    public List<String> restaurantList = new ArrayList<>();

    public int restaurantIndex;
    public String curr_businessID;
    public int num_no = 0;
    public int num_yes = 0;
    public int num_remain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_screen);

        mAuth = FirebaseAuth.getInstance();
        GD = new GestureDetector(this, new MyGestureListener());

        bttn_menu = findViewById(R.id.bttn_menu);
        lbl_no = findViewById(R.id.lbl_no);
        tv_no = findViewById(R.id.tv_no);
        lbl_remaining = findViewById(R.id.lbl_remaining);
        tv_remain = findViewById(R.id.tv_remain);
        lbl_yes = findViewById(R.id.lbl_yes);
        tv_yes = findViewById(R.id.tv_yes);
        lbl_name = findViewById(R.id.lbl_name);
        restaurant_rating = findViewById(R.id.restaurant_rating);
        img_restaurant = findViewById(R.id.img_restaurant);
        tv_distance = findViewById(R.id.tv_distance);
        tv_priceRange = findViewById(R.id.tv_priceRange);
        lbl_cuisines = findViewById(R.id.lbl_cuisines);
        tv_cuisines = findViewById(R.id.tv_cuisines);
        lbl_reviews = findViewById(R.id.lbl_reviews);
        tv_review1 = findViewById(R.id.tv_review1);
        tv_review2 = findViewById(R.id.tv_review2);
        tv_review3 = findViewById(R.id.tv_review3);
        bttn_yes = findViewById(R.id.bttn_yes);
        bttn_no = findViewById(R.id.bttn_no);
        bttn_star = findViewById(R.id.bttn_star);
        lbl_restrictions = findViewById(R.id.lbl_restrictions);
        tv_dietary = findViewById(R.id.tv_dietary);

        //sets the restaurant list array to contain the business_ids
        Intent intent = getIntent();
        int type = intent.getIntExtra("type",0);
        //checks to see if it coming from the "liked" button (type = 1) or the new search button (type =0)
        if(type == 0) {
            //TODO: set the number_results based on the Yelp API call
            //num_remain =;
            if (num_remain == 0) {
                Intent intent_none = new Intent(swipe.this, NoResults.class);
                startActivity(intent_none);
            } else {
                //adds the restaurants to the restaurantList
                tv_remain.setText(num_remain);
                for (int index = 0; index < num_remain + 1; index++) {
                    restaurantList.add(businesses[index].id);
                }
            }
        }
        //liked button
        else if(type == 1){
            restaurantList.clear();
            noList.clear();
            num_no = 0;
            num_yes = 0;
            tv_yes.setText("--");
            tv_no.setText("--");
            num_remain = yesList.size();
            if(num_remain == 0){
                Intent intent_none = new Intent(swipe.this, NoResults.class);
                startActivity(intent_none);
            }
            else {
                tv_remain.setText(num_remain);
                for (String restaurant_id : yesList) {
                    restaurantList.add(restaurant_id);
                }
                yesList.clear();
            }
        }
        else{
            Toast.makeText(getBaseContext(), "Error. Please Try Again.", Toast.LENGTH_LONG).show();
        }

        //displays the first restaurant of the results
        restaurantIndex = 0;
        curr_businessID = restaurantList.get(restaurantIndex);
        displayRestaurant(curr_businessID);

        //sets the buttons
        bttn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(swipe.this, menu.class);
                startActivity(intent_menu);
            }
        });

        bttn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectYes(curr_businessID);
            }
        });

        bttn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectNo(curr_businessID);
            }
        });

        bttn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectStar(curr_businessID);
            }
        });
    }

    //sets the UI to the given restaurant
    public void displayRestaurant(String business_ID){
        //TODO: takes the info from the Yelp API for business ID call & sets the views
        //call the Yelp API and return info based on business id

        lbl_name.setText(name);
        restaurant_rating.setRating(rating);
        tv_priceRange.setText("Price Range: " + price);
        //TODO: set the images of the business (returns up to 3)
        //TODO: figure out how to list cuisines
        tv_cuisines.setText("Cuisine: " + cuisines);
        //TODO: figure out how to set dietary restriction
        float distance_meters = businesses[restaurantIndex].distance;
        float distance_miles = distance_meters / 1609;
        tv_distance.setText("Distance: " + distance_miles);

        //Get the reviews for the business
        //TODO: call the Yelp Reviews API
            if(review[0] != null){
                tv_review1.setText(review[0].text);
            }
            else{
                tv_review1.setText("--");
            }
            if(review[1] != null){
                tv_review2.setText(review[1].text);
            }
            else{
                tv_review2.setText("--");
            }
            if(review[1] != null){
                tv_review3.setText(review[2].text);
            }
            else{
                tv_review3.setText("--");
            }
    }

    //adds the restaurant to the "yes" list
    public void selectYes(String business_id){
        yesList.add(business_id);

        num_yes++;
        tv_yes.setText(num_yes);

        num_remain--;
        tv_remain.setText(num_remain);

        restaurantIndex++;
        if(restaurantIndex > restaurantList.size() - 1){
            //go to the new intent
        }
        else{
            curr_businessID = restaurantList.get(restaurantIndex);
            displayRestaurant(curr_businessID);
        }
    }

    //adds the restaurant to the "no" list
    public void selectNo(String business_id){
        noList.add(business_id);

        num_no++;
        tv_no.setText(num_no);

        num_remain--;
        tv_remain.setText(num_remain);

        restaurantIndex++;
        if(restaurantIndex > restaurantList.size() - 1){
            //go to the new intent
        }
        else{
            curr_businessID = restaurantList.get(restaurantIndex);
            displayRestaurant(curr_businessID);
        }

    }

    //user selected the restaurant (swipeUp or star)
    public void selectStar(String business_id){
        //TODO: make new intent
        //go to the new intent
    }

    //gesturedetector for swiping yes, no, star
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.GD.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            int min = 120;

            float x1 = e1.getX();
            float x2 = e2.getX();
            float y1 = e1.getY();
            float y2 = e2.getY();

            float x_distance = Math.abs(x1 - x2);
            float y_distance = Math.abs(y1 - y2);

            if (y_distance < min && x_distance > min) {
                //swipe right (yes)
                if (x1 < x2) {
                    selectYes(curr_businessID);
                    return true;
                }
                //swipe left (no)
                else {
                    selectNo(curr_businessID);
                    return true;
                }
            } else {
                //swipe up (star)
                if (y1 > y2) {
                    selectStar(curr_businessID);
                }
                return true;
            }
        }
    }
}
