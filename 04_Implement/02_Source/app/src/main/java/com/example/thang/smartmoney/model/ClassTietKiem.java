package com.example.thang.smartmoney.model;

import android.support.annotation.Nullable;

import java.util.Date;

public class ClassTietKiem extends ClassGiaoDich {

    public enum TYPE {
        WITHDRAW,  // RUT RA
        DEPOSIT  // THEM VAO
    }

    public ClassTietKiem(Date thoigian, int sotien, @Nullable String note, ClassTietKiem.TYPE type, int viID)
            throws IllegalArgumentException
    {
        super(thoigian, sotien, 0, note, 0, 0);

        if (type == TYPE.DEPOSIT) {
            from_id = ClassVi.VI_CHINH_ID;
            to_id = viID;
        } else if (type == TYPE.WITHDRAW) {
            from_id = viID;
            to_id = ClassVi.VI_CHINH_ID;
        } else {
            throw new IllegalArgumentException("Type tiet kiem khong dung");
        }
    }

    public ClassTietKiem(ClassGiaoDich gd) {
        super(gd);
    }

    public static boolean isTietKiem(ClassGiaoDich gd)
    {
        return (gd.from_id == ClassVi.VI_CHINH_ID && gd.to_id > ClassVi.VI_CHINH_ID)
                || (gd.to_id == ClassVi.VI_CHINH_ID && gd.from_id > ClassVi.VI_CHINH_ID);
    }
}
