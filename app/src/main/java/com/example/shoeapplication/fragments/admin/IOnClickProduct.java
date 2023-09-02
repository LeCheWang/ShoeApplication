package com.example.shoeapplication.fragments.admin;

import com.example.shoeapplication.Models.Shoe;

public interface IOnClickProduct {
    void iOnClickEdit(Shoe shoe, int position);
    void iOnClickDelete(Shoe shoe, int position);
}
