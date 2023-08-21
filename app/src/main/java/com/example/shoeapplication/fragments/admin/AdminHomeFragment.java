package com.example.shoeapplication.fragments.admin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shoeapplication.R;
import com.example.shoeapplication.databinding.FragmentAdminAccountBinding;
import com.example.shoeapplication.databinding.FragmentAdminHomeBinding;

public class AdminHomeFragment extends Fragment {


    public AdminHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAdminHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false);


        return binding.getRoot();
    }
}