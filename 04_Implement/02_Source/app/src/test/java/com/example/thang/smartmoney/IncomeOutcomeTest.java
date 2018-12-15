package com.example.thang.smartmoney;

import android.content.ContentValues;

import com.example.thang.smartmoney.model.ClassExpense;
import com.example.thang.smartmoney.model.ClassGiaoDich;
import com.example.thang.smartmoney.model.ClassIncome;
import com.example.thang.smartmoney.model.ClassVi;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.Calendar;
import java.util.Date;

@RunWith(RobolectricTestRunner.class)
public class IncomeOutcomeTest {

    Calendar cal;

    @Before
    public void setUp()
    {
        cal = Calendar.getInstance();
    }

    @Before
    public void tearDown()
    {

    }

    @Test
    public void testKhoiTaoIncome()
    {
        Date date = cal.getTime();
        ClassIncome income = new ClassIncome(date, 10000, 1, "hello");

        if (income.from_id != 0 || income.to_id != ClassVi.VI_CHINH_ID) {
            Assert.fail();
        }
    }

    @Test
    public void testKhoiTaoOutcome()
    {
        Date date = cal.getTime();
        ClassExpense expense = new ClassExpense(date, 10000, 1, "world");

        if (expense.from_id != ClassVi.VI_CHINH_ID || expense.to_id != 0) {
            Assert.fail();
        }
    }
}
