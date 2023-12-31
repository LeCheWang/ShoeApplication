package com.example.shoeapplication.controllers;

import com.example.shoeapplication.Models.Account;
import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Order;
import com.example.shoeapplication.Models.OrderUpdateStatus;
import com.example.shoeapplication.Models.Shoe;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

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
import retrofit2.http.PATCH;
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
    Call<Shoe> createShoe(@Part("name") RequestBody name, @Part("description") RequestBody description, @Part("price") RequestBody price, @Part("newPrice") RequestBody newPrice, @Part("sizes") RequestBody sizes, @Part("category") RequestBody category, @Part MultipartBody.Part img);

    @PATCH("shoes/{id}")
    @Multipart
    Call<Shoe> updateShoe(@Path("id") String id,@Part("name") RequestBody name, @Part("description") RequestBody description, @Part("price") RequestBody price, @Part("newPrice") RequestBody newPrice, @Part("sizes") RequestBody sizes, @Part("category") RequestBody category, @Part MultipartBody.Part img);

    @DELETE("shoes/{id}")
    Call<JSONObject> deleteShoe(@Path("id") String id);


    //account
    @POST("accounts/login")
    Call<Account> login(@Body() Account account);

    @POST("accounts")
    Call<Account> register(@Body() Account account);

    //cart
    @POST("carts/{id_account}")
    Call<Cart<String>> addToCart(@Body() ItemCart<String> itemCart, @Path("id_account") String id_account);

    @GET("carts/{id_account}")
    Call<Cart<Shoe>> getCart(@Path("id_account") String id_account, @Query("isOrder") int isOrder);

    @DELETE("carts/{id_account}/{id_shoe}")
    Call<Cart<String>> deleteItemCart(@Path("id_account") String id_account, @Path("id_shoe") String id_shoe);

    //orders
    @GET("orders")
    Call<List<Order<Cart<Shoe>>>> getOrders(@Query("account_id") String account_id, @Query("status") String status);

    @GET("orders")
    Call<List<Order<Cart<Shoe>>>> getOrders();

    @POST("orders")
    Call<Order<String>> createOrder(@Body() Order<String> order);

    @PATCH("orders/{id}")
    Call<Order<String>> updateStatusOrder(@Path("id") String id, @Body() OrderUpdateStatus order);
}
