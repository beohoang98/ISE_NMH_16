package com.example.thang.smartmoney.model;

import android.content.ContentValues;

import java.util.Date;

public class ClassIncome extends ClassGiaoDich {

    public ClassIncome(ContentValues val) {
        super(val);
        from_id = 0;
        to_id = 1;
    }

    public ClassIncome(Date thoigian, int sotien, int category_id, String note) {
        super(thoigian, sotien, category_id, note, 0, 1);
    }

    @Override
    public ContentValues getContentValues() {
        return super.getContentValues();
    }
}
