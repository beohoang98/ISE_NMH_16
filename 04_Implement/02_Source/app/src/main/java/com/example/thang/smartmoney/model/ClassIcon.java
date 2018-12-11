package com.example.thang.smartmoney.model;

import android.content.Context;

import com.example.thang.smartmoney.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ClassIcon {
    public static List<String> list;
    public static String baseUrl;
    public static String type;

    public static void load(Context ctx) {
        if (list != null) return;

        baseUrl = ctx.getString(R.string.md_icon_baseurl);
        type = ctx.getResources().getStringArray(R.array.md_icon_type)[0];
        String jsonStr;
        list = new ArrayList<>();

        try {
            InputStream is = ctx.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            jsonStr = new String(buffer, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        try {
            JSONObject json = new JSONObject(jsonStr);

            JSONArray categories = json.getJSONArray("categories");
            for (int i = 0; i < categories.length(); ++i) {
                JSONObject categoryFirst = categories.getJSONObject(0);
                JSONArray icons = categoryFirst.getJSONArray("icons");

                for (int j = 0; j < icons.length(); ++j) {
                    list.add(icons.getJSONObject(j).getString("id"));
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String getUrlAt(int pos) {
        return baseUrl + type + "-" + list.get(pos) + "-24px.svg";
    }
}
