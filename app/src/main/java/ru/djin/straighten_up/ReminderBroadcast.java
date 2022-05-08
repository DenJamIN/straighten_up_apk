package ru.djin.straighten_up;

import static androidx.core.app.NotificationCompat.DEFAULT_ALL;
import static androidx.core.app.NotificationCompat.DEFAULT_VIBRATE;
import static androidx.core.app.NotificationCompat.FLAG_AUTO_CANCEL;
import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import ru.djin.straighten_up.notification.Notification;

public class ReminderBroadcast extends BroadcastReceiver {

    //TODO добавить открытие приложение по нажатию на уведомление. Придумать логику обработки
    @Override
    public void onReceive(Context context, Intent intent){
        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyStraightenUp")
                .setSmallIcon(R.drawable.ic_accessibility)
                .setContentTitle(Notification.Messages.getNotifyTitle())
                .setContentText(Notification.Messages.getNotifyText())
                .setPriority(PRIORITY_HIGH)
                .setDefaults(DEFAULT_ALL)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_accessibility, "Выпрямиться", pendingIntent);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        managerCompat.notify(200, builder.build());
    }
}
