package com.example.shoeapplication.fragments.admin;

import com.example.shoeapplication.Models.Order;

public interface IOnClickPayload {
    void updateStatus(String order_id, int position);
}
