package com.example.shoeapplication.fragments.shopping;

import static com.example.shoeapplication.fragments.loginRegister.LoginFragment.currentAccount;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
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

import com.bumptech.glide.Glide;
import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.adapters.BestDealsAdapter;
import com.example.shoeapplication.controllers.ApiController;
import com.example.shoeapplication.databinding.FragmentSearchBinding;
import com.example.shoeapplication.fragments.categories.IOnClick;
import com.example.shoeapplication.helpers.MyHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    FragmentSearchBinding binding;
    BestDealsAdapter adapter;
    List<Shoe> shoes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false);

        ApiController.apiService.getShoes().enqueue(new Callback<List<Shoe>>() {
            @Override
            public void onResponse(Call<List<Shoe>> call, Response<List<Shoe>> response) {
                if (response.isSuccessful()) {
                    shoes = response.body();
                    adapter = new BestDealsAdapter(shoes, getActivity());
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

                    binding.rvProductsSearch.setAdapter(adapter);
                    binding.rvProductsSearch.setLayoutManager(layoutManager);

                    adapter.setiOnClick(new IOnClick() {
                        @Override
                        public void iOnClickSeeProduct(Shoe shoe, int position) {
                            openDialogDetailProduct(shoe, position);
                        }

                        @Override
                        public void iOnClickAddToCard(Shoe shoe) {
                            //no action
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
                            String str = s.toString();
                            List<Shoe> searchShoes = new ArrayList<>();
                            for (Shoe shoe : shoes) {
                                if (shoe.getName().toLowerCase().contains(str.toLowerCase())) {
                                    searchShoes.add(shoe);
                                }
                            }
                            adapter.setShoes(searchShoes);
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
            public void onFailure(Call<List<Shoe>> call, Throwable t) {
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });

        binding.ivSearchByPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] prices = {"Low To High", "High To Low"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Sort By Price")
                        .setItems(prices, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String selectedSize = prices[which];

                                switch (selectedSize) {
                                    case "Low To High":
                                        sortByPrice("Low To High");
                                        break;
                                    case "High To Low":
                                        sortByPrice("High To Low");
                                        break;
                                }
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        binding.ivSearchByCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] categories = {"All", "Apple", "Samsung", "Huawei", "Xiaomi"};

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Search By Category")
                        .setItems(categories, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                String selectedSize = categories[which];

                                switch (selectedSize) {
                                    case "All":
                                        searchByCategory("All");
                                        break;
                                    case "Apple":
                                        searchByCategory("Apple");
                                        break;
                                    case "Samsung":
                                        searchByCategory("Samsung");
                                        break;
                                    case "Huawei":
                                        searchByCategory("Huawei");
                                        break;
                                    case "Xiaomi":
                                        searchByCategory("Xiaomi");
                                        break;

                                }
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        return binding.getRoot();
    }

    private void sortByPrice(String sortByPrice) {
        if (sortByPrice.equals("Low To High")) {
            Collections.sort(shoes, new Comparator<Shoe>() {
                @Override
                public int compare(Shoe o1, Shoe o2) {
                    if (o1.getNewprice() - o2.getNewprice() > 0) {
                        return 1;
                    } else if (o1.getNewprice() - o2.getNewprice() < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        } else if (sortByPrice.equals("High To Low")) {
            Collections.sort(shoes, new Comparator<Shoe>() {
                @Override
                public int compare(Shoe o1, Shoe o2) {
                    if (o2.getNewprice() - o1.getNewprice() > 0) {
                        return 1;
                    } else if (o2.getNewprice() - o1.getNewprice() < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                }
            });
        }
        adapter.setShoes(shoes);
    }

    private void searchByCategory(String category) {
        List<Shoe> searchShoes = new ArrayList<>();
        if (category.equals("All")) {
            searchShoes = shoes;
        } else {
            for (Shoe shoe : shoes) {
                if (shoe.getCategory().equals(category)) {
                    searchShoes.add(shoe);
                }
            }
        }
        adapter.setShoes(searchShoes);
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

        String[] sizes = shoe.getSizes().split(",");
        final String[] sizeChoose = {null};
        for (int size = 0; size < sizes.length; size++) {
            TextView textView = new TextView(getActivity());

            textView.setId(size);
            textView.setText(sizes[size]);

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
            int finalSize = size;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (sizeChoose[0] != null) {
                        TextView tvOld = dialog.findViewById(0);
                        tvOld.setBackgroundResource(R.drawable.bg_border_stroke);
                    }
                    if (sizeChoose[0] != sizes[finalSize]) {
                        textView.setBackgroundResource(R.drawable.bg_border_stroke_active);
                    } else {
                        textView.setBackgroundResource(R.drawable.bg_border_stroke);
                    }
                    sizeChoose[0] = sizes[finalSize];

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
                if (sizeChoose[0] == null) {
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
                Toast.makeText(getActivity(), "Network error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}