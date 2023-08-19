package com.example.shoeapplication.activities;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import com.example.shoeapplication.R;
import com.example.shoeapplication.databinding.ActivityShoppingBinding;


public class ShoppingActivity extends AppCompatActivity {
    private ActivityShoppingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShoppingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.shoppingHostFragment);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
    }
}