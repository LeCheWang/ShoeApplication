package com.example.shoeapplication.fragments.admin;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.Order;
import com.example.shoeapplication.Models.OrderUpdateStatus;
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.adapters.PayloadAdapter;
import com.example.shoeapplication.controllers.ApiController;
import com.example.shoeapplication.databinding.FragmentAdminOrderBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminOrderFragment extends Fragment {

    public AdminOrderFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentAdminOrderBinding binding;
    List<Order<Cart<Shoe>>> orderList = new ArrayList<>();
    PayloadAdapter payloadAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_order, container, false);

        ApiController.apiService.getOrders().enqueue(new Callback<List<Order<Cart<Shoe>>>>() {
            @Override
            public void onResponse(Call<List<Order<Cart<Shoe>>>> call, Response<List<Order<Cart<Shoe>>>> response) {
                if (response.isSuccessful()){
                    orderList = response.body();
                    payloadAdapter = new PayloadAdapter(orderList, getActivity());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    binding.revPayload.setLayoutManager(layoutManager);
                    binding.revPayload.setAdapter(payloadAdapter);

                    payloadAdapter.setiOnClickPayload(new IOnClickPayload() {
                        @Override
                        public void updateStatus(String order_id, int position) {
                            final String[] status = {"pay", "ship", "receive", "rate"};
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Update Status")
                                    .setItems(status, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String status1 = status[which];
                                            OrderUpdateStatus order = new OrderUpdateStatus(status1);

                                            ApiController.apiService.updateStatusOrder(order_id, order).enqueue(new Callback<Order<String>>() {
                                                @Override
                                                public void onResponse(Call<Order<String>> call, Response<Order<String>> response) {
                                                    if (response.isSuccessful()){
                                                        Toast.makeText(getActivity(), "Update Success", Toast.LENGTH_SHORT).show();
                                                        orderList.get(position).setStatus(status1);
                                                        payloadAdapter.setOrderList(orderList);
                                                        dialog.dismiss();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Order<String>> call, Throwable t) {
                                                    Toast.makeText(getActivity(), "Network Error", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Order<Cart<Shoe>>>> call, Throwable t) {

            }
        });

        binding.ivSearchByStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] status = {"pay", "ship", "receive", "rate"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Search By Status")
                        .setItems(status, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String status1 = status[which];
                                List<Order<Cart<Shoe>>> orderListSearh = new ArrayList<>();
                                for (Order<Cart<Shoe>> order: orderList){
                                    if (order.getStatus().equals(status1)){
                                        orderListSearh.add(order);
                                    }
                                }
                                payloadAdapter.setOrderList(orderListSearh);
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        binding.edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String name = s.toString().trim().toLowerCase();
                List<Order<Cart<Shoe>>> orderListSearh = new ArrayList<>();
                for (Order<Cart<Shoe>> order: orderList){
                    if (order.getCustomer().toLowerCase().contains(name)){
                        orderListSearh.add(order);
                    }
                }
                payloadAdapter.setOrderList(orderListSearh);
            }
        });

        return binding.getRoot();
    }
}