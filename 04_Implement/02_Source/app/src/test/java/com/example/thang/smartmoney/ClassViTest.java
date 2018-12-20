package com.example.thang.smartmoney;

import android.content.Context;
import com.example.thang.smartmoney.database.DBVi;
import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.model.ClassVi;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import java.util.Calendar;

@RunWith(RobolectricTestRunner.class)
public class ClassViTest {

    Calendar cal;
    Context mCtx;

    @Before
    public void setUp()
    {
        cal = Calendar.getInstance();
        mCtx = RuntimeEnvironment.application.getApplicationContext();
        Database.initForTest();
        DBVi.init(mCtx);
    }

    @After
    public void tearDown()
    {
        DBVi.close();
    }

    @Test
    public void testKhoiTao()
    {
        ClassVi.loadFromDB(mCtx);
        Assert.assertNotNull(ClassVi.list);
    }

    @Test
    public void testGetData()
    {
        ClassVi.loadFromDB(mCtx);
        ClassVi Vi = ClassVi.list.get(0);
        Assert.assertTrue((Vi != null) && Vi.id == ClassVi.VI_CHINH_ID && Vi.name.equals("Vi chinh"));
    }


}
