package com.example.indecisiveeater;

import java.io.Serializable;
import java.util.ArrayList;

public class Restaurant implements Serializable {
    private String id;
    private String name;
    private String phone;
    private String website;
    private double rating;
    private String price;
    private int reviewCount;
    private double distance;
    private String imageUrl;
    private ArrayList<String> address;
//    private double latitude;
//    private double longitude;
    private ArrayList<String> categories;

    public Restaurant(String id, String name, String phone, String website,
                      double rating, String price, int reviewCount, double distance,
                      String imageUrl, ArrayList<String> address, ArrayList<String> categories) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.website = website;
        this.rating = rating;
        this.price = price;
        this.reviewCount = reviewCount;
        this.distance = distance;
        this.imageUrl = imageUrl;
        this.address = address;
//        this.latitude = latitude;
//        this.longitude = longitude;
        this.categories = categories;
    }

    public Restaurant(){

    }

    public String getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return  website;
    }

    public double getRating() {
        return rating;
    }

    public String getPrice(){
        return price;
    }

    public int getReviewCount(){
        return reviewCount;
    }

    public double getDistance(){
        return distance;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public ArrayList<String> getAddress() {
        return address;
    }

//    public double getLatitude() {
//        return latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }

    public ArrayList<String> getCategories() {
        return categories;
    }
}