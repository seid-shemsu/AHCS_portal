package com.izhar.ahcsportal.notification;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.izhar.ahcsportal.R;

public class AppointmentNotification extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "appointment_notification")
                .setSmallIcon(R.drawable.notification)
                .setContentTitle("Appointment")
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentText(("Appointment name"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(140, builder.build());
    }
}
