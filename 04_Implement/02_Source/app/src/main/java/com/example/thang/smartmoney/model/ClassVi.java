package com.example.thang.smartmoney.model;

import android.content.Context;
import android.database.Cursor;

import com.example.thang.smartmoney.database.Database;

import java.util.ArrayList;

public class ClassVi {

    public static ArrayList<ClassVi> list;
    public static Context mCtx;

    public static final int VI_CHINH_ID = 1;

    public int id;
    public String name;

    public static void loadFromDB(Context ctx)
    {
        if (mCtx == null) mCtx = ctx;
        if (list == null) list = new ArrayList<>();
        Cursor cursor = Database.getInstance(ctx).GetData("SELECT * FROM Vi");
        while (cursor.moveToNext()) {
            ClassVi vi = new ClassVi();
            vi.id = cursor.getInt(0);
            vi.name = cursor.getString(1);
            list.add(vi);
        }
    }

    public ClassVi() {

    }


}
