package com.example.workouttracker.data.model;


public class User {

    public String uid;
    public String email;
    public String username;
    public String firstName;
    public String lastName;
    public long age;
    public float height;
    public float weight;
    public String photoUrl;

    public User() {};

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
    }

    public User(String uid, String email, String username, String firstName, String lastName, long age, float height, float weight, String photoUrl) {
        this.uid = uid;
        this.email = email;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.photoUrl = photoUrl;
    }
}
