package com.example.shoeapplication.helpers;

import java.text.DecimalFormat;

public class MyHelper {

    public static String formatToDolar(float amount) {
        DecimalFormat decimalFormat = new DecimalFormat("$0.00");
        return decimalFormat.format(amount).replace(",", ".");
    }
}
