package com.example.thang.smartmoney.model;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.widget.ImageView;
import com.ahmadrosid.svgloader.SvgLoader;
import com.example.thang.smartmoney.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ClassIcon {
    public static List<String> list;
    public static String baseUrl;
    public static String type;
    private static Context mContext;

    public static void load(Context ctx) {
        if (list != null) return;
        mContext = ctx;

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

    public static void downloadIcon(String name, String url) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            Response res = client.newCall(request).execute();
            String data = res.body().string();

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(mContext.openFileOutput(name + ".svg", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean loadDownloadedIcon(String name, ImageView target, Activity activity) {
        File icon = new File(activity.getFilesDir(), name + ".svg");
        if (!icon.exists())
            return false;

        SvgLoader.pluck().with(activity)
                .setPlaceHolder(R.mipmap.ic_launcher_round, R.mipmap.ic_launcher_round)
                .load(icon.getAbsolutePath(), target)
                .close();

        return true;
    }

}
