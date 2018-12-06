package com.example.thang.smartmoney.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.thang.smartmoney.database.Database;

import java.util.ArrayList;

public class ClassCategory {
    public static ArrayList<ClassCategory> list;
    private static SQLiteDatabase db;

    public int id;
    public int type;
    public String name;
    public String icon_url;

    public ClassCategory(ContentValues val) {
        id = val.getAsInteger("id");
        type = val.getAsInteger("type");
        name = val.getAsString("name");
        icon_url = val.getAsString("icon_url");
    }

    public static void loadFromDB(Context ctx) {
        if (db == null) {
            db = Database.getInstance(ctx).getWritableDatabase();
        }
        if (list == null) {
            list = new ArrayList<>();
        }

        Cursor cursor = db.rawQuery("SELECT * FROM category", null);
        list.clear();
        while (cursor.moveToNext()) {
            ContentValues val = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(cursor, val);
            list.add(new ClassCategory(val));
        }
    }

    public static String getName(int id) {
        for (ClassCategory cate : list) {
            if (cate.id == id)
                return cate.name;
        }

        return null;
    }
}