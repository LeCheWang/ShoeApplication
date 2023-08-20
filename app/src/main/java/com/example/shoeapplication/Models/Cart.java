package com.example.shoeapplication.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Cart<T> {
    @SerializedName("_id")
    private String id;

    @SerializedName("user")
    private String user;

    @SerializedName("items")
    private List<ItemCart<T>> items;

    @SerializedName("createdAt")
    private String createdAt;

    public Cart(String id, String user, List<ItemCart<T>> items, String createdAt) {
        this.id = id;
        this.user = user;
        this.items = items;
        this.createdAt = createdAt;
    }

    public Cart() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<ItemCart<T>> getItems() {
        return items;
    }

    public void setItems(List<ItemCart<T>> items) {
        this.items = items;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", user='" + user + '\'' +
                ", items=" + items +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}
