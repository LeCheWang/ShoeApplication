package com.example.shoeapplication.fragments.shopping;

import static com.example.shoeapplication.fragments.loginRegister.LoginFragment.currentAccount;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.adapters.CartAdapter;
import com.example.shoeapplication.controllers.ApiController;
import com.example.shoeapplication.databinding.FragmentCartBinding;
import com.example.shoeapplication.helpers.MyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CartFragment extends Fragment {

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentCartBinding binding;
    CartAdapter cartAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);

        ApiController.apiService.getCart(currentAccount.getId()).enqueue(new Callback<Cart<Shoe>>() {
            @Override
            public void onResponse(Call<Cart<Shoe>> call, Response<Cart<Shoe>> response) {
                if (response.isSuccessful()) {
                    Cart<Shoe> cart = response.body();

                    calTotalPriceAndQuantum(cart);

                    cartAdapter = new CartAdapter(cart, getActivity());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    binding.revCart.setLayoutManager(layoutManager);
                    binding.revCart.setAdapter(cartAdapter);

                    cartAdapter.setiOnClickItemCart(new IOnClickItemCart() {
                        @Override
                        public void decrease(Shoe shoe, int position) {
                            if (cart.getItems().get(position).getQuantity() > 1) {
                                cart.getItems().get(position).setQuantity(cart.getItems().get(position).getQuantity() - 1);
                                cartAdapter.setCart(cart);
                                calTotalPriceAndQuantum(cart);
                            }
                        }

                        @Override
                        public void increase(Shoe shoe, int position) {
                            if (cart.getItems().get(position).getQuantity() < 10) {
                                cart.getItems().get(position).setQuantity(cart.getItems().get(position).getQuantity() + 1);
                                cartAdapter.setCart(cart);
                                calTotalPriceAndQuantum(cart);
                            }
                        }

                        @Override
                        public void delete(Shoe shoe, int position) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Delete Confirm");
                            builder.setMessage("Delete " + shoe.getName() + " | " + MyHelper.formatToDolar(shoe.getPrice()));

                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //call api delete
                                            ApiController.apiService.deleteItemCart(currentAccount.getId(), shoe.getId()).enqueue(new Callback<Cart<String>>() {
                                                @Override
                                                public void onResponse(Call<Cart<String>> call, Response<Cart<String>> response) {
                                                    if (response.isSuccessful()) {
                                                        cart.getItems().remove(position);
                                                        cartAdapter.setCart(cart);
                                                        calTotalPriceAndQuantum(cart);
                                                    } else {
                                                        String err = "";
                                                        try {
                                                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                                                            err = jsonObject.getString("error");
                                                        } catch (JSONException | IOException e) {
                                                            e.printStackTrace();
                                                        }
                                                        Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<Cart<String>> call, Throwable t) {
                                                    Toast.makeText(getActivity(), "Cannot delete, Please try again later!", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        }
                                    })
                                    .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.dismiss();
                                        }
                                    }).create();

                            builder.show();
                        }
                    });
                } else {
                    String err = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response.errorBody().string());
                        err = jsonObject.getString("error");
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cart<Shoe>> call, Throwable t) {
                Toast.makeText(getActivity(), "Error networking", Toast.LENGTH_SHORT).show();
            }
        });

        return binding.getRoot();
    }

    private void calTotalPriceAndQuantum(Cart<Shoe> cart) {
        List<ItemCart<Shoe>> itemCart = cart.getItems();
        float sum = 0;
        for (ItemCart<Shoe> itemCart1 : itemCart) {
            sum += itemCart1.getShoe().getNewprice() * itemCart1.getQuantity();
        }

        binding.tvTotalPrice.setText(MyHelper.formatToDolar(sum));
        binding.tvBtnCheckout.setText("Checkout(" + itemCart.size() + ")");
    }
}