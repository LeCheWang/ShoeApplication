package com.example.shoeapplication.fragments.shopping;

import com.example.shoeapplication.Models.Shoe;

public interface IOnClickItemCart {
    void decrease(Shoe shoe, int position);
    void increase(Shoe shoe, int position);
    void delete(Shoe shoe, int position);
}
