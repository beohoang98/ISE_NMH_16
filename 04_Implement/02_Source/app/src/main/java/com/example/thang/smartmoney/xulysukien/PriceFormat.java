package com.example.thang.smartmoney.xulysukien;

import java.text.DecimalFormat;

public class PriceFormat {

    private static DecimalFormat fmt;

    public static String format(int price)
    {
        return getFmt().format(price);
    }

    static DecimalFormat getFmt()
    {
        if (fmt == null) {
            fmt = new DecimalFormat("#,###");
        }
        return fmt;
    }
}
