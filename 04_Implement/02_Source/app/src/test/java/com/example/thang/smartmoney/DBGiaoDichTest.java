package com.example.thang.smartmoney;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.os.Build;
import android.support.test.InstrumentationRegistry;

import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.model.ClassGiaoDich;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;


import android.database.sqlite.SQLiteDatabase;

import junit.framework.JUnit4TestAdapter;
import junit.framework.TestCase;

import java.io.Console;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

@RunWith(RobolectricTestRunner.class)
public class DBGiaoDichTest extends TestCase {
    Calendar cal;
    SQLiteDatabase dbOther;
    Context ctx;

    @Before
    public void setUp() throws Exception
    {
        ctx = RuntimeEnvironment.application.getApplicationContext();
        Database.initForTest();
        DBGiaoDich.init(ctx);
        cal = Calendar.getInstance();
        dbOther = Database.getInstance(ctx).getWritableDatabase();
    }

    @After
    public void tearDown() throws Exception
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

        assertTrue(Arrays.binarySearch(arr, gd1) > -1 && Arrays.binarySearch(arr, gd2) > -1);
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
}
