package com.example.thang.smartmoney.model;

import android.content.ContentValues;

import java.util.Date;

public class ClassIncome extends ClassGiaoDich {

    public ClassIncome(ContentValues val) {
        super(val);
        from_id = 0;
        to_id = ClassVi.VI_CHINH_ID;
    }

    public ClassIncome(Date thoigian, int sotien, int category_id, String note) {
        super(thoigian, sotien, category_id, note, 0, ClassVi.VI_CHINH_ID);
    }

    public static boolean isIncome(ClassGiaoDich giaoDich) {
        return (giaoDich.from_id == 0 && giaoDich.to_id == ClassVi.VI_CHINH_ID);
    }
}
