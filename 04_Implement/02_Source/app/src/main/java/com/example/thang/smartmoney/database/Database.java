package com.example.thang.smartmoney.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.thang.smartmoney.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Database extends SQLiteOpenHelper {
    private static final String DBNAME = "SmartMoney.sqlite";
    private static final int DBVERSION = 3;
    private static Database mInstance = null;
    private Context mContext;

    public Database(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.mContext = context;
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
        InitDB(db, R.raw.db);
        InitDB(db, R.raw.initdb);
    }

    void InitDB(SQLiteDatabase db, int resID) {
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
                    db.execSQL(smt);
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
        if (newVersion > oldVersion)
        {
            Log.d("database", "Upgrade from version " + oldVersion + " to " + newVersion);

            try {
                db.execSQL("DROP TABLE IF EXISTS giaodich");
                db.execSQL("DROP TABLE IF EXISTS category");
                db.execSQL("DROP TABLE IF EXISTS vi");
                db.execSQL("DROP TABLE IF EXISTS ngan_sach");

                Log.d("database", "Upgrade success");

                onCreate(db);
            } catch (SQLException e)
            {
                Log.e("database", "Upgrade fail: " + e.getMessage());
            }

        }
    }
}
