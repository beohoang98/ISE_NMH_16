package com.example.thang.smartmoney.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import com.example.thang.smartmoney.model.ClassExpense;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassNganSach;
import com.example.thang.smartmoney.model.ClassVi;
import com.example.thang.smartmoney.xulysukien.DateFormat;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBNganSach {

    private Context context;
    private SQLiteDatabase dbinstance;

    private DBNganSach(@NotNull Context ctx)
    {
        context = ctx;
        dbinstance = Database.getInstance(ctx).getWritableDatabase();
    }

    static public DBNganSach getInstance(Context ctx) {
        return new DBNganSach(ctx);
    }

    public List<ClassNganSach> getAllNganSach()
    {
        List<ClassNganSach> list = new ArrayList<>();
        Cursor cursor = dbinstance.rawQuery("SELECT * FROM " + Database.TABLE.NganSach, null);
        List<ContentValues> values = Database.cursorToContentValues(cursor);
        for (ContentValues value : values) {
            list.add(ClassNganSach.from(value));
        }
        cursor.close();

        return list;
    }

    public List<ClassNganSach> getNganSachNotFinish(Date currentDate)
    {
        List<ClassNganSach> all = getAllNganSach();
        List<ClassNganSach> result = new ArrayList<>();
        for (ClassNganSach nganSach : all) {
            if (!nganSach.getNgayKT().before(currentDate)) {
                result.add(nganSach);
            }
        }
        all.clear();
        return result;
    }

    public List<ClassNganSach> getNganSachFinished(Date currentDate)
    {
        List<ClassNganSach> all = getAllNganSach();
        List<ClassNganSach> result = new ArrayList<>();
        for (ClassNganSach nganSach : all) {
            if (nganSach.getNgayKT().before(currentDate)) {
                result.add(nganSach);
            }
        }
        all.clear();
        return result;
    }

    public ClassNganSach getNganSach(int id)
    {
        Cursor cursor = dbinstance.rawQuery("SELECT * FROM " + Database.TABLE.NganSach + " WHERE id = " + id, null);
        if (!cursor.moveToFirst()) return null;

        ContentValues values = new ContentValues();
        DatabaseUtils.cursorRowToContentValues(cursor, values);
        cursor.close();

        return ClassNganSach.from(values);
    }

    public List<ClassExpense> getChiTieu(int id)
    {
        List<ClassExpense> giaoDichList = new ArrayList<>();
        ClassNganSach nganSach = getNganSach(id);
        if (nganSach == null) return giaoDichList;

        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd", Locale.US);
        String ngayBD = fmt.format(nganSach.getNgayBD());
        String ngatKT = fmt.format(nganSach.getNgayKT());

        String query = String.format(Locale.ENGLISH,
                "SELECT gd.* FROM %s gd, %s ns WHERE ns.id = %d AND from_id = %d " +
                        "AND (substr(gd.thoi_gian, 7, 4) || substr(gd.thoi_gian,4,2) || substr(gd.thoi_gian,1,2)" +
                        " BETWEEN '%s' AND '%s')",
                Database.TABLE.GiaoDich, Database.TABLE.NganSach, id, ClassVi.VI_CHINH_ID, ngayBD, ngatKT);

        Cursor cursor = dbinstance.rawQuery(query, null);
        List<ContentValues> valuesList = Database.cursorToContentValues(cursor);
        cursor.close();

        for (ContentValues values : valuesList) {
            ClassExpense giaoDich = new ClassExpense(values);
            giaoDichList.add(giaoDich);
        }

        return giaoDichList;
    }

    public int getTongChi(int id, @Nullable Date currentDate) {
        List<ClassExpense> giaoDichList = getChiTieu(id);

        int tongChi = 0;
        for (ClassExpense giaoDich : giaoDichList) {
            if (currentDate == null || currentDate.after(giaoDich.ngay)) {
                tongChi += giaoDich.sotien;
            }
        }
        return tongChi;
    }

    /**
     *
     * @param nganSach
     * @return id vua moi tao, -1 khi khong the tao duoc
     */
    public int create(ClassNganSach nganSach) {
        ContentValues values = nganSach.getContentValue();
        values.remove("id");
        return (int)dbinstance.insert(Database.TABLE.NganSach, null, values);
    }

    public int update(ClassNganSach nganSach) {
        return dbinstance.update(Database.TABLE.NganSach, nganSach.getContentValue(),
                "id = " + nganSach.getId(), null);
    }

    public int xoa(int id) {
        return dbinstance.delete(Database.TABLE.NganSach, "id = " + id, null);
    }

    public void close() {
        dbinstance.close();
    }
}
