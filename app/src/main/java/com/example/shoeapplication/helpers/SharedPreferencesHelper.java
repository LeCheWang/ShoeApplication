package com.example.shoeapplication.helpers;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoeapplication.Models.Account;

public class SharedPreferencesHelper extends AppCompatActivity {
    final  String SHARE_PRE_NAME="Account";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARE_PRE_NAME,MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveAccount(String username, String password) {
        editor.putString("username", username);
        editor.putString("password", password);
        editor.commit();
    }

    public Account getAccount(){
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        return new Account(username, password);
    }

}