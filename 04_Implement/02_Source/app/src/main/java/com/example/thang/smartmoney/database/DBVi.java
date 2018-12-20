package com.example.thang.smartmoney.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.thang.smartmoney.xulysukien.DateFormat;

import java.util.Date;

public class DBVi {
    private static SQLiteDatabase db;
    private static Context mCtx;

    public static void init(Context ctx)
    {
        if (mCtx == null) mCtx = ctx.getApplicationContext();
        db = getDb();
    }

    private static SQLiteDatabase getDb()
    {
        if (db == null) db = Database.getInstance(mCtx).getWritableDatabase();
        return db;
    }

    public static int getSoDu()
    {
        return getTongIncome() - getTongOutcome();
    }

    public static int getTongIncome()
    {
        Cursor cursor = db.rawQuery("SELECT SUM(so_tien) FROM giaodich WHERE from_id = 0;", null);
        if (!cursor.moveToFirst())
            return 0;

        return cursor.getInt(0);
    }

    public static int getTongOutcome()
    {
        Cursor cursor = db.rawQuery("SELECT SUM(so_tien) FROM giaodich WHERE to_id = 0;", null);
        if (!cursor.moveToFirst())
            return 0;

        return cursor.getInt(0);
    }

    public static int getTongIncomeByDate(Date date)
    {
        String dateStr = DateFormat.format(date);
        String[] args = { dateStr };
        Cursor cursor = db.rawQuery("SELECT SUM(so_tien) FROM giaodich WHERE from_id = 0 AND thoi_gian =?", args);
        if (!cursor.moveToFirst())
            return 0;

        return cursor.getInt(0);
    }

    public static int getTongOutcomeByDate(Date date)
    {
        String dateStr = DateFormat.format(date);
        String[] args = {dateStr};
        Cursor cursor = db.rawQuery("SELECT SUM(so_tien) FROM giaodich WHERE to_id = 0 AND thoi_gian =?", args);
        if (!cursor.moveToFirst())
            return 0;

        return cursor.getInt(0);
    }

    public static void close() {
        db.close();
    }
}
