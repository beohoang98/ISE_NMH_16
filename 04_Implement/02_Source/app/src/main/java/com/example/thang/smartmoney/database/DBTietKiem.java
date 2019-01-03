package com.example.thang.smartmoney.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.Nullable;

import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassTietKiem;
import com.example.thang.smartmoney.model.ClassVi;

import java.util.ArrayList;
import java.util.Date;

public class DBTietKiem extends DBGiaoDich {

    public static int themTietKiem(Date date, int sotien, int viId, @Nullable String note)
    {
        ClassTietKiem tk = new ClassTietKiem(date, sotien, note, ClassTietKiem.TYPE.DEPOSIT, viId);
        return them(tk);
    }

    public static int rutTietKiem(Date date, int sotien, int fromViId)
    {
        ClassTietKiem tk = new ClassTietKiem(date, sotien, null, ClassTietKiem.TYPE.WITHDRAW, fromViId);
        return them(tk);
    }

    public static void xoaTietKiem(int gdTkId) throws RuntimeException
    {
        ClassTietKiem tk = findGiaoDichTietKiemById(gdTkId);
        if (tk == null) {
            throw new RuntimeException("Giao dich " + gdTkId + " khong phai la 1 gd tiet kiem");
        }
        xoa(gdTkId);
    }

    public static ClassTietKiem findGiaoDichTietKiemById(int gdTkId)
    {
        ClassGiaoDich gd = getById(gdTkId);
        if (gd == null || gd.from_id == 0 || gd.to_id == 0) {
            return null;
        }
        return new ClassTietKiem(gd);
    }

    /**
     *
     * @param name ten cua Vi
     * @return ViId vua duoc insert
     */
    public static long taoSoTietKiem(String name) {
        ContentValues vi = new ContentValues();
        vi.put("name", name);

        return getDB().insert("Vi", null, vi);
    }

    public static boolean xoaSoTietKiem(int soTkId) throws RuntimeException {
        if (soTkId == ClassVi.VI_CHINH_ID) {
            throw new RuntimeException("Khong the xoa Vi Chinh");
        }
        return getDB().delete("Vi", "id = ?", new String[]{"" + soTkId}) > 0;
    }

    /**
     *
     * @param soTkId id cua so tiet kiem
     * @param editedName ten muon sua
     * @return that bai hoac thanh cong
     */
    public static boolean suaTenSoTietKiem(int soTkId, String editedName)
    {
        ContentValues val = new ContentValues();
        val.put("name", editedName);
        return getDB().update("Vi", val, "id=?", new String[]{"" + soTkId}) > 0;
    }

    @Nullable
    public static ClassVi getSoTietKiem(int soTkId)
    {
        ClassVi vi = new ClassVi();
        Cursor cursor = getDB().rawQuery("SELECT * FROM Vi WHERE id = ?", new String[]{"" + soTkId});
        if (cursor == null || !cursor.moveToFirst()) {
            return null;
        }
        vi.id = cursor.getInt(0);
        vi.name = cursor.getString(1);
        cursor.close();

        return vi;
    }

    public static ArrayList<ClassGiaoDich> getGiaoDichBySoTietKiem(int soTkId)
    {
        Cursor cursor = getDB().rawQuery("SELECT * FROM giaodich WHERE (from_id = ? AND to_id = ?) OR (from_id = ? AND to_id = ?)",
                new String[]{ ClassVi.VI_CHINH_ID + "", "" + soTkId, "" + soTkId, "" + ClassVi.VI_CHINH_ID});
        return cursorToArray(cursor);
    }
}
