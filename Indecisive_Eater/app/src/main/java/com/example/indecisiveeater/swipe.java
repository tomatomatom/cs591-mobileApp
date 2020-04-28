package com.example.indecisiveeater;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Semaphore;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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
    private TextView lbl_categories;
    private TextView tv_categories;
    private TextView tv_reviewCount;
    private TextView lbl_reviews;
    private TextView tv_review1;
    private TextView tv_review2;
    private TextView tv_review3;
    private ImageButton bttn_yes;
    private ImageButton bttn_no;
    private ImageButton bttn_star;
    private Button bttn_end;

    public int num_remain;
    public int restaurantIndex;
    public int num_yes;
    public int num_no;

    private Semaphore liked_semaphore;
    private Semaphore load_semaphore;
    private Semaphore api_semaphore;
    private Semaphore review_semaphore;

//    public ArrayList<String> restaurantList = new ArrayList<>();
//    public ArrayList<String> noList = new ArrayList<>();
//    public ArrayList<String> yesList = new ArrayList<>();


    public List<Restaurant> restaurantList = new ArrayList<>();
    public List<Restaurant> yesList = new ArrayList<>();
    public List<Restaurant> noList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_screen);

        mAuth = FirebaseAuth.getInstance();
//        GD = new GestureDetector(this, new MyGestureListener());

        liked_semaphore = new Semaphore(0);
        load_semaphore = new Semaphore(0);
        api_semaphore = new Semaphore(0);
        review_semaphore = new Semaphore(0);

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
        lbl_categories = findViewById(R.id.lbl_categories);
        tv_categories = findViewById(R.id.tv_categories);
        tv_reviewCount = findViewById(R.id.tv_reviewCount);
        lbl_reviews = findViewById(R.id.lbl_reviews);
        tv_review1 = findViewById(R.id.tv_review1);
        tv_review2 = findViewById(R.id.tv_review2);
        tv_review3 = findViewById(R.id.tv_review3);
        bttn_yes = findViewById(R.id.bttn_yes);
        bttn_no = findViewById(R.id.bttn_no);
        bttn_star = findViewById(R.id.bttn_star);
        bttn_end = findViewById(R.id.bttn_end);

        bttn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_menu = new Intent(swipe.this, menu.class);
                startActivity(intent_menu);
            }
        });

        bttn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_end = new Intent(swipe.this, EndResults.class);
                startActivity(intent_end);
            }
        });

        bttn_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant curr_Restaurant = restaurantList.get(restaurantIndex);
                selectYes(curr_Restaurant);
            }
        });

        bttn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant curr_Restaurant = restaurantList.get(restaurantIndex);
                selectNo(curr_Restaurant);
            }
        });

        bttn_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Restaurant curr_Restaurant = restaurantList.get(restaurantIndex);
                selectStar(curr_Restaurant);
            }
        });

        //getting the type of call to swipe where
        // type options are ["liked", "searchAgain", "newSearch", "back"]
        //TODO: searchAgain from liked list
        Intent i = getIntent();
        String type = i.getStringExtra("type");
        if (type.equals("newSearch") || type.equals("searchAgain")) {
            searchYelpAPI();
        } else if (type.equals("liked")) {
            swipeYesList();
        }
        else{
            loadPrevious();
        }
    }

    public void searchYelpAPI() {
        //calls the Yelp API to search
        //stsarts a new thread to run the database call
        new Thread(new Runnable() {
            public void run() {
                final YelpService yelpService = new YelpService();
                System.err.println("kristi before findRestaurants");
                yelpService.findRestaurants(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.err.println("kristi + yelpService error");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        System.err.println("kristi calling the YelpService");
                        restaurantList = yelpService.processResults(response);
                        System.err.println("kristi onResponse of YelpService size" + restaurantList.size());
                        System.err.println("kristi release api_semaphore");
                        api_semaphore.release();
                    }
                });
            }
        }).start();

        //new thread to set the restaurantList and the number counts
        new Thread(new Runnable() {
            public void run() {
                try {
                    System.err.println("kristi api_acquire");
                    api_semaphore.acquire();

                    System.err.println("kristi afterYelpService call size" + restaurantList.size());
                    updateDatabase(restaurantList, "restaurant_list");
                    updateDatabase(noList, "no_list");
                    updateDatabase(yesList, "yes_list");
                    System.err.println("kristi after searchUpdate");
                    num_remain = restaurantList.size();
                    num_yes = 0;
                    num_no = 0;
                    restaurantIndex = 0;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            continueActivity();
                        }
                    });
                } catch (InterruptedException e) {
                    System.err.println("kristi exception " + e.toString());
                }
            }
        }).start();
    }

    //displays the restaurant on the UI
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
        tv_distance.setText("Distance: " + miles + " miles");
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


    public void updateDatabase(List<Restaurant> list, String list_type){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference root = database.getReference();
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        root.child("selection_lists").child(userID).child(list_type).removeValue();

        for(int index = 0; index < list.size(); index++){
            String dataIndex = "indexNum" + index;
            Restaurant restaurant = list.get(index);
            root.child("selection_lists").child(userID).child(list_type).child(dataIndex).setValue(restaurant);
        }
    }

    public void swipeYesList() {
        //gets the yes_list from the database
        //starts a new thread to run the database call
        new Thread(new Runnable() { public void run() {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference root = database.getReference();
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            //gets the data and puts it in the restaurantList array
            root.child("selection_lists").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int size = (int) dataSnapshot.child("yes_list").getChildrenCount();
                    for(int i = 0; i < size; i++){
                        Restaurant id = dataSnapshot.child("yes_list").child("indexNum"+i).getValue(Restaurant.class);
                        restaurantList.add(id);
                    }
                    System.err.println("kristi db release thread" + Thread.currentThread().getName());
                    liked_semaphore.release();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(swipe.this, "cancelled", Toast.LENGTH_SHORT).show();
                }
            });
        } }).start();

        //new thread to set arrays
        new Thread(new Runnable() { public void run() {
            try {
                //locks the method so that the app calls the database first
                System.err.println("kristi db acquire thread" + Thread.currentThread().getName());
                liked_semaphore.acquire();

                System.err.println("kristi updateDatabase" + Thread.currentThread().getName());
                updateDatabase(restaurantList, "restaurant_list");
                updateDatabase(yesList, "yes_list");
                updateDatabase(noList, "no_list");
                System.err.println("kristi after updateDatabase() " + restaurantList.size());
                num_remain = restaurantList.size();
                num_yes = 0;
                num_no = 0;
                restaurantIndex = 0;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        continueActivity();
                    }
                });
        } catch (InterruptedException e) {
            System.err.println("kristi"+ "exception" + e.toString());
        }} }) .start();
    }

    public void continueActivity(){
        System.err.println("kristi begin continueActivity num_remain" + num_remain);
        if(num_remain == 0){
            Intent intent_no = new Intent(swipe.this, NoResults.class);
            startActivity(intent_no);
        }
        else {
            tv_remain.setText(Integer.toString(num_remain));
            tv_yes.setText(Integer.toString(num_yes));
            tv_no.setText(Integer.toString(num_no));

            System.err.println("kristi continueActivity restaurant Index" + restaurantIndex);
            displayRestaurant(restaurantList.get(restaurantIndex));
        }
    }

    public void loadPrevious(){
        //gets the lists from the database
        //starts a new thread to run the database call
        new Thread(new Runnable() { public void run() {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference root = database.getReference();
            String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

            //gets the data and puts it in the restaurantList array
            root.child("selection_lists").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int yes_size = (int) dataSnapshot.child("yes_list").getChildrenCount();
                    for(int i = 0; i < yes_size; i++){
                        Restaurant id = dataSnapshot.child("yes_list").child("indexNum"+i).getValue(Restaurant.class);
                        yesList.add(id);
                    }
                    int restaurant_size = (int) dataSnapshot.child("restaurant_list").getChildrenCount();
                    for(int i =0; i < restaurant_size; i++){
                        Restaurant id = dataSnapshot.child("restaurant_list").child("indexNum"+ i).getValue(Restaurant.class);
                        restaurantList.add(id);
                    }
                    int no_size = (int) dataSnapshot.child("no_list").getChildrenCount();
                    for(int i =0; i < no_size; i++){
                        Restaurant id = dataSnapshot.child("no_list").child("indexNum"+i).getValue(Restaurant.class);
                        noList.add(id);
                    }

                    load_semaphore.release();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(swipe.this, "cancelled", Toast.LENGTH_SHORT).show();
                }
            });

        } }).start();

        //new thread to set arrays
        new Thread(new Runnable() { public void run() {
            try {
                //locks the method so that the app calls the database first
                load_semaphore.acquire();
                System.err.println("kristi restaurantIndex" + restaurantIndex);
                num_no = noList.size();
                System.err.println("kristi no" + num_no);
                num_yes = yesList.size();
                System.err.println("kristi yes" + num_yes);
                num_remain = restaurantList.size() - (num_no + num_yes);
                System.err.println("kristi num_remain" + num_remain);
                restaurantIndex = restaurantList.size() - num_remain;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        continueActivity();
                    }
                });
            } catch (InterruptedException e) {
                System.err.println("kristi"+ "exception" + e.toString());
            }} }) .start();

    }

    public void selectYes(Restaurant restaurant){
        //add restaurant to yes list
        yesList.add(restaurant);

        //change the numbers accordingly
        num_remain--;
        tv_remain.setText(Integer.toString(num_remain));
        num_yes++;
        tv_yes.setText(Integer.toString(num_yes));

        //save the yes list to the database
          updateDatabase(yesList, "yes_list");

        //checks if there are any more restaurants and displays it or goes to end result activity

        System.err.println("kristi before restaurantIndex" + restaurantIndex);
        restaurantIndex++;
        System.err.println("kristi after restaurantIndex" + restaurantIndex);
        System.err.println("kristi restaurantList" + restaurantList.toString());
        if(restaurantIndex > restaurantList.size() - 1){
            Intent intent_end = new Intent(swipe.this, EndResults.class);
            startActivity(intent_end);
        }
        else {
            displayRestaurant(restaurantList.get(restaurantIndex));
        }
    }

    public void selectNo(Restaurant restaurant){
        //add restaurant to no list
        noList.add(restaurant);

        //change the numbers accordingly
        num_remain--;
        tv_remain.setText(Integer.toString(num_remain));
        num_no++;
        tv_no.setText(Integer.toString(num_no));

        //save the no list to the database
        updateDatabase(noList, "no_list");

        //checks if there are any more restaurants and displays it or goes to end result activity
        restaurantIndex++;
        if(restaurantIndex > restaurantList.size() - 1){
            Intent intent_end = new Intent(swipe.this, EndResults.class);
            startActivity(intent_end);
        }
        else {
            displayRestaurant(restaurantList.get(restaurantIndex));
        }
    }

    //goes to the single restaurant activity
    public void selectStar(Restaurant restaurant){
        Intent intent_single = new Intent(swipe.this, singleRestaurant.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("selected", restaurant);
        intent_single.putExtras(bundle);
        startActivity(intent_single);
    }


    //gestureDetector for swiping yes, no, star
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
            Restaurant curr_Restaurant = restaurantList.get(restaurantIndex);
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
                    selectYes(curr_Restaurant);
                    return true;
                }
                //swipe left (no)
                else {
                    selectNo(curr_Restaurant);
                    return true;
                }
            } else {
                //swipe up (star)
                if (y1 > y2) {
                    selectStar(curr_Restaurant);
                }
                return true;
            }
        }

    }
}

