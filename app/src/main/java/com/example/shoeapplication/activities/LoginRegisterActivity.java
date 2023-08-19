package com.example.shoeapplication.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;

import com.example.shoeapplication.R;
import com.example.shoeapplication.fragments.loginRegister.LoginFragment;
import com.example.shoeapplication.fragments.loginRegister.RegisterFragment;

public class LoginRegisterActivity extends AppCompatActivity {
    AppCompatButton btnRegisterAccountOptions, btnLoginAccountOptions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        refView();

        btnRegisterAccountOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(new RegisterFragment());
            }
        });

        btnLoginAccountOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment(new LoginFragment());
            }
        });

    }

    private void refView() {
        btnRegisterAccountOptions = findViewById(R.id.btnRegisterAccountOptions);
        btnLoginAccountOptions = findViewById(R.id.btnLoginAccountOptions);
    }
    private void getFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frLoginRegisterLayout, fragment).addToBackStack("lr").commit();
    }
}