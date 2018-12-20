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

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class NotifyService {
    public static final int CODE = 100;
    private Context context;
    private Intent intent;
    private PendingIntent pendingIntent;
    private boolean notify = false;

    public NotifyService(Context context) {
        this.context = context;
        intent = new Intent(context, NotifyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, CODE, intent, PendingIntent.FLAG_NO_CREATE);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        StatusBarNotification[] list = notificationManager.getActiveNotifications();
        for (StatusBarNotification statusBarNotification : list) {
            if (statusBarNotification.getId() == NotifyService.CODE) {
                notify = true;
                break;
            }
        }
    }

    public boolean isNotify() {
        return notify;
    }

    public void turnOn(int hours, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, CODE, intent, PendingIntent.FLAG_NO_CREATE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY,
                pendingIntent);
        Log.d("notify", "turn on");
    }

    public void turnOff() {
        PendingIntent checkIntent = PendingIntent.getBroadcast(context, CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        checkIntent.cancel();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(checkIntent);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(CODE);

        Log.d("notify", "turn off");
    }
}
