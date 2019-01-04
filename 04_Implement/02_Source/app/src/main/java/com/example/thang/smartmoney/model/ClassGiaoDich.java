package com.example.thang.smartmoney.model;

import android.content.ContentValues;
import android.support.annotation.Nullable;

import com.example.thang.smartmoney.xulysukien.DateFormat;

import java.text.ParseException;
import java.util.Date;

public class ClassGiaoDich implements Comparable {
    public int id;
    public String thoigian; // danh cho chuoi
    public Date ngay; // ngay that su
    public int sotien;
    public int category_id;
    public String note;
    public int from_id;
    public int to_id;

    public ClassGiaoDich(ContentValues val) {
        this.id = val.getAsInteger("id");
        this.thoigian = val.getAsString("thoi_gian");

        try {
            this.ngay = DateFormat.parse(this.thoigian);
        } catch (ParseException e) {
            //ignore
        }

        this.sotien = val.getAsInteger("so_tien");
        this.category_id = val.getAsInteger("category_id");
        this.note = val.getAsString("note");
        this.from_id = val.getAsInteger("from_id");
        this.to_id = val.getAsInteger("to_id");
    }

    public ClassGiaoDich(Date thoigian, int sotien, int category_id, String note, int from_id, int to_id) {
        this.thoigian = DateFormat.format(thoigian);
        this.ngay = thoigian;
        this.sotien = sotien;
        this.category_id = category_id;
        this.note = note;
        this.from_id = from_id;
        this.to_id = to_id;
    }

    public ClassGiaoDich(ClassGiaoDich other)
    {
        this(other.getContentValues());
    }

    public ContentValues getContentValues() {
        ContentValues val = new ContentValues();
        val.put("id", id);

        val.put("thoi_gian", thoigian);
        val.put("so_tien", sotien);
        val.put("category_id", category_id);
        val.put("note", note);

        val.put("from_id", from_id);
        val.put("to_id", to_id);

        return val;
    }

    @Override
    public boolean equals(Object _obj) {
        ClassGiaoDich obj = (ClassGiaoDich)_obj;
        return (id == obj.id)
                && (thoigian.equals(obj.thoigian))
                && (sotien == obj.sotien)
                && (note.equals(obj.note))
                && (category_id == obj.category_id)
                && (from_id == obj.from_id)
                && (to_id == obj.to_id);
    }

    @Override
    public int compareTo(Object o) {
        return (this.equals(o))?1:0;
    }
}
