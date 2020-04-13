package com.example.indecisiveeater;

public class User {
    //user info
    public String email;
    public String password;
//    //allergies
//    public boolean peanut;
//    public boolean treenut;
//    public boolean soy;
//    public boolean wheat;
//    public boolean milk;
//    public boolean egg;
//    public boolean fish;
//    public boolean shellfish;
//    //dietary restrictions
//    public boolean vegetarian;
//    public boolean vegan;
//    public boolean gluten;

    //default constructor
    public User(){
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }
}
