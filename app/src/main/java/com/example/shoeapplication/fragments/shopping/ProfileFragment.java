package com.example.shoeapplication.fragments.shopping;

import static com.example.shoeapplication.fragments.loginRegister.LoginFragment.currentAccount;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Order;
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.activities.LoginRegisterActivity;
import com.example.shoeapplication.adapters.OrderAdapter;
import com.example.shoeapplication.controllers.ApiController;
import com.example.shoeapplication.databinding.FragmentProfileBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        binding.tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginRegisterActivity.class));
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

        RecyclerView revOrder = dialog.findViewById(R.id.revOrder);
        TextView tvTitleOrder = dialog.findViewById(R.id.tvTitleOrder);

        tvTitleOrder.setText("TO " + status.toUpperCase());

        ApiController.apiService.getOrders(currentAccount.getId(), status).enqueue(new Callback<List<Order<Cart<Shoe>>>>() {
            @Override
            public void onResponse(Call<List<Order<Cart<Shoe>>>> call, Response<List<Order<Cart<Shoe>>>> response) {
                if (response.isSuccessful()){
                    List<Order<Cart<Shoe>>> orders = response.body();
                    List<ItemCart<Shoe>> itemCarts = new ArrayList<>();
                    for (Order<Cart<Shoe>> order: orders){
                        itemCarts.addAll(order.getCart().getItems());
                    }
                    OrderAdapter adapter = new OrderAdapter(itemCarts, getActivity());

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    revOrder.setLayoutManager(layoutManager);
                    revOrder.setAdapter(adapter);

                    if (itemCarts.size() > 0) {
                        dialog.show();
                    }else {
                        Toast.makeText(getActivity(), "No orders found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Order<Cart<Shoe>>>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}