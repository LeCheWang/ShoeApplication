package com.example.shoeapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Order;
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.fragments.shopping.IOnClickItemCart;
import com.example.shoeapplication.helpers.MyHelper;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    List<ItemCart<Shoe>> itemCarts;
    Context context;

    public OrderAdapter(List<ItemCart<Shoe>> itemCarts, Context context) {
        this.itemCarts = itemCarts;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.order_item, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemCart<Shoe> itemCart = itemCarts.get(position);
        Shoe shoe = itemCart.getShoe();
        holder.tvShoeNameOrder.setText(shoe.getName());
        holder.tvPrice.setText(MyHelper.formatToDolar(shoe.getNewprice()));
        holder.tvQuantity.setText(itemCart.getQuantity() + "");
        holder.tvSize.setText(itemCart.getSize());

        Glide.with(context).load(shoe.getLinkImage()).into(holder.imgShoeOrder);


    }

    @Override
    public int getItemCount() {
        return itemCarts == null ? 0 : itemCarts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgShoeOrder;
        TextView tvShoeNameOrder, tvPrice, tvQuantity, tvSize;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShoeOrder = itemView.findViewById(R.id.imgShoeOrder);
            tvShoeNameOrder = itemView.findViewById(R.id.tvShoeNameOrder);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvSize = itemView.findViewById(R.id.tvSize);
        }
    }
}
