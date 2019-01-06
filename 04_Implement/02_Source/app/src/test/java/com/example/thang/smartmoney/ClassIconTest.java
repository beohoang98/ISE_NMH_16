package com.example.thang.smartmoney;

import android.content.Context;
import android.graphics.Picture;
import android.widget.ImageView;

import com.caverock.androidsvg.SVGParseException;
import com.example.thang.smartmoney.database.DBVi;
import com.example.thang.smartmoney.database.Database;
import com.example.thang.smartmoney.model.ClassIcon;
import com.example.thang.smartmoney.model.ClassVi;
import com.squareup.picasso.Picasso;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.shadows.ShadowApplication;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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

    @Test
    public void testDownloadIcon()
    {
        ClassIcon.load(mCtx);
        String name = ClassIcon.list.get(0);
        String url = ClassIcon.getUrlAt(0);
        ClassIcon.downloadIcon(name, url);

        File icon = new File(mCtx.getFilesDir(), name + ".svg");
        if (icon.exists()) {
            icon.delete();
        } else {
            Assert.fail("icon khong ton tai");
        }
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
