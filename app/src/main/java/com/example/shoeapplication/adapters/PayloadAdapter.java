package com.example.shoeapplication.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shoeapplication.Models.Cart;
import com.example.shoeapplication.Models.ItemCart;
import com.example.shoeapplication.Models.Order;
import com.example.shoeapplication.Models.Shoe;
import com.example.shoeapplication.R;
import com.example.shoeapplication.fragments.admin.IOnClickPayload;
import com.example.shoeapplication.helpers.MyHelper;

import java.util.List;

public class PayloadAdapter extends RecyclerView.Adapter<PayloadAdapter.ViewHolder> {
    List<Order<Cart<Shoe>>> orderList;
    Context context;

    public void setOrderList(List<Order<Cart<Shoe>>> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    IOnClickPayload iOnClickPayload;

    public IOnClickPayload getiOnClickPayload() {
        return iOnClickPayload;
    }

    public void setiOnClickPayload(IOnClickPayload iOnClickPayload) {
        this.iOnClickPayload = iOnClickPayload;
    }

    public PayloadAdapter(List<Order<Cart<Shoe>>> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public PayloadAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.payload_item, parent, false);
        PayloadAdapter.ViewHolder viewHolder = new PayloadAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PayloadAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Order<Cart<Shoe>> order = orderList.get(position);
        holder.tvOrderId.setText("Order " + order.getId());
        holder.tvCustomerInfor.setText(order.getCustomer() + " - " + order.getPhone() + " - " + order.getAddress());

        OrderAdapter orderAdapter = new OrderAdapter(order.getCart().getItems(), context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.revOrder.setLayoutManager(layoutManager);
        holder.revOrder.setAdapter(orderAdapter);

        holder.tvUpdateStatus.setText("Update Status (To " + order.getStatus() + ")");
        holder.tvUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iOnClickPayload.updateStatus(order.getId(), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderList == null ? 0 : orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderId, tvUpdateStatus, tvCustomerInfor;
        RecyclerView revOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderId = itemView.findViewById(R.id.tvOrderId);
            tvUpdateStatus = itemView.findViewById(R.id.tvUpdateStatus);
            revOrder = itemView.findViewById(R.id.revOrderOfPayload);
            tvCustomerInfor = itemView.findViewById(R.id.tvCustomerInfor);
        }
    }
}