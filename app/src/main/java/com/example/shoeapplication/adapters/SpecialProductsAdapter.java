package com.example.shoeapplication.adapters;

import android.content.Context;
import android.util.Log;
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

public class SpecialProductsAdapter extends RecyclerView.Adapter<SpecialProductsAdapter.ViewHolder> {

    List<Shoe> shoes;
    Context context;

    IOnClick iOnClick;

    public IOnClick getiOnClick() {
        return iOnClick;
    }

    public void setiOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    public SpecialProductsAdapter(List<Shoe> shoes, Context context) {
        this.shoes = shoes;
        this.context = context;
    }

    @NonNull
    @Override
    public SpecialProductsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.special_rv_item, parent, false);
        SpecialProductsAdapter.ViewHolder viewHolder = new SpecialProductsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialProductsAdapter.ViewHolder holder, int position) {
        Shoe shoe = shoes.get(position);
        holder.tv_ad_name.setText(shoe.getName());
        holder.tv_ad_price.setText(MyHelper.formatToDolar(shoe.getNewprice()));

        holder.btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClick.iOnClickAddToCard(shoe);
            }
        });

        Glide.with(context).load(shoe.getLinkImage()).placeholder(R.drawable.ic_launcher_background).into(holder.img_ad);
    }

    @Override
    public int getItemCount() {
        return shoes == null ? 0 : shoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_ad;
        TextView tv_ad_name, tv_ad_price;
        CircularProgressButton btn_add_to_cart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_ad = itemView.findViewById(R.id.img_ad);
            tv_ad_name = itemView.findViewById(R.id.tv_ad_name);
            tv_ad_price = itemView.findViewById(R.id.tv_ad_price);
            btn_add_to_cart = itemView.findViewById(R.id.btn_add_to_cart);
        }
    }
}
