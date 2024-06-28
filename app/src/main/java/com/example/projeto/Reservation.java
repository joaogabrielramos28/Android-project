package com.example.projeto;

import com.google.firebase.firestore.DocumentReference;

public class Reservation {
    private String userId,day,hour,id;
    private Restaurant restaurant;
    private DocumentReference restaurantReference;

    public Reservation() {
    }

    public Reservation(String userId, String day, String hour, DocumentReference restaurantReference,String id) {
        this.userId = userId;
        this.day = day;
        this.hour = hour;
        this.restaurantReference = restaurantReference;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public DocumentReference getRestaurantReference() {
        return restaurantReference;
    }

    public void setRestaurantReference(DocumentReference restaurantReference) {
        this.restaurantReference = restaurantReference;
    }

    public Reservation getData() {
        return this;
    }
}
