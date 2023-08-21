package com.example.shoeapplication.fragments.admin;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.shoeapplication.R;
import com.example.shoeapplication.databinding.FragmentAdminAccountBinding;

import java.util.ArrayList;


public class AdminAccountFragment extends Fragment {

     public AdminAccountFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAdminAccountBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_account, container, false);



        return binding.getRoot();
    }
}