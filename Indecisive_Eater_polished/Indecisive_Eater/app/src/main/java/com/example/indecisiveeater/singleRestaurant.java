package com.example.indecisiveeater;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class singleRestaurant extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Semaphore reserve_semaphore;

    private Button bttn_menu;
    private TextView lbl_name;
    private RatingBar restaurant_rating;
    private ImageView img_restaurant;
    private TextView tv_priceRange;
    private TextView lbl_categories;
    private TextView tv_categories;
    private TextView tv_reviewCount;
    private TextView tv_distance;
    private TextView lbl_reviews;
    private TextView tv_review1;
    private TextView tv_review2;
    private TextView tv_review3;
    private Button bttn_reserve;
    private Button bttn_call;
    private Button bttn_share;
    private Button bttn_directions;
    private Button bttn_calendar;
    private Button bttn_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singlerestaurant);

        mAuth = FirebaseAuth.getInstance();

        reserve_semaphore = new Semaphore(0);

        bttn_menu = findViewById(R.id.bttn_menu);
        lbl_name = findViewById(R.id.lbl_name);
        restaurant_rating = findViewById(R.id.restaurant_rating);
        img_restaurant = findViewById(R.id.img_restaurant);
        tv_priceRange = findViewById(R.id.tv_priceRange);
        lbl_categories = findViewById(R.id.lbl_categories);
        tv_categories = findViewById(R.id.tv_categories);
        tv_distance = findViewById(R.id.tv_distance);
        tv_reviewCount = findViewById(R.id.tv_reviewCount);
        lbl_reviews = findViewById(R.id.lbl_reviews);
        tv_review1 = findViewById(R.id.tv_review1);
        tv_review2 = findViewById(R.id.tv_review2);
        tv_review3 = findViewById(R.id.tv_review3);
        bttn_calendar = findViewById(R.id.bttn_calendar);
        bttn_call = findViewById(R.id.bttn_call);
        bttn_directions = findViewById(R.id.bttn_directions);
        bttn_reserve = findViewById(R.id.bttn_reserve);
        bttn_share = findViewById(R.id.bttn_share);
        bttn_back = findViewById(R.id.bttn_back);

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        final Restaurant select_restaurant = (Restaurant) bundle.getSerializable("selected");
        displayRestaurant(select_restaurant);

        //sets the menu button
        bttn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(singleRestaurant.this, menu.class);
                startActivity(intent_menu);
            }
        });

        bttn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String website = select_restaurant.getWebsite();
                Uri uri = Uri.parse(website);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        //gets directions
        bttn_directions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = select_restaurant.getAddress().toString();
                String formatted_address = address.replace(' ','+');
                formatted_address = formatted_address.replace("[", "");
                formatted_address = formatted_address.replace("]", "");
                Intent geoLocationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + formatted_address));
                startActivity(geoLocationIntent);
            }
        });

        //calls the restaurant
        bttn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = select_restaurant.getPhone();
                if(phone == null){
                    Toast.makeText(getBaseContext(), "No phone number on file.", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent intent_call = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                    startActivity(intent_call);
                }
            }
        });

        //texts friends about plans
        bttn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = select_restaurant.getName();
                String message = "Hi! Please join me at " + name + " today.";

                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.putExtra("sms_body", message);
                sendIntent.setType("vnd.android-dir/mms-sms");
                startActivity(sendIntent);
            }
        });

        //add to calendar
        bttn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = select_restaurant.getAddress().toString();
                address = address.replace("[", "");
                address = address.replace("]", "");
                Intent intent_calendar = new Intent(Intent.ACTION_INSERT);
                intent_calendar.setData(CalendarContract.Events.CONTENT_URI);
                intent_calendar.putExtra(CalendarContract.Events.EVENT_LOCATION, address);
                startActivity(intent_calendar);
            }
        });

        //go back to search
        bttn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_back = new Intent(singleRestaurant.this, swipe.class);
                intent_back.putExtra("type", "back");
                startActivity(intent_back);
            }
        });

    }

    public void displayRestaurant(Restaurant restaurant){
        //gets the info
        String name = restaurant.getName();
        float rating = (float) restaurant.getRating();
        String price = restaurant.getPrice();
        int reviewCount = restaurant.getReviewCount();
        double distance = restaurant.getDistance();
        distance = distance / 1609;
        DecimalFormat df = new DecimalFormat("#.##");
        String miles = df.format(distance);
        ArrayList<String> catList = restaurant.getCategories();
        String categories = "";
        for(String x : catList){
            categories = categories + x + ", ";
        }

        //sets the UI
        lbl_name.setText(name);
        restaurant_rating.setRating(rating);
        Glide.with(this).load(restaurant.getImageUrl()).into(img_restaurant);
        tv_distance.setText("Distance: " + miles);
        tv_priceRange.setText("Price Range: " + price);
        tv_reviewCount.setText("Number of Reviews: " + reviewCount);
        tv_categories.setText(categories);

        callReviews(restaurant.getId());
    }
    public void callReviews(final String restaurantID){
        final YelpReviews yelpReviews = new YelpReviews();
        yelpReviews.getReviews(restaurantID, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.err.println("kristi yelpReview error" + e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final ArrayList<String> review_list = yelpReviews.processReviews(response);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(review_list.get(0) != null){
                            tv_review1.setText(review_list.get(0));
                        }
                        else{
                            tv_review1.setText("--");
                        }
                        if(review_list.get(1) != null){
                            tv_review2.setText(review_list.get(1));
                        }
                        else{
                            tv_review2.setText("--");
                        }
                        if(review_list.get(2) != null){
                            tv_review3.setText(review_list.get(2));
                        }
                        else{
                            tv_review3.setText("--");
                        }
                    }
                });
            }
        });
    }
}
