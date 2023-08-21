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

public class BestDealsAdapter extends RecyclerView.Adapter<BestDealsAdapter.ViewHolder> {

    List<Shoe> shoes;
    Context context;

    IOnClick iOnClick;

    public IOnClick getiOnClick() {
        return iOnClick;
    }

    public void setiOnClick(IOnClick iOnClick) {
        this.iOnClick = iOnClick;
    }

    public BestDealsAdapter(List<Shoe> shoes, Context context) {
        this.shoes = shoes;
        this.context = context;
    }

    public void setShoes(List<Shoe> shoes) {
        this.shoes = shoes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BestDealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.best_deals_rv_item, parent, false);
        BestDealsAdapter.ViewHolder viewHolder = new BestDealsAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BestDealsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            Shoe shoe = shoes.get(position);
            holder.tv_deal_product_name.setText(shoe.getName());
            holder.tv_new_price.setText(MyHelper.formatToDolar(shoe.getNewprice()));

            holder.tv_old_price.setText(MyHelper.formatToDolar(shoe.getPrice()));
            holder.tv_old_price.setPaintFlags(holder.tv_old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            holder.btn_See_Product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    iOnClick.iOnClickSeeProduct(shoe, position);
                }
            });
            Glide.with(context).load(shoe.getLinkImage()).into(holder.img_best_deal);
    }

    @Override
    public int getItemCount() {
        return shoes == null ? 0 : shoes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_best_deal;
        TextView tv_deal_product_name, tv_new_price, tv_old_price;
        CircularProgressButton btn_See_Product;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_best_deal = itemView.findViewById(R.id.img_best_deal);
            tv_deal_product_name = itemView.findViewById(R.id.tv_deal_product_name);
            tv_new_price = itemView.findViewById(R.id.tv_new_price);
            tv_old_price = itemView.findViewById(R.id.tv_old_price);
            btn_See_Product = itemView.findViewById(R.id.btn_see_product);
        }
    }
}
