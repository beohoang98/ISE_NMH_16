package com.example.thang.smartmoney.model;

import android.content.ContentValues;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClassGiaoDich {
    public int id;
    public String thoigian;
    public int sotien;
    public int category_id;
    public String note;
    public int from_id;
    public int to_id;

    public ClassGiaoDich(ContentValues val) {
        this.id = val.getAsInteger("id");
        this.thoigian = val.getAsString("thoi_gian");
        this.sotien = val.getAsInteger("so_tien");
        this.category_id = val.getAsInteger("category_id");
        this.note = val.getAsString("note");
        this.from_id = val.getAsInteger("from_id");
        this.to_id = val.getAsInteger("from_id");
    }

    public ClassGiaoDich(Date thoigian, int sotien, int category_id, String note, int from_id, int to_id) {
        this.thoigian = new SimpleDateFormat("dd/MM/yyyy").format(thoigian);
        this.sotien = sotien;
        this.category_id = category_id;
        this.note = note;
        this.from_id = from_id;
        this.to_id = to_id;
    }

    public ContentValues getContentValues() {
        ContentValues val = new ContentValues();
        val.put("thoi_gian", thoigian);
        val.put("so_tien", sotien);
        val.put("category_id", category_id);
        val.put("note", note);

        val.put("from_id", from_id);
        val.put("to_id", to_id);

        return val;
    }
}
