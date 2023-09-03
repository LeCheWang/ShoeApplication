package com.example.shoeapplication.fragments.shopping;

import static com.example.shoeapplication.fragments.loginRegister.LoginFragment.currentAccount;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Order;
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

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
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
    float sum = 0;
    String cart_id;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);

        ApiController.apiService.getCart(currentAccount.getId(), 0).enqueue(new Callback<Cart<Shoe>>() {
            @Override
            public void onResponse(Call<Cart<Shoe>> call, Response<Cart<Shoe>> response) {
                if (response.isSuccessful()) {
                    Cart<Shoe> cart = response.body();
                    cart_id = cart.getId();

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

        binding.tvBtnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogCheckout();
            }
        });

        return binding.getRoot();
    }

    private void openDialogCheckout() {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_checkout);

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

        EditText edit_name = dialog.findViewById(R.id.edit_name);
        EditText edit_phone = dialog.findViewById(R.id.edit_phone);
        EditText edit_address = dialog.findViewById(R.id.edit_address);

        TextView text_total_price = dialog.findViewById(R.id.text_total_price);
        text_total_price.setText("Total Price: " + MyHelper.formatToDolar(sum));

        TextView tvClose = dialog.findViewById(R.id.tvClose);
        CircularProgressButton btnPlaceOrder = dialog.findViewById(R.id.btnPlaceOrder);
        RadioButton rbPaymentOnDeliver = dialog.findViewById(R.id.rbPaymentOnDeliver);
        RadioButton rbBankTransfer = dialog.findViewById(R.id.rbBankTransfer);
        RadioGroup rbPaymentGroup = dialog.findViewById(R.id.rbPaymentGroup);

        final String[] paymentMethod = {"On Delivery"};
        rbPaymentGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Kiểm tra RadioButton nào được chọn
                if (checkedId == R.id.rbPaymentOnDeliver) {
                    // Xử lý khi RadioButton Payment on delivery được chọn
                    rbBankTransfer.setChecked(false);
                    paymentMethod[0] = "On Delivery";
                } else if (checkedId == R.id.rbBankTransfer) {
                    // Xử lý khi RadioButton Bank transfer được chọn
                    rbPaymentOnDeliver.setChecked(false);
                    paymentMethod[0] = "Online";
                }
            }
        });

        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String customer = edit_name.getText().toString().trim();
                String phone = edit_phone.getText().toString().trim();
                String address = edit_address.getText().toString().trim();

                Order<String> order = new Order<>(customer, phone, address, sum, paymentMethod[0], cart_id, currentAccount.getId());

                ApiController.apiService.createOrder(order).enqueue(new Callback<Order<String>>() {
                    @Override
                    public void onResponse(Call<Order<String>> call, Response<Order<String>> response) {
                        if (response.isSuccessful()){
                            Cart<Shoe> cart = new Cart<>();
                            cartAdapter.setCart(cart);
                            binding.tvBtnCheckout.setText("Checkout(0)");
                            binding.tvTotalPrice.setText("$ 0");
                            dialog.dismiss();
                            Toast.makeText(getActivity(), "Order Success", Toast.LENGTH_SHORT).show();
                        }else {
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
                    public void onFailure(Call<Order<String>> call, Throwable t) {
                        Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void calTotalPriceAndQuantum(Cart<Shoe> cart) {
        List<ItemCart<Shoe>> itemCart = cart.getItems();
        sum = 0;
        for (ItemCart<Shoe> itemCart1 : itemCart) {
            sum += itemCart1.getShoe().getNewprice() * itemCart1.getQuantity();
        }

        binding.tvTotalPrice.setText(MyHelper.formatToDolar(sum));
        binding.tvBtnCheckout.setText("Checkout(" + itemCart.size() + ")");
    }
}