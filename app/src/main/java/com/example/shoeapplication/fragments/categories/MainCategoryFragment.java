package com.example.shoeapplication.fragments.categories;

import static com.example.shoeapplication.fragments.loginRegister.LoginFragment.currentAccount;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.adapters.BestDealsAdapter;
import com.example.shoeapplication.adapters.BestProductsAdapter;
import com.example.shoeapplication.adapters.SpecialProductsAdapter;
import com.example.shoeapplication.controllers.ApiController;
import com.example.shoeapplication.databinding.FragmentMainCategoryBinding;
import com.example.shoeapplication.helpers.MyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainCategoryFragment extends BaseCategoryFragment {
    private String tab;
    public MainCategoryFragment(String tab) {
        this.tab = tab;
    }

    private FragmentMainCategoryBinding binding;
    List<Shoe> shoes = new ArrayList<>();

    SpecialProductsAdapter adapter;
    BestDealsAdapter adapter2;
    BestProductsAdapter adapter3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_category, container, false);
        // You can perform any additional view initialization or setup here
        ApiController.apiService.getShoes(tab).enqueue(new Callback<List<Shoe>>() {
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

                    adapter.setiOnClick(new IOnClick() {
                        @Override
                        public void iOnClickSeeProduct(Shoe shoe, int position) {
                            //no action
                        }

                        @Override
                        public void iOnClickAddToCard(Shoe shoe) {
                            choiceSizeAndAddToCart(shoe);
                        }
                    });

                    adapter2.setiOnClick(new IOnClick() {
                        @Override
                        public void iOnClickSeeProduct(Shoe shoe, int position) {
                            openDialogDetailProduct(shoe, position);
                        }

                        @Override
                        public void iOnClickAddToCard(Shoe shoe) {

                        }
                    });

                    adapter3.setiOnClick(new IOnClick() {
                        @Override
                        public void iOnClickSeeProduct(Shoe shoe, int position) {
                            openDialogDetailProduct(shoe, position);
                        }

                        @Override
                        public void iOnClickAddToCard(Shoe shoe) {
                            choiceSizeAndAddToCart(shoe);
                        }
                    });
                }else {
                    Toast.makeText(getActivity(), "Không lấy được dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Shoe>> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi đường truyền " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("tt", "onFailure: " + t.getMessage());
            }
        });

        return binding.getRoot();
    }

    private void choiceSizeAndAddToCart(Shoe shoe) {
        final String[] sizes = {"37", "38", "39", "40", "41"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Sizes")
                .setItems(sizes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedSize = sizes[which];
                        addToCart(shoe.getId(), selectedSize, 1);
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void openDialogDetailProduct(Shoe shoe, int position) {
        Dialog dialog = new Dialog(getActivity());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.shoe_detail);

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

        ImageView backButton, imgProductImage, imgFavoriteButton;
        TextView productName, salePrice, originalPrice, productDescription;
        Button addToCartButton;
        LinearLayout linearContainer;

        linearContainer = dialog.findViewById(R.id.linearContainer);
        backButton = dialog.findViewById(R.id.imgBackButton);
        imgProductImage = dialog.findViewById(R.id.imgProductImage);
        imgFavoriteButton = dialog.findViewById(R.id.imgFavoriteButton);

        productName = dialog.findViewById(R.id.productName);
        salePrice = dialog.findViewById(R.id.salePrice);
        originalPrice = dialog.findViewById(R.id.originalPrice);
        productDescription = dialog.findViewById(R.id.productDescription);

        addToCartButton = dialog.findViewById(R.id.addToCartButton);

        String []sizes = shoe.getSizes().split(",");
        final String[] sizeChoose = {null};
        for (String size: sizes){
            TextView textView = new TextView(getActivity());

            textView.setId(Integer.parseInt(size));
            textView.setText(size);

            // Set các thuộc tính cho TextView
            textView.setTextColor(getResources().getColor(R.color.black));
            textView.setTextSize(18); // Unit là sp
            int padding = (int) getResources().getDimension(R.dimen.padding_4dp); // Lấy giá trị padding từ resources
            textView.setPadding(padding, padding, padding, padding);

            textView.setBackgroundResource(R.drawable.bg_border_stroke);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            layoutParams.setMarginEnd(8);
            textView.setLayoutParams(layoutParams);

            // Xử lý sự kiện click
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sizeChoose[0] != null){
                        TextView tvOld = dialog.findViewById(Integer.parseInt(sizeChoose[0]));
                        tvOld.setBackgroundResource(R.drawable.bg_border_stroke);
                    }
                    if (sizeChoose[0] != size){
                        textView.setBackgroundResource(R.drawable.bg_border_stroke_active);
                    }else {
                        textView.setBackgroundResource(R.drawable.bg_border_stroke);
                    }
                    sizeChoose[0] = size;

                }
            });

            linearContainer.addView(textView);
        }

        productName.setText(shoe.getName());
        productDescription.setText(shoe.getDescription());
        salePrice.setText(MyHelper.formatToDolar(shoe.getNewprice()));

        originalPrice.setText(MyHelper.formatToDolar(shoe.getPrice()));
        originalPrice.setPaintFlags(originalPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Glide.with(getActivity()).load(shoe.getLinkImage()).into(imgProductImage);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sizeChoose[0] == null){
                    Toast.makeText(getActivity(), "please choose size!", Toast.LENGTH_SHORT).show();
                    return;
                }
                addToCart(shoe.getId(), sizeChoose[0], 1);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void addToCart(String shoe_id, String size, int quantity) {
        ItemCart<String> itemCart = new ItemCart<String>(shoe_id, size, quantity);

        ApiController.apiService.addToCart(itemCart, currentAccount.getId()).enqueue(new Callback<Cart<String>>() {
            @Override
            public void onResponse(Call<Cart<String>> call, Response<Cart<String>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Added to card", Toast.LENGTH_SHORT).show();
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
            public void onFailure(Call<Cart<String>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
