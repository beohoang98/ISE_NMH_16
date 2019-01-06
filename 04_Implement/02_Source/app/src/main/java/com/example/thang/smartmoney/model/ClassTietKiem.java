package com.example.thang.smartmoney.model;

import android.support.annotation.Nullable;

import java.util.Date;

public class ClassTietKiem extends ClassGiaoDich {

    public enum TYPE {
        WITHDRAW,  // RUT RA
        DEPOSIT  // THEM VAO
    }
    public TYPE type;
    public int viId;

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


        this.viId = viID;
        this.type = type;
    }

    public ClassTietKiem(ClassGiaoDich gd) {
        super(gd);

        if (from_id == ClassVi.VI_CHINH_ID && to_id > ClassVi.VI_CHINH_ID) {
            this.type = TYPE.DEPOSIT;
            this.viId = to_id;
        } else if (from_id > ClassVi.VI_CHINH_ID && to_id == ClassVi.VI_CHINH_ID) {
            this.type = TYPE.WITHDRAW;
            this.viId = from_id;
        }
    }

    public static boolean isTietKiem(ClassGiaoDich gd)
    {
        return (gd.from_id != 0 && gd.to_id != 0);
    }
}
