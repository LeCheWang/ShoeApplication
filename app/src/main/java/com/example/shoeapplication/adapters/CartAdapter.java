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
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.fragments.shopping.IOnClickItemCart;
import com.example.shoeapplication.helpers.MyHelper;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Cart<Shoe> cart;
    Context context;

    IOnClickItemCart iOnClickItemCart;

    public IOnClickItemCart getiOnClickItemCart() {
        return iOnClickItemCart;
    }

    public void setiOnClickItemCart(IOnClickItemCart iOnClickItemCart) {
        this.iOnClickItemCart = iOnClickItemCart;
    }

    public CartAdapter(Cart<Shoe> cart, Context context) {
        this.cart = cart;
        this.context = context;
    }

    public Cart<Shoe> getCart() {
        return cart;
    }

    public void setCart(Cart<Shoe> cart) {
        this.cart = cart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        CartAdapter.ViewHolder viewHolder = new CartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ItemCart<Shoe> itemCart = cart.getItems().get(position);
        Shoe shoe = itemCart.getShoe();
        holder.tvShoeName.setText(shoe.getName());
        holder.tvPrice.setText(MyHelper.formatToDolar(shoe.getNewprice()));
        holder.tvQuantityText.setText(itemCart.getQuantity() + "");
        holder.tvSize.setText(itemCart.getSize());
        Glide.with(context).load(shoe.getLinkImage()).into(holder.imgShoe);

        holder.tvDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemCart.decrease(shoe, position);
            }
        });

        holder.tvIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemCart.increase(shoe, position);
            }
        });

        holder.tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickItemCart.delete(shoe, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart.getItems() == null ? 0 : cart.getItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgShoe;
        TextView tvShoeName, tvPrice, tvDecrease, tvQuantityText, tvIncrease, tvDelete, tvSize;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgShoe = itemView.findViewById(R.id.imgShoe);
            tvShoeName = itemView.findViewById(R.id.tvShoeName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDecrease = itemView.findViewById(R.id.tvDecrease);
            tvQuantityText = itemView.findViewById(R.id.tvQuantityText);
            tvIncrease = itemView.findViewById(R.id.tvIncrease);
            tvDelete = itemView.findViewById(R.id.tvDelete);
            tvSize = itemView.findViewById(R.id.tvSize);
        }
    }
}
