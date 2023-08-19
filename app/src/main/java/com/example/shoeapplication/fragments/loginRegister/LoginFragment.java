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
import com.example.shoeapplication.databinding.FragmentLoginBinding;
import com.example.shoeapplication.helpers.SharedPreferencesHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    SharedPreferencesHelper sharedPreferencesHelper;
    FragmentLoginBinding binding;

    public static Account currentAccount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        sharedPreferencesHelper = new SharedPreferencesHelper(getActivity());

        currentAccount = sharedPreferencesHelper.getAccount();

        binding.edEmailLogin.setText(currentAccount.getUsername());
        binding.edPasswordLogin.setText(currentAccount.getPassword());

        binding.btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edEmailLogin.getText().toString().trim();
                String password = binding.edPasswordLogin.getText().toString().trim();
                sharedPreferencesHelper.saveAccount(username, password);

                ApiController.apiService.login(new Account(username, password)).enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        if (response.isSuccessful()) {
                            currentAccount = response.body();
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
        });

        return binding.getRoot();
    }


}
