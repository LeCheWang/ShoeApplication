package com.example.shoeapplication.Models;

import com.google.gson.annotations.SerializedName;

public class ItemCart<T> {
    @SerializedName("shoe")
    private T shoe;

    @SerializedName("size")
    private String size;

    @SerializedName("quantity")
    private int quantity;


    public ItemCart(T shoe, String size, int quantity) {
        this.shoe = shoe;
        this.size = size;
        this.quantity = quantity;
    }

    public ItemCart(T shoe, String size) {
        this.shoe = shoe;
        this.size = size;
    }

    public T getShoe() {
        return shoe;
    }

    public void setShoe(T shoe) {
        this.shoe = shoe;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ItemCard{" +
                "shoe=" + shoe +
                ", size='" + size + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
