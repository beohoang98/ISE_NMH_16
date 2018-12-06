package com.example.thang.smartmoney;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.example.thang.smartmoney.database.Database;

import java.util.ArrayList;

public class GhiChuDB {

    private Database db;

    public GhiChuDB(Context ctx) {
        this.db = Database.getInstance(ctx);
    }

    public void addGhiChu(String text, String date) {
        Log.d("info", text + " : " + date);
        db.QueryData("INSERT INTO GhiChuThuNhap VALUES(null,'" + text + "','" + date + "')");
    }

    public ArrayList<AddGhiChu> getGhiChuByDay(String date) {
        Log.d("info", date);

        Cursor cursor = db.GetData("SELECT * FROM GhiChuThuNhap WHERE DateTC = '" + date + "'");
        ArrayList<AddGhiChu> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            String ten = cursor.getString(1);
            int id = cursor.getInt(0);
            String datetime = cursor.getString(2);
            result.add(0, new AddGhiChu(id, ten, datetime));
        }

        return result;
    }

    public ArrayList<AddGhiChu> getAllGhiChu() {
        Cursor cursor = db.GetData("SELECT * FROM GhiChuThuNhap");
        ArrayList<AddGhiChu> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            String ten = cursor.getString(1);
            int id = cursor.getInt(0);
            String datetime = cursor.getString(2);
            result.add(0, new AddGhiChu(id, ten, datetime));
        }

        return result;
    }
}
