package com.example.shoeapplication.fragments.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.PopupMenu;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.shoeapplication.R;
import com.example.shoeapplication.activities.LoginRegisterActivity;
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

        binding.ivMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu();
            }
        });

        return binding.getRoot();
    }

    private void showPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(getActivity(), binding.ivMore);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setForceShowIcon(true);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.i_logout:
                        startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
                        break;
                }
                return true;
            }
        });
        popupMenu.show();
    }
}