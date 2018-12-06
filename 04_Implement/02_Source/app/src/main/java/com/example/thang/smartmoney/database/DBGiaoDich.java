package com.example.thang.smartmoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.thang.smartmoney.model.ClassGiaoDich;

import java.util.ArrayList;
import java.util.Date;

public class DBGiaoDich {
    private static SQLiteDatabase db;

    public static void init(Context ctx) {
        if (db == null) db = Database.getInstance(ctx).getWritableDatabase();
    }

    public static boolean them(ClassGiaoDich gd) {
        try {
            ContentValues newVal = gd.getContentValues();
            long id = db.insert("giaodich", null, newVal);

            if (id > -1) return true;
            else return false;
        } catch (SQLException e) {
            return false;
        }
    }

    public static ArrayList<ClassGiaoDich> cursorToArray(Cursor c) {
        ArrayList<ClassGiaoDich> res = new ArrayList<>();
        if (c.getCount() == 0) return res;

        do {
            ContentValues val = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(c, val);
            ClassGiaoDich gd = new ClassGiaoDich(val);
            res.add(gd);
        } while (c.moveToNext());
        return res;
    }

    public static ArrayList<ClassGiaoDich> getTatCa() {
        Cursor cursor = db.rawQuery("SELECT * FROM giaodich ORDER BY thoi_gian DESC", null);
        return cursorToArray(cursor);
    }

    public static ArrayList<ClassGiaoDich> getByCategory(String category_name) {
        Cursor cursor = db.rawQuery("SELECT giaodich.* FROM giaodich, category WHERE giaodich.category_id = category.id", null);
        return cursorToArray(cursor);
    }

    public static ArrayList<ClassGiaoDich> getByDate(Date date) {
        Cursor cursor = db.rawQuery("SELECT giaodich.* FROM giaodich WHERE thoi_gian = '" + date.toString() + "'", null);
        return cursorToArray(cursor);
    }

    public static ClassGiaoDich getById(int id) {
        Cursor cursor = db.rawQuery("SELECT * FROM giaodich WHERE id = " + id, null);
        return cursorToArray(cursor).get(0);
    }

    public static ArrayList<ClassGiaoDich> getByMonth(int month, int year) {
        String[] args = {month + "", year + ""};
        Cursor cursor = db.rawQuery("SELECT * FROM giaodich WHERE strftime('%m', thoi_gian) = ? AND strftime('%y', thoi_gian) = ?", args);
        return cursorToArray(cursor);
    }


    public static boolean xoa(int id) {
        String[] args = {id + ""};
        int idDeleted = db.delete("giaodich", "id = ?", args);
        if (idDeleted > -1) return true;
        return false;
    }
}
