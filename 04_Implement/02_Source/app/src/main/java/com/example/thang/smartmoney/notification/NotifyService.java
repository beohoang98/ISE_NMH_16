package com.example.thang.smartmoney.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotifyService {
    private final String TAG = "notify";

    public static final int CODE = 100;
    private static AlarmManager alarmManager;
    private static NotifyService instance;

    private Context context;
    private Intent intent;
    private PendingIntent pendingIntent;
    private boolean notify = false;

    public static NotifyService getInstance(@NotNull Context context) {
        if (instance == null) {
            instance = new NotifyService(context.getApplicationContext());
        }
        return instance;
    }

    public NotifyService(@NotNull Context ctx) {
        context = ctx.getApplicationContext();
        alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);

        intent = new Intent(context, NotifyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, NotifyService.CODE, intent, PendingIntent.FLAG_NO_CREATE);

        notify = pendingIntent != null;
    }

    public boolean isNotify() {
        return notify;
    }

    public void turnOn(int hours, int minute, int second) {
        if (notify) {
            Log.d(TAG, "Alarm clock was turned on");
            Toast.makeText(context, "Alarm clock was turned on", Toast.LENGTH_SHORT).show();
            return;
        }
        pendingIntent = PendingIntent.getBroadcast(context, NotifyService.CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
//                1000*60,
                pendingIntent);
        Log.d(TAG, "turn on");
    }

    public void turnOff() {
        pendingIntent.cancel();
        alarmManager.cancel(pendingIntent);
        Log.d(TAG, "turn off");
    }
}
