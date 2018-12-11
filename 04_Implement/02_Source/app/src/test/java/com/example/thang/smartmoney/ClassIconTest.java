package com.example.thang.smartmoney;

import android.content.Context;
import com.example.thang.smartmoney.database.DBVi;
import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.model.ClassIcon;
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
public class ClassIconTest {

    Context mCtx;

    @Before
    public void setUp()
    {
        mCtx = RuntimeEnvironment.application.getApplicationContext();
    }

    @After
    public void tearDown()
    {

    }

    @Test
    public void testKhoiTao()
    {
        ClassIcon.load(mCtx);
    }
}
