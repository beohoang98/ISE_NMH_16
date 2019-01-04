package com.example.thang.smartmoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.thang.smartmoney.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static String DBNAME = "SmartMoney.sqlite";
    public static final String DBNAME_TEST = "SmartMoney.sqlite.test";

    private static int DBVERSION = 4; // them so tiet kiem
    public static final int TESTVERSION = 1;

    private static Database mInstance = null;
    private Context mContext;

    public Database(Context context) {
        super(context, DBNAME, null, DBVERSION);
        this.mContext = context;
    }

    /**
     * DUng de khoi tao cho unit test, tao database rieng va version rieng
     */
    public static void initForTest() {
        DBNAME = DBNAME_TEST;
        DBVERSION = TESTVERSION;
    }

    public static Database getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new Database(ctx);
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

    static public List<ContentValues> cursorToContentValues(Cursor c) {
        ArrayList<ContentValues> list = new ArrayList<>();
        if (!c.moveToFirst()) return list;

        do {
            ContentValues values = new ContentValues();
            DatabaseUtils.cursorRowToContentValues(c, values);
            list.add(values);
        } while (c.moveToNext());

        return list;
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
                if (chars[0] == '\n' || chars[0] == '\t') chars[0] = ' ';
                smt += chars[0];

                if (chars[0] == ';') {
                    Log.d("initdb", smt);
                    SQLiteStatement statement = db.compileStatement(smt);
                    statement.execute();
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
        if (newVersion > oldVersion || newVersion == TESTVERSION)
        {
            Log.d("database", "Upgrade from version " + oldVersion + " to " + newVersion);

            try {
                db.execSQL("DROP TABLE IF EXISTS " + TABLE.GiaoDich);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE.Category);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE.Vi);
                db.execSQL("DROP TABLE IF EXISTS " + TABLE.NganSach);

                Log.d("database", "Clear old database");
                onCreate(db);
                Log.d("database", "Update success database");
            } catch (SQLException e)
            {
                Log.e("database", "Upgrade fail: " + e.getMessage());
            }
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public abstract class TABLE {
        public static final String GiaoDich = "giaodich";
        public static final String Category = "category";
        public static final String NganSach = "ngan_sach";
        public static final String Vi = "vi";
    }
}
