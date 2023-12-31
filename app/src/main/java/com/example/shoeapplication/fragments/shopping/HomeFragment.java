package com.example.shoeapplication.fragments.shopping;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.shoeapplication.adapters.HomeViewpagerAdapter;
import com.example.shoeapplication.databinding.FragmentHomeBinding;
import com.example.shoeapplication.fragments.categories.MainCategoryFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HomeViewpagerAdapter viewPager2Adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Fragment> categoriesFragments = new ArrayList<>();
        categoriesFragments.add(new MainCategoryFragment("Apple"));
        categoriesFragments.add(new MainCategoryFragment("Samsung"));
        categoriesFragments.add(new MainCategoryFragment("Huawei"));
        categoriesFragments.add(new MainCategoryFragment("Xiaomi"));
//        categoriesFragments.add(new MainCategoryFragment("Oxford"));
//        categoriesFragments.add(new MainCategoryFragment("Loafer"));

        setupViewPager(categoriesFragments, getChildFragmentManager(), getViewLifecycleOwner().getLifecycle());
    }

    private void setupViewPager(ArrayList<Fragment> categoriesFragments, FragmentManager childFragmentManager, Lifecycle lifecycle) {
        viewPager2Adapter = new HomeViewpagerAdapter(categoriesFragments, childFragmentManager, lifecycle);
        binding.viewpagerHome.setAdapter(viewPager2Adapter);

        TabLayout tabLayout = binding.tabLayout; // Thay đổi tabLayout trong binding thành tên tương ứng

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, binding.viewpagerHome, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setText("Apple");
                        break;
                    case 1:
                        tab.setText("Samsung");
                        break;
                    case 2:
                        tab.setText("Huawei");
                        break;
                    case 3:
                        tab.setText("Xiaomi");
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }
}
