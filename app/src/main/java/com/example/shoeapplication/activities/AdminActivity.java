package com.example.shoeapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.shoeapplication.R;
import com.example.shoeapplication.databinding.ActivityAdminBinding;
import com.example.shoeapplication.databinding.ActivityShoppingBinding;

public class AdminActivity extends AppCompatActivity {
    ActivityAdminBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.shoppingHostFragment);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
    }
}