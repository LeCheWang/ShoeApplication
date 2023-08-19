package com.example.shoeapplication.fragments.categories;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.adapters.BestDealsAdapter;
import com.example.shoeapplication.adapters.BestProductsAdapter;
import com.example.shoeapplication.adapters.SpecialProductsAdapter;
import com.example.shoeapplication.controllers.ApiController;
import com.example.shoeapplication.databinding.FragmentMainCategoryBinding;
import com.example.shoeapplication.databinding.FragmentSoccerShoesBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SoccerShoesFragment extends BaseCategoryFragment {
    public SoccerShoesFragment() {

    }

    private FragmentSoccerShoesBinding binding;
    List<Shoe> shoes = new ArrayList<>();

    SpecialProductsAdapter adapter;
    BestDealsAdapter adapter2;
    BestProductsAdapter adapter3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_soccer_shoes, container, false);
        // You can perform any additional view initialization or setup here
        ApiController.apiService.getShoes("Soccer").enqueue(new Callback<List<Shoe>>() {
            @Override
            public void onResponse(Call<List<Shoe>> call, Response<List<Shoe>> response) {
                if (response.isSuccessful()){
                    shoes = response.body();
                    adapter = new SpecialProductsAdapter(shoes, getActivity());
                    adapter2 = new BestDealsAdapter(shoes, getActivity());
                    adapter3 = new BestProductsAdapter(shoes, getActivity());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    RecyclerView.LayoutManager layoutManager3 = new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false);

                    binding.rvBestDealsProducts.setAdapter(adapter2);
                    binding.rvBestDealsProducts.setLayoutManager(layoutManager);

                    binding.rvSpecialProducts.setAdapter(adapter);
                    binding.rvSpecialProducts.setLayoutManager(layoutManager2);

                    binding.rvBestProducts.setAdapter(adapter3);
                    binding.rvBestProducts.setLayoutManager(layoutManager3);
                }else {
                    Toast.makeText(getActivity(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Shoe>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi đường truyền", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

}