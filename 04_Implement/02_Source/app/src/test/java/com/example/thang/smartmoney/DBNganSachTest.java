package com.example.thang.smartmoney;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.DBNganSach;
import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.model.ClassExpense;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassNganSach;
import com.example.thang.smartmoney.xulysukien.DateFormat;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;


@RunWith(RobolectricTestRunner.class)
public class DBNganSachTest {

    Context context;
    static DBNganSach database;
    static SQLiteDatabase dbOther;

    @Before
    public void setUp() {
        Database.initForTest();

        context = ApplicationProvider.getApplicationContext();

        database = DBNganSach.getInstance(context);
        DBGiaoDich.init(context);
        dbOther = Database.getInstance(context).getReadableDatabase();
    }

    @After
    public void tearDown() {
        database.close();
        dbOther.close();
        DBGiaoDich.close();
    }

    @Test
    public void testCreate() throws ParseException {
        Date ngayBD = DateFormat.parse("15/10/2018");
        Date ngayKT = DateFormat.parse("06/01/2019");
        ClassNganSach nganSach = ClassNganSach.create("test", 1000000, ngayBD, ngayKT);

        int id = database.create(nganSach);
        if (id < 0) Assert.fail("id < 0");

        Cursor cursor = dbOther.rawQuery("SELECT * FROM " + Database.TABLE.NganSach, null);
        if (cursor.getCount() <= 0) Assert.fail("Khong co trong database");
    }

    @Test
    public void testGet() throws ParseException {
        Date ngayBD = DateFormat.parse("15/10/2018");
        Date ngayKT = DateFormat.parse("06/01/2019");
        ClassNganSach nganSach = ClassNganSach.create("test", 1000000, ngayBD, ngayKT);

        int id = database.create(nganSach);

        ClassNganSach result = database.getNganSach(id);
        ContentValues expectValues = nganSach.getContentValue();

        if (id != result.getId()) {
            Assert.fail();
        }
        expectValues.put("id", id);

        Assert.assertEquals(expectValues, result.getContentValue());
    }

    @Test
    public void testUpdate() throws ParseException {
        Date ngayBD = DateFormat.parse("15/10/2018");
        Date ngayKT = DateFormat.parse("06/01/2019");
        ClassNganSach nganSach = ClassNganSach.create("test", 1000000, ngayBD, ngayKT);

        int id = database.create(nganSach);
        nganSach.setId(id);

        nganSach.setSoTien(500000);
        nganSach.setName("test-edited");
        int rows = database.update(nganSach);
        Assert.assertTrue(rows > 0);

        ClassNganSach result = database.getNganSach(id);
        Assert.assertTrue(result.getName().equals("test-edited") && result.getSoTien() == 500000);
    }

    @Test
    public void testDelete() throws ParseException {
        ClassNganSach nganSach = prepareNganSach();

        int id = database.create(nganSach);

        database.xoa(id);

        ClassNganSach result = database.getNganSach(id);
        Assert.assertNull(result);
    }

    @Test
    public void testDetectGiaoDich() throws ParseException {
        ClassNganSach nganSach = prepareNganSach();

        int id = database.create(nganSach);

        List<ClassGiaoDich> emptyList = database.getChiTieu(id);
        Assert.assertTrue(emptyList.size() == 0);

        List<ClassExpense> listBanDau = prepareListGiaoDich();
        for (ClassExpense expense : listBanDau) {
            DBGiaoDich.them(expense);
        }

        List<ClassGiaoDich> listCoDuoc = DBGiaoDich.getTatCa();
        Assert.assertEquals("error in create test",5, listCoDuoc.size());

        List<ClassGiaoDich> listResult = database.getChiTieu(id);
        Assert.assertEquals("Giao dich cua ngan sach khong chinh xac", 4, listResult.size());
    }

    @Test
    public void testTongChiNganSach() throws ParseException {
        ClassNganSach nganSach = prepareNganSach();
        int id = database.create(nganSach);

        List<ClassExpense> listBanDau = prepareListGiaoDich();
        for (ClassExpense expense : listBanDau) {
            DBGiaoDich.them(expense);
        }

        List<ClassGiaoDich> listCoDuoc = DBGiaoDich.getTatCa();
        Assert.assertEquals("error in create test",5, listCoDuoc.size());

        int tongChi = database.getTongChi(id, null);
        Assert.assertEquals(391000, tongChi);
    }

    @Test
    public void testTongChiTheoNgay() throws ParseException
    {
        ClassNganSach nganSach = prepareNganSach();
        int id = database.create(nganSach);

        List<ClassExpense> listBanDau = prepareListGiaoDich();
        for (ClassExpense expense : listBanDau) {
            DBGiaoDich.them(expense);
        }

        List<ClassGiaoDich> listCoDuoc = DBGiaoDich.getTatCa();
        Assert.assertEquals("error in create test",5, listCoDuoc.size());

        int tongChi = database.getTongChi(id, DateFormat.parse("15/11/2018"));
        Assert.assertEquals("incorrect in result", 300000, tongChi);
    }

    @Test
    public void testGetFinished() throws  ParseException {
        Date testDate = DateFormat.parse("15/12/2018");

        database.create(prepareNganSach("15/10/2018", "15/11/2018"));
        database.create(prepareNganSach("15/11/2018", "15/12/2018"));
        database.create(prepareNganSach("25/10/2018", "25/12/2018"));
        database.create(prepareNganSach("15/07/2018", "15/08/2018"));
        database.create(prepareNganSach("15/07/2018", "01/01/2019"));

        List<ClassNganSach> nganSachList = database.getNganSachFinished(testDate);
        Assert.assertEquals(2, nganSachList.size());
    }

    @Test
    public void testGetNotFinished() throws  ParseException {
        Date testDate = DateFormat.parse("15/12/2018");

        database.create(prepareNganSach("15/10/2018", "15/11/2018"));
        database.create(prepareNganSach("15/11/2018", "15/12/2018"));
        database.create(prepareNganSach("25/10/2018", "25/12/2018"));
        database.create(prepareNganSach("15/07/2018", "15/08/2018"));
        database.create(prepareNganSach("15/07/2018", "01/01/2019"));

        List<ClassNganSach> nganSachList = database.getNganSachNotFinish(testDate);
        Assert.assertEquals(3, nganSachList.size());
    }

    List<ClassExpense> prepareListGiaoDich() throws ParseException {
        ArrayList<ClassExpense> list = new ArrayList<>();

        // 5 cai
        // 4 cai hop le
        // tong chi 391000
        list.add(new ClassExpense(DateFormat.parse("14/10/2018"), 100000, 1, null));
        list.add(new ClassExpense(DateFormat.parse("15/10/2018"), 200000, 2, null));
        list.add(new ClassExpense(DateFormat.parse("11/11/2018"), 100000, 3, null));
        list.add(new ClassExpense(DateFormat.parse("31/12/2018"), 50000, 2, null));
        list.add(new ClassExpense(DateFormat.parse("06/01/2019"), 41000, 1, null));

        return list;
    }

    ClassNganSach prepareNganSach() throws ParseException
    {
        return prepareNganSach(null, null);
    }

    ClassNganSach prepareNganSach(@Nullable String start, @Nullable String end) throws ParseException {
        if (start == null) start = "15/10/2018";
        if (end == null) end = "06/01/2019";

        Date ngayBD = DateFormat.parse(start);
        Date ngayKT = DateFormat.parse(end);
        return ClassNganSach.create("Thang 10-12", 1000000, ngayBD, ngayKT);
    }


    /**
     * FIX ATTEMPT RE-OPEN ALREADY-CLOSED....
     * https://stackoverflow.com/questions/30308776/robolectric-accessing-database-throws-an-error
     *
     * P/s: Don't understand why?
     */

    @After
    public void finishComponentTesting() {
        resetSingleton(Database.class, "mInstance");
    }

    private void resetSingleton(Class clazz, String fieldName) {
        Field instance;
        try {
            instance = clazz.getDeclaredField(fieldName);
            instance.setAccessible(true);
            instance.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
