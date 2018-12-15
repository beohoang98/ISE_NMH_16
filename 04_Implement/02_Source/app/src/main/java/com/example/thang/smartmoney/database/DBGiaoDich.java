package com.example.thang.smartmoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.xulysukien.DateFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DBGiaoDich {
    protected static SQLiteDatabase db;
    static Context ctx;

    public static void init(Context _ctx) {
        if (ctx == null) ctx = _ctx;
        if (db == null) db = Database.getInstance(ctx).getWritableDatabase();
    }

    public static void close() {
        db.close();
        db = null;
    }

    protected static SQLiteDatabase getDB() {
        if (db == null) db = Database.getInstance(ctx).getWritableDatabase();
        return db;
    }

    /**
     *
     * @param {ClassGiaoDich} giao dich
     * @return {int} tra ve id vua moi duoc insert
     */
    public static int them(ClassGiaoDich gd) {
        try {
            ContentValues newVal = gd.getContentValues();
            // them vao thi bo id ra
            newVal.remove("id");

            long id = getDB().insert("giaodich", null, newVal);

            return (int)id;
        } catch (SQLException e) {
            return -1;
        }
    }

    protected static ArrayList<ClassGiaoDich> cursorToArray(Cursor c) {
        ArrayList<ClassGiaoDich> res = new ArrayList<>();
        if (c.getCount() == 0) return res;

        while (c.moveToNext()) {
            ContentValues val = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(c, val);
            ClassGiaoDich gd = new ClassGiaoDich(val);
            res.add(gd);
        }
        return res;
    }

    public static ArrayList<ClassGiaoDich> getTatCa() {
        Cursor cursor = getDB().rawQuery("SELECT * FROM giaodich ORDER BY thoi_gian DESC", null);
        return cursorToArray(cursor);
    }


    public static ArrayList<ClassGiaoDich> getByCategory(String category_name) {
        Cursor cursor = getDB().rawQuery("SELECT giaodich.* FROM giaodich, category WHERE giaodich.category_id = category.id", null);
        return cursorToArray(cursor);
    }

    public static ArrayList<ClassGiaoDich> getByDate(Date date) {
        String dateStr = DateFormat.format(date);
        Cursor cursor = getDB().rawQuery("SELECT * FROM giaodich WHERE thoi_gian = '" + dateStr + "'", null);

        Log.d("query", dateStr + " : " + cursor.getCount());

        return cursorToArray(cursor);
    }

    /**
     *
     * @param id id cua giao dich do
     * @return ClassGiaoDich ung voi id do
     */
    public static ClassGiaoDich getById(int id) {
        Cursor cursor = getDB().rawQuery("SELECT * FROM giaodich WHERE id = " + id, null);
        ArrayList<ClassGiaoDich> res = cursorToArray(cursor);
        if (res.size() == 0)
            return null;
        return res.get(0);
    }

    public static ArrayList<ClassGiaoDich> getByMonth(int month, int year) {
        String yearStr = "" + year;
        String monthStr = (month > 9) ? ("" + month) : ("0" + month);
        String pattern = "'%/" + monthStr + "/" + yearStr + "'";

        Cursor cursor = getDB().rawQuery("SELECT * FROM giaodich WHERE thoi_gian LIKE " + pattern, null);
        return cursorToArray(cursor);
    }

    /**
     * update CLassGiaoDich gd khi da duoc chinh sua
     * @param gd
     * @return
     */
    public static int update(ClassGiaoDich gd) {
        ContentValues val = gd.getContentValues();
        val.remove("id"); // bo id ra, khong keo bi loi~
        return getDB().update("giaodich", val, "id = ?", new String[]{ gd.id + "" });
    }

    /**
     *
     * @param id id cua giao dich
     * @return {true} neu xoa thanh cong, {false} neu xoa that bai
     */
    public static boolean xoa(int id) {
        String[] args = {id + ""};
        return getDB().delete("giaodich", "id = ?", args) > 0;
    }
}
