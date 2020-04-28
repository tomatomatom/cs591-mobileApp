package com.example.indecisiveeater;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YelpService {
    private static Semaphore db_semaphore = new Semaphore(0);
    private static String location;
    private static String distance;
    private static String price;
    private static String open;
    private static String reserve;
    private static String categories;

    //pulls the criteria from the database
    public static void getCriteria() {
        new Thread(new Runnable() {
            public void run() {
                System.err.println("kristi start getCriteria");
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference root = database.getReference();

                root.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        System.err.println("kristi calling db now");
                        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        location = dataSnapshot.child("search_criteria").child(userID).child("location").getValue(String.class);
                        int miles = dataSnapshot.child("search_criteria").child(userID).child("distance").getValue(int.class);
                        distance = Integer.toString((miles * 1609));
                        price = dataSnapshot.child("search_criteria").child(userID).child("price").getValue(String.class);
                        price = price.replaceAll(",$", "");
                        boolean b1 = dataSnapshot.child("search_criteria").child(userID).child("open").getValue(boolean.class);
                        open = String.valueOf(b1);

                        String csv = dataSnapshot.child("search_criteria").child(userID).child("categories").getValue(String.class);

                        //dietary_restrictions
                        if (dataSnapshot.child("users").child(userID).child("vegetarian").exists() == true) {
                            boolean vegetarian = dataSnapshot.child("users").child(userID).child("vegetarian").getValue(boolean.class);
                            if (vegetarian == true) {
                                if(csv.equals("") == true){
                                    csv = csv + "vegetarian";
                                }
                                else {
                                    csv = csv + "," + "vegetarian";
                                }
                            }
                        }
                        if (dataSnapshot.child("users").child(userID).child("vegan").exists() == true) {
                            boolean vegan = dataSnapshot.child("users").child(userID).child("vegan").getValue(boolean.class);
                            if (vegan == true) {
                                if(csv.equals("") == true){
                                    csv = csv + "vegan";
                                }
                                else {
                                    csv = csv + "," + "vegan";
                                }
                            }
                        }
                        if (dataSnapshot.child("users").child(userID).child("gluten-free").exists() == true) {
                            boolean gluten = dataSnapshot.child("users").child(userID).child("gluten-free").getValue(boolean.class);
                            if (gluten == true) {
                                if (csv.equals("") == true) {
                                    csv = csv + "gluten_free";
                                } else {
                                    csv = csv + "," + "gluten_free";
                                }
                            }
                        }
                        categories = csv.toLowerCase();
                        categories = categories.replace(" ", "");

                        System.err.println("kristi + db_semaphore release");
                        db_semaphore.release();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        }).start();
    }

    //enters the search criteria into the Yelp API
    public static void findRestaurants(final Callback callback) {
        new Thread(new Runnable() {public void run() {
            try {
                getCriteria();
                System.err.println("kristi db_semaphore acquire");
                db_semaphore.acquire();

                System.err.println("kristi starting the Yelp API Call");
                OkHttpClient client = new OkHttpClient.Builder().build();
                HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
                urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
                urlBuilder.addQueryParameter(Constants.YELP_LIMIT_QUERY_PARAMETER, "50");
                urlBuilder.addQueryParameter(Constants.YELP_OFFSET_QUERY_PARAMETER, "10");
                urlBuilder.addQueryParameter(Constants.YELP_RADIUS_QUERY_PARAMETER, distance);
                if(price != null && price.equals("") == false){
                    System.err.println("kristi price " + price);
                    urlBuilder.addQueryParameter(Constants.YELP_PRICE_QUERY_PARAMETER, price);
                }
                if(open.equals("true")) {
                    urlBuilder.addQueryParameter(Constants.YELP_OPEN_QUERY_PARAMETER, open);
                }
                if(categories != null && categories.equals("") == false) {
                    urlBuilder.addQueryParameter(Constants.YELP_CATEGORIES_QUERY_PARAMETER, categories);
                }
                String url = urlBuilder.build().toString();

                Request request = new Request.Builder()
                        .url(url)
                        .header("Authorization", Constants.YELP_TOKEN)
                        .build();

                Call call = client.newCall(request);
                System.err.println("kristi query "+ request.toString());
                call.enqueue(callback);
                System.err.println("kristi finished calling API");
        } catch (InterruptedException e) {
            System.err.println("kristi error " + e.toString());
        }} }).start();
    }


    public ArrayList<Restaurant> processResults(Response response) {
        ArrayList<Restaurant> restaurants = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            JSONObject yelpJSON = new JSONObject(jsonData);
            JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
            int total = yelpJSON.getInt("total");
            System.err.println("kristi number of results " + total);
            for (int i = 0; i < businessesJSON.length(); i++) {
                JSONObject restaurantJSON = businessesJSON.getJSONObject(i);
                String id = restaurantJSON.getString("id");
                String name = restaurantJSON.getString("name");
                String phone = restaurantJSON.optString("display_phone", "Phone not available");
                String website = restaurantJSON.getString("url");
                double rating = restaurantJSON.getDouble("rating");
                String price = restaurantJSON.getString("price");
                int reviewCount = restaurantJSON.getInt("review_count");
                double distance = restaurantJSON.getDouble("distance");

                String imageUrl = restaurantJSON.getString("image_url");
//
//                double latitude = (double) restaurantJSON.getJSONObject("coordinates").getDouble("latitude");
//
//                double longitude = (double) restaurantJSON.getJSONObject("coordinates").getDouble("longitude");

                ArrayList<String> address = new ArrayList<>();
                JSONArray addressJSON = restaurantJSON.getJSONObject("location")
                        .getJSONArray("display_address");
                for (int y = 0; y < addressJSON.length(); y++) {
                    address.add(addressJSON.get(y).toString());
                }

                ArrayList<String> categories = new ArrayList<>();
                JSONArray categoriesJSON = restaurantJSON.getJSONArray("categories");

                for (int y = 0; y < categoriesJSON.length(); y++) {
                    categories.add(categoriesJSON.getJSONObject(y).getString("title"));
                }
                Restaurant restaurant = new Restaurant(id, name, phone, website, rating, price,
                        reviewCount, distance, imageUrl, address, categories);
                restaurants.add(restaurant);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return restaurants;
    }
}