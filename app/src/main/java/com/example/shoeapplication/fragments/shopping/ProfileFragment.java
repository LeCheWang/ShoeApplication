package com.example.shoeapplication.fragments.shopping;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.shoeapplication.R;
import com.example.shoeapplication.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    FragmentProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);

        binding.tvToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogMyOrder("pay");
            }
        });

        binding.tvToShip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogMyOrder("ship");
            }
        });
        binding.tvToReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogMyOrder("receive");
            }
        });
        binding.tvToRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogMyOrder("rate");
            }
        });

        return binding.getRoot();
    }

    private void openDialogMyOrder(String status) {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_my_order);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtributes = window.getAttributes();
        windowAtributes.gravity = Gravity.CENTER_VERTICAL;
        window.setAttributes(windowAtributes);

        dialog.setCancelable(true);


        dialog.show();
    }
}