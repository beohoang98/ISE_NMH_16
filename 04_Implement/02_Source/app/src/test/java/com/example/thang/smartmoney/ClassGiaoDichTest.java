package com.example.thang.smartmoney;

import android.content.ContentValues;

import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.xulysukien.DateFormat;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.Date;

@RunWith(RobolectricTestRunner.class)
public class ClassGiaoDichTest {
    Calendar cal;

    @Before
    public void setUp() {
        cal = Calendar.getInstance();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testCreateGiaoDich() {
        Date date = cal.getTime();
        String dateStr = DateFormat.format(date);
        ClassGiaoDich gd = new ClassGiaoDich(date, 10000, 1, "asd", 1, 0);

        if (gd.id != 0
                || !gd.thoigian.equals(dateStr)
                || gd.category_id != 1
                || !gd.note.equals("asd")
                || gd.from_id != 1
                || gd.to_id != 0) {
            Assert.fail();
        }
    }

    @Test
    public void testContentValues()
    {
        ContentValues val = new ContentValues();
        val.put("id", 10);
        val.put("thoi_gian", 20);
        val.put("so_tien", 30000);
        val.put("category_id", 10);
        val.put("note", "test asdasd");
        val.put("from_id", -1);
        val.put("to_id", -1000);

        ClassGiaoDich gd = new ClassGiaoDich(val);
        ContentValues newVal = gd.getContentValues();

        for (String key : val.keySet()) {
            if (!val.getAsString(key).equals(newVal.getAsString(key))) {
                Assert.fail();
                return;
            }
        }
    }
}
