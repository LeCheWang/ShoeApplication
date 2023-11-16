package com.example.shoeapplication.helpers;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MyHelper {

    public static String formatToDolar(float amount) {
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(amount);
        return formattedNumber + " vnđ";
    }
}
