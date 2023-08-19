package com.example.shoeapplication.fragments.loginRegister;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shoeapplication.R;

public class IntroductionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Sử dụng layout fragment_introduction.xml làm layout cho fragment
        return inflater.inflate(R.layout.fragment_introduction, container, false);
    }

    // Các phương thức và thành phần khác của IntroductionFragment
}
