package com.example.thang.smartmoney.xulysukien;

import android.content.Context;
import android.widget.Toast;

import com.example.thang.smartmoney.R;

public class KiemTraInput {

    private Context mContext;

    public KiemTraInput(Context ctx)
    {
        mContext = ctx;
    }

    /**
     *
     * @return {resouce id} khi bi khong hop le
     * @return {-1} khi hop le
     */
    public boolean KiemTraGiaTien(int value)
    {
        if (value < 1000) {
            showError(R.string.price_nho_hon_1000);
        } else if (value % 1000 > 0) {
            showError(R.string.price_le);
        } else if (value < 0) {
            showError(R.string.price_null);
        } else {
            return true;
        }
        return false;
    }

    /**
     *
     * @param category_id id cua category tim duoc
     * @return {resouce id} khi khong hop le
     * @return -1 khi hop le
     */
    public boolean KiemTraCategory(int category_id)
    {
        if (category_id < 0)
        {
            showError(R.string.category_null);
        } else {
            return true;
        }
        return false;
    }

    public void showError(int stringId)
    {
        Toast.makeText(mContext, stringId, Toast.LENGTH_LONG).show();
    }
}
