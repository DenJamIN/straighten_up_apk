package ru.djin.straighten_up;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.djin.straighten_up.notification.Notification;

public class ReminderBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyStraightenUp")
                .setSmallIcon(R.drawable.ic_accessibility)
                .setContentTitle(Notification.Messages.getNotifyTitle())
                .setContentText(Notification.Messages.getNotifyText())
                .setPriority(PRIORITY_HIGH);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        managerCompat.notify(200, builder.build());
    }
}
