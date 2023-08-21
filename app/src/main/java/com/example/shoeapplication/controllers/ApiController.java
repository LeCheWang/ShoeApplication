package com.example.shoeapplication.controllers;

import com.example.shoeapplication.Models.Account;
import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Shoe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiController {
    String DOMAIN = "https://api.shoe.gorokiapp.com/api/";
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    ApiController apiService = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiController.class);

    //Shoe
    @GET("shoes")
    Call<List<Shoe>> getShoes(@Query("category") String category);
    @GET("shoes")
    Call<List<Shoe>> getShoes();

    @POST("shoes")
    @Multipart
    Call<Shoe> createShoe(@Part("name") RequestBody name, @Part("description") RequestBody description, @Part("price") RequestBody price, @Part MultipartBody.Part img);

    //account
    @POST("accounts/login")
    Call<Account> login(@Body() Account account);

    @POST("accounts")
    Call<Account> register(@Body() Account account);

    //cart
    @POST("carts/{id_account}")
    Call<Cart<String>> addToCart(@Body() ItemCart<String> itemCart, @Path("id_account") String id_account);

    @GET("carts/{id_account}")
    Call<Cart<Shoe>> getCart(@Path("id_account") String id_account);

    @DELETE("carts/{id_account}/{id_shoe}")
    Call<Cart<String>> deleteItemCart(@Path("id_account") String id_account, @Path("id_shoe") String id_shoe);
}
