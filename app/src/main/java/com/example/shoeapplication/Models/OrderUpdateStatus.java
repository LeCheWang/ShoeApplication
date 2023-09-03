package com.example.shoeapplication.Models;

import com.google.gson.annotations.SerializedName;

public class OrderUpdateStatus {
    @SerializedName("status")
    private String status;

    public OrderUpdateStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
