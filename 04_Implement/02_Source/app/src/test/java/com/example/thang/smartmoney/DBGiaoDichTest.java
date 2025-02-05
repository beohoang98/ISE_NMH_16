package com.example.thang.smartmoney;

import android.content.Context;
import android.database.Cursor;

import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.xulysukien.DateFormat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import junit.framework.TestCase;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class DBGiaoDichTest extends TestCase {
    Calendar cal;
    SQLiteDatabase dbOther;
    Context ctx;

    @Before
    public void setUp()
    {
        ctx = ApplicationProvider.getApplicationContext();
//        ctx = InstrumentationRegistry.getTargetContext();
        Database.initForTest();
        DBGiaoDich.init(ctx);
        cal = Calendar.getInstance();
        dbOther = Database.getInstance(ctx).getWritableDatabase();
    }

    @After
    public void tearDown()
    {
        DBGiaoDich.close();
        dbOther.close();
    }

    @Test
    public void testThemGiaoDich()
    {
        ClassGiaoDich gd = new ClassGiaoDich(cal.getTime(), 1000, 0, "test-123456", 0, 0);
        gd.id = DBGiaoDich.them(gd);
        Cursor cursor = dbOther.query("giaodich", null, "note = ?", new String[]{"test-123456"}, null, null, null);
        if (cursor == null || cursor.getCount() == 0)
        {
            Assert.fail();
        } else {
            cursor.moveToFirst();
            int id = cursor.getInt(0);

            if (id != gd.id) {
                Assert.fail();
            }
        }
    }

    @Test
    public void testGetGiaoDich()
    {
        ClassGiaoDich gd1 = new ClassGiaoDich(cal.getTime(), 1000, 1, "test-1", 1, 0);
        ClassGiaoDich gd2 = new ClassGiaoDich(cal.getTime(), 2000, 2, "test-2", 0, 1);
        gd1.id = DBGiaoDich.them(gd1);
        gd2.id = DBGiaoDich.them(gd2);

        ArrayList<ClassGiaoDich> list = DBGiaoDich.getByDate(cal.getTime());
        ClassGiaoDich[] arr = list.toArray(new ClassGiaoDich[list.size()]);

        Assert.assertTrue(Arrays.binarySearch(arr, gd1) > -1 && Arrays.binarySearch(arr, gd2) > -1);
    }

    @Test
    public void testGetGiaoDichById()
    {
        ClassGiaoDich gd = new ClassGiaoDich(cal.getTime(), 10000, 1, "test-1", 1, 0);
        gd.id = DBGiaoDich.them(gd);

        Assert.assertNotNull(DBGiaoDich.getById(gd.id));
    }

    @Test
    public void testGetGiaoDichById2()
    {
        ClassGiaoDich gd = new ClassGiaoDich(cal.getTime(), 10000, 1, "test-1", 1, 0);
        gd.id = DBGiaoDich.them(gd);

        ClassGiaoDich gd2 = (DBGiaoDich.getById(gd.id));
        Assert.assertEquals(gd2, gd);
    }

    @Test
    public void testUpdateGiaoDich()
    {
        ClassGiaoDich gd = new ClassGiaoDich(cal.getTime(), 10000, 1, "test-1", 1, 0);
        gd.id = DBGiaoDich.them(gd);

        gd.sotien = 50000;
        gd.category_id = 2;
        gd.to_id = 3;


        Assert.assertEquals(1, DBGiaoDich.update(gd));
    }

    @Test
    public void testUpdateGiaoDich2()
    {
        ClassGiaoDich gd = new ClassGiaoDich(cal.getTime(), 10000, 1, "test-1", 1, 0);
        gd.id = DBGiaoDich.them(gd);

        gd.sotien = 50000;
        gd.category_id = 2;
        gd.to_id = 3;

        DBGiaoDich.update(gd);

        Assert.assertEquals(gd, DBGiaoDich.getById(gd.id));
    }

    @Test
    public void testDeleteGiaoDich()
    {
        ClassGiaoDich gd = new ClassGiaoDich(cal.getTime(), 10000, 1, "test-1", 1, 0);
        gd.id = DBGiaoDich.them(gd);

        Assert.assertTrue(DBGiaoDich.xoa(gd.id));
    }

    @Test
    public void testDeleteGiaoDich2()
    {
        ClassGiaoDich gd = new ClassGiaoDich(cal.getTime(), 10000, 1, "test-1", 1, 0);
        gd.id = DBGiaoDich.them(gd);

        DBGiaoDich.xoa(gd.id);

        Assert.assertNull(DBGiaoDich.getById(gd.id));
    }

    @Test
    public void testGetGDByMonth() throws ParseException {
        Date date = DateFormat.parse("30/4/1975");
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1; // wtf january in Java start at 0
        int year = cal.get(Calendar.YEAR);
        Log.d("test", "" + month + "/" + year);

        ClassGiaoDich gd = new ClassGiaoDich(date, 10000, 1, "test-1", 1, 0);
        DBGiaoDich.them(gd);
        DBGiaoDich.them(gd);
        DBGiaoDich.them(gd);
        DBGiaoDich.them(gd);
        // 4

        ArrayList<ClassGiaoDich> list = DBGiaoDich.getByMonth(month, year);
        Assert.assertEquals(4, list.size());
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
