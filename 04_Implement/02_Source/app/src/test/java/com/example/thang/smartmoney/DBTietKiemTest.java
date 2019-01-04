package com.example.thang.smartmoney;

import android.content.Context;

import com.example.thang.smartmoney.database.DBGiaoDich;
import com.example.thang.smartmoney.database.DBTietKiem;
import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassTietKiem;
import com.example.thang.smartmoney.model.ClassVi;
import com.example.thang.smartmoney.xulysukien.DateFormat;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.tools.ant.types.resources.comparators.Content;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.test.core.app.ApplicationProvider;

@RunWith(RobolectricTestRunner.class)
public class DBTietKiemTest extends TestCase {

    Calendar cal;

    @Before
    public void setUp() {
        cal = Calendar.getInstance();
        Context context = ApplicationProvider.getApplicationContext();
        DBTietKiem.init(context);
        DBGiaoDich.init(context);
    }

    @After
    public void tearDown() {
//        DBTietKiem.close();
        DBGiaoDich.close();
    }

    @Test
    public void testTaoSoTietKiem()
    {
        int id = (int)DBTietKiem.taoSoTietKiem("tiet-kiem-1");
        Assert.assertTrue("Sai id", id > ClassVi.VI_CHINH_ID);
    }

    @Test
    public void testGetSoTietKiem()
    {
        int id = (int)DBTietKiem.taoSoTietKiem("tiet-kiem-1");
        ClassVi viTietKiem = DBTietKiem.getSoTietKiem(id);

        assertEquals("Sai ten", "tiet-kiem-1", viTietKiem.name);
        assertTrue("Sai id",viTietKiem.id > ClassVi.VI_CHINH_ID);
    }

    @Test
    public void testXoaSoTietKiem()
    {
        int id = (int)DBTietKiem.taoSoTietKiem("tiet-kiem-1");
        DBTietKiem.xoaSoTietKiem(id);

        ClassVi soTietKiem = DBTietKiem.getSoTietKiem(id);
        assertNull(soTietKiem);
    }

    @Test
    public void testThemGDTietKiem()
    {
        int id = (int)DBTietKiem.taoSoTietKiem("tiet-kiem-1");
        DBTietKiem.xoaSoTietKiem(id);

        Date today = cal.getTime();
        int idGD = DBTietKiem.themTietKiem(today, 5000, id, "");

        assertTrue("Khong them duoc", idGD >= 0);

        ClassGiaoDich giaoDich = DBGiaoDich.getById(idGD);
        assertNotNull("khong ton tai giao dich", giaoDich);

        assertTrue("Khong phai gd tiet kiem", ClassTietKiem.isTietKiem(giaoDich));

        assertEquals(DateFormat.format(today), DateFormat.format(giaoDich.ngay));
        assertEquals(5000, giaoDich.sotien);
        assertEquals(idGD, giaoDich.id);
    }

    @Test
    public void testGetGiaoDichTietKiem()
    {
        int id = (int)DBTietKiem.taoSoTietKiem("tiet-kiem-1");
        DBTietKiem.xoaSoTietKiem(id);

        Date today = cal.getTime();
        DBTietKiem.themTietKiem(today, 5000, id, "1");
        DBTietKiem.themTietKiem(today, 15000, id, "2");
        DBTietKiem.themTietKiem(today, 25000, id, "3");
        DBTietKiem.themTietKiem(today, 35000, id, "4");

        List<ClassGiaoDich> list = DBTietKiem.getGiaoDichBySoTietKiem(id);
        assertEquals(4, list.size());
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
