package com.example.thang.smartmoney.model;

import java.util.Date;

public class ClassTietKiem extends ClassGiaoDich {

    enum TYPE {
        WITHDRAW,  // THEM VO
        DEPOSIT  // RUT RA
    }

    public ClassTietKiem(Date thoigian, int sotien, String note, ClassTietKiem.TYPE type, int viID)
            throws IllegalArgumentException
    {
        super(thoigian, sotien, 0, note, 0, 0);

        if (type == TYPE.WITHDRAW) {
            from_id = ClassVi.VI_CHINH_ID;
            to_id = viID;
        } else if (type == TYPE.DEPOSIT) {
            from_id = viID;
            to_id = ClassVi.VI_CHINH_ID;
        } else {
            throw new IllegalArgumentException("Type tiet kiem khong dung");
        }
    }
}
