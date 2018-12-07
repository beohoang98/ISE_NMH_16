package com.example.thang.smartmoney.model;

import android.content.ContentValues;

import java.util.Date;

public class ClassExpense extends ClassGiaoDich {

    public ClassExpense(ContentValues val) {
        super(val);
        from_id = ClassVi.VI_CHINH_ID;
        to_id = 0;
    }

    public ClassExpense(Date thoigian, int sotien, int category_id, String note)
    {
        super(thoigian, sotien, category_id, note, ClassVi.VI_CHINH_ID, 0);
    }

}
