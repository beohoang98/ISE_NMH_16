package com.example.thang.smartmoney.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.thang.smartmoney.R;
import com.example.thang.smartmoney.home_activity;

public class NotifyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent repeatIntent = new Intent(context, home_activity.class);
        repeatIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                NotifyService.CODE,
                repeatIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String CHANNEL_ID = "Smart-money";

        handleAPI26(notificationManager, CHANNEL_ID);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.lo_go)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Hello world!")
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true);

        notificationManager.notify(NotifyService.CODE, builder.build());
    }

    void handleAPI26(NotificationManager notificationManager, String CHANNEL_ID) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence name = "smart_money";
            String Description = "smart money notify channel";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

    }
}
