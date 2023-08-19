package com.example.shoeapplication.fragments.loginRegister;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.shoeapplication.Models.Account;
import com.example.shoeapplication.R;
import com.example.shoeapplication.activities.ShoppingActivity;
import com.example.shoeapplication.controllers.ApiController;
import com.example.shoeapplication.databinding.FragmentRegisterBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false);

        binding.btnRegisterRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edEmailRegister.getText().toString().trim();
                String password = binding.edPasswordRegister.getText().toString().trim();
                String passwordAgain = binding.edPasswordRegisterAgain.getText().toString().trim();
                String phone = binding.edPhone.getText().toString().trim();
                String address = binding.edAddress.getText().toString().trim();

                if (passwordAgain.compareTo(password) != 0){
                    Toast.makeText(getActivity(), "Password not match", Toast.LENGTH_SHORT).show();
                }else {
                    Account account = new Account(username, password, phone, address);
                    ApiController.apiService.register(account).enqueue(new Callback<Account>() {
                        @Override
                        public void onResponse(Call<Account> call, Response<Account> response) {
                            if (response.isSuccessful()) {
                                LoginFragment.currentAccount = response.body();
                                Toast.makeText(getActivity(), "Register success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getActivity(), ShoppingActivity.class));
                            } else {
                                String err="";
                                try {
                                    JSONObject jsonObject= new JSONObject(response.errorBody().string());
                                    err= jsonObject.getString("error");
                                } catch (JSONException | IOException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(getActivity(),  err, Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Account> call, Throwable t) {
                            Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return binding.getRoot();
    }

    // Các phương thức và thành phần khác của IntroductionFragment
}
