package com.example.thang.smartmoney.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.thang.smartmoney.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Database extends SQLiteOpenHelper {
    private static final String DBNAME = "SmartMoney.sqlite";
    private static final int DBVERSION = 1;
    private static Database mInstance = null;
    private Context mContext;

    public Database(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.mContext = context;

        InitDB(R.raw.db);
        InitDB(R.raw.initdb);
    }

    public static Database getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new Database(ctx.getApplicationContext());
        }
        return mInstance;
    }

    public void QueryData(String sql) {
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
    }

    public Cursor GetData(String sql) {
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    void InitDB(int resID) {
        InputStream is = mContext.getResources().openRawResource(resID);
        InputStreamReader isr = new InputStreamReader(is);
        String smt = "";
        char[] chars = new char[1];

        try {
            while (isr.ready()) {
                isr.read(chars);
                if (chars[0] == '\n') continue;

                smt += chars[0];

                if (chars[0] == ';') {
                    Log.d("initdb", smt);
                    QueryData(smt);
                    smt = "";
                }

                chars[0] = 0;
            }
        } catch (IOException e) {
            Log.e("initdb", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
