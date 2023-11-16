package com.example.shoeapplication.Models;

import com.google.gson.annotations.SerializedName;

public class Shoe {
    @SerializedName("_id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("imageUrl")
    private String linkImage;

    @SerializedName("price")
    private float price;

    @SerializedName("newPrice")
    private float newprice;

    @SerializedName("sizes")
    private String sizes = "xám,đen";

    @SerializedName("category")
    private String category;

    public Shoe(String id, String name, String description, String linkImage, float price, float newprice, String sizes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.linkImage = linkImage;
        this.price = price;
        this.newprice = newprice;
        this.sizes = sizes;
    }

    public Shoe(String id, String name, String description, String linkImage, float price, float newprice) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.linkImage = linkImage;
        this.price = price;
        this.newprice = newprice;
    }

    public Shoe(String name, String linkImage, float price, float newprice) {
        this.name = name;
        this.linkImage = linkImage;
        this.price = price;
        this.newprice = newprice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Shoe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkImage() {
        return linkImage;
    }

    public void setLinkImage(String linkImage) {
        this.linkImage = linkImage;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public float getNewprice() {
        return newprice;
    }

    public void setNewprice(long newprice) {
        this.newprice = newprice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }
}
