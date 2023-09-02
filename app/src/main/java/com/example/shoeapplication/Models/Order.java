package com.example.shoeapplication.Models;

import com.google.gson.annotations.SerializedName;

public class Order<T> {
    @SerializedName("customer")
    private String customer;

    @SerializedName("phone")
    private String phone;

    @SerializedName("address")
    private String address;

    @SerializedName("total_money")
    private double total_money;

    @SerializedName("payment_method")
    private String payment_method;

    @SerializedName("is_payment")
    private int is_payment;

    @SerializedName("status")
    private String status;

    @SerializedName("cart")
    private T cart;

    @SerializedName("account")
    private String account;

    public Order(String customer, String phone, String address, double total_money, String payment_method, T cart, String account) {
        this.customer = customer;
        this.phone = phone;
        this.address = address;
        this.total_money = total_money;
        this.payment_method = payment_method;
        this.cart = cart;
        this.account = account;
    }

    public Order(String customer, String phone, String address, double total_money, String payment_method, int is_payment, String status, T cart, String account) {
        this.customer = customer;
        this.phone = phone;
        this.address = address;
        this.total_money = total_money;
        this.payment_method = payment_method;
        this.is_payment = is_payment;
        this.status = status;
        this.cart = cart;
        this.account = account;
    }

    public Order() {
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal_money() {
        return total_money;
    }

    public void setTotal_money(double total_money) {
        this.total_money = total_money;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public int getIs_payment() {
        return is_payment;
    }

    public void setIs_payment(int is_payment) {
        this.is_payment = is_payment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getCart() {
        return cart;
    }

    public void setCart(T cart) {
        this.cart = cart;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
