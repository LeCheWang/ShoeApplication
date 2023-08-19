package com.example.shoeapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.fragments.categories.IOnClick;
import com.example.shoeapplication.helpers.MyHelper;

import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class BestProductsAdapter extends RecyclerView.Adapter<BestProductsAdapter.ViewHolder> {

    List<Shoe> shoes;
    Context context;

    IOnClick iOnClick;

    public IOnClick getiOnClick() {
        return iOnClick;
    }

    public void setiOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    public BestProductsAdapter(List<Shoe> shoes, Context context) {
        this.shoes = shoes;
        this.context = context;
    }

    @NonNull
    @Override
    public BestProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.product_rv_item, parent, false);
        BestProductsAdapter.ViewHolder viewHolder = new BestProductsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BestProductsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Shoe shoe = shoes.get(position);
        holder.tv_name.setText(shoe.getName());
        holder.tv_price.setText(MyHelper.formatToDolar(shoe.getPrice()));
        holder.tv_price.setPaintFlags(holder.tv_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.tv_new_price.setText(MyHelper.formatToDolar(shoe.getNewprice()));

        Glide.with(context).load(shoe.getLinkImage()).into(holder.img_product);

        holder.btn_see_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClick.iOnClickSeeProduct(shoe, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoes == null ? 0 : shoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_product;
        TextView tv_name, tv_price, tv_new_price;

        CircularProgressButton btn_see_product, btn_add_to_card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_product = itemView.findViewById(R.id.img_product);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_new_price = itemView.findViewById(R.id.tv_new_price);
            btn_see_product = itemView.findViewById(R.id.btn_see_product);
            btn_add_to_card = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
