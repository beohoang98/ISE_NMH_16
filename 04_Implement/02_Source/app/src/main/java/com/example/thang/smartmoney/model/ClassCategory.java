package com.example.thang.smartmoney.model;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.ahmadrosid.svgloader.SvgDecoder;
import com.ahmadrosid.svgloader.SvgLoader;
import com.ahmadrosid.svgloader.SvgParser;
import com.bumptech.glide.Glide;
import com.caverock.androidsvg.SVGExternalFileResolver;
import com.caverock.androidsvg.SVGParser;
import com.example.thang.smartmoney.database.Database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassCategory {
    public static List<ClassCategory> list;
    private static SQLiteDatabase db;
    private static Context context;

    public enum CATEGORY_TYPE {
        INCOME, EXPENSE, SAVING
    } // saving la tiet kiem

    public int id;
    public CATEGORY_TYPE type;
    public String name;
    public String icon_url;

    public ClassCategory(ContentValues val) {
        id = val.getAsInteger("id");
        type = CATEGORY_TYPE.values()[val.getAsInteger("type")];
        name = val.getAsString("name");
        icon_url = val.getAsString("icon_url");
    }

    public static void loadFromDB(Context ctx) {
        context = ctx;

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

    public static int getIdOfName(String name)
    {
        for (ClassCategory cate : list) {
            if (cate.name.equals(name))
                return cate.id;
        }

        return -1;
    }

    public static ArrayList<ClassCategory> getByType(CATEGORY_TYPE type)
    {
        ArrayList<ClassCategory> res = new ArrayList<>();
        for (ClassCategory cate : list) {
            if (cate.type == type) {
                res.add(cate);
            }
        }
        return res;
    }

    public static long add(String name, ClassCategory.CATEGORY_TYPE type, String icon_url)
    {
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("type", type.ordinal());
        values.put("icon_url", icon_url);
        return db.insertWithOnConflict("category", null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public static long delete(int id) {
        return db.delete("category", "id =?", new String[] {"" + id});
    }

    public static ClassCategory getById(long id) {
        for (ClassCategory cate : list) {
            if (cate.id == id)
                return cate;
        }
        return null;
    }
}