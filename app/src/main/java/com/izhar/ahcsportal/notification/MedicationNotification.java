 package com.izhar.ahcsportal.notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.izhar.ahcsportal.R;

public class MedicationNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "medication_notification")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle(intent.getExtras().getString("title"))
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentText("" + intent.getExtras().getString("desc"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());

    }
}