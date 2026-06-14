package com.example.workouttracker.data.model;


public class User {

    public String uid;
    public String email;
    public String username;
    public int age;
    public float height;
    public float weight;
    public String photoUrl;

    public User() {};

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public User(String uid, String email, String username, int age, float height, float weight, String photoUrl) {
        this.uid = uid;
        this.email = email;
        this.username = username;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.photoUrl = photoUrl;
    }
}
