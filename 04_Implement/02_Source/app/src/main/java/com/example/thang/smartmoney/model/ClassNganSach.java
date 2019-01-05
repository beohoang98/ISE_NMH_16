package com.example.thang.smartmoney.model;

import android.content.ContentValues;
import android.content.Context;

import com.example.thang.smartmoney.database.DBNganSach;
import com.example.thang.smartmoney.xulysukien.DateFormat;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public class ClassNganSach {
    private int id;
    private String name;
    private Date ngayBD; // start date
    private Date ngayKT; // end date
    private int soTien;

    private ClassNganSach(int id, String name, int soTien, Date ngayBD, Date ngayKT)
    {
        this.id = id;
        this.name = name;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.soTien = soTien;
    }

    private ClassNganSach(ContentValues values)
            throws ParseException
    {
        this.id = values.getAsInteger("id");
        this.name = values.getAsString("name");
        this.soTien = values.getAsInteger("so_tien");
        this.ngayBD = DateFormat.parse(values.getAsString("ngay_bd"));
        this.ngayKT = DateFormat.parse(values.getAsString("ngay_kt"));
    }

    public List<ClassExpense> getChiTieu(Context context) {
        return DBNganSach.getInstance(context).getChiTieu(this.id);
    }

    public static ClassNganSach create(String name, int soTien, Date ngayBD, Date ngayKT) {
        return new ClassNganSach(-1, name, soTien, ngayBD, ngayKT);
    }

    public static ClassNganSach from(ContentValues values) {
        try {
            return new ClassNganSach(values);
        } catch (ParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ContentValues getContentValue() {
        ContentValues values = new ContentValues();
        values.put("id", this.id);
        values.put("name", this.name);
        values.put("so_tien", this.soTien);
        values.put("ngay_bd", DateFormat.format(this.ngayBD));
        values.put("ngay_kt", DateFormat.format(this.ngayKT));
        return values;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getNgayBD() {
        return ngayBD;
    }

    public Date getNgayKT() {
        return ngayKT;
    }

    public int getSoTien() {
        return soTien;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNgayBD(Date ngayBD) {
        this.ngayBD = ngayBD;
    }

    public void setNgayKT(Date ngayKT) {
        this.ngayKT = ngayKT;
    }

    public void setSoTien(int soTien) {
        this.soTien = soTien;
    }
}
