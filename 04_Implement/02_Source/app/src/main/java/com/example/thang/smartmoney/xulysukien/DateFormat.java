package com.example.thang.smartmoney.xulysukien;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {

    private static SimpleDateFormat fmt;

    public static String format(Date date)
    {
        return getFmt().format(date);
    }

    static SimpleDateFormat getFmt()
    {
        if (fmt == null) {
            fmt = new SimpleDateFormat("dd/MM/yyyy");
        }
        return fmt;
    }
}
