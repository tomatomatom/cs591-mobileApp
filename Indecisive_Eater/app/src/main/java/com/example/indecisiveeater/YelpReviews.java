package com.example.indecisiveeater;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class YelpReviews {
    private static Semaphore rev_semaphore = new Semaphore(0);

    public static void getReviews(String restaurantID, Callback callback){
        OkHttpClient client = new OkHttpClient.Builder().build();

        String base = "https://api.yelp.com/v3/businesses/" + restaurantID + "/reviews";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(base).newBuilder();

        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.YELP_TOKEN)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<String> processReviews(Response response) {
        ArrayList<String> reviews = new ArrayList<>();
        try {
            String jsonData = response.body().string();
            JSONObject yelpJSON = new JSONObject(jsonData);
            JSONArray reviewsJSON = yelpJSON.getJSONArray("reviews");
            for(int i=0; i < reviewsJSON.length(); i++){
                JSONObject singleJSON = reviewsJSON.getJSONObject(i);
                String review_text = singleJSON.getString("text");
                reviews.add(review_text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

}
