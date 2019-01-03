package com.example.thang.smartmoney;

import android.content.ContentValues;

import com.example.thang.smartmoney.model.ClassNganSach;
import com.example.thang.smartmoney.xulysukien.DateFormat;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.text.ParseException;
import java.util.Date;

@RunWith(RobolectricTestRunner.class)
public class ClassNganSachTest extends TestCase {

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testConstruct() throws ParseException {
        Date ngayBD = DateFormat.parse("20/12/2018");
        Date ngayKT = DateFormat.parse("27/12/2018");
        ClassNganSach nganSach = ClassNganSach.create("test", 10000, ngayBD, ngayKT);

        Assert.assertEquals(-1, nganSach.getId());
        Assert.assertEquals("test", nganSach.getName());
        Assert.assertEquals(10000, nganSach.getSoTien());
        Assert.assertEquals("20/12/2018", DateFormat.format(nganSach.getNgayBD()));
        Assert.assertEquals("27/12/2018", DateFormat.format(nganSach.getNgayKT()));
    }

    @Test
    public void testGetContentValues() {

        ContentValues expected = new ContentValues();
        expected.put("id", 2);
        expected.put("name", "asdasd");
        expected.put("so_tien", 123456);
        expected.put("ngay_bd", "20/12/2018");
        expected.put("ngay_kt", "27/12/2018");

        ClassNganSach nganSach = ClassNganSach.from(expected);

        ContentValues actual = nganSach.getContentValue();

        Assert.assertEquals(expected, actual);
    }
}
