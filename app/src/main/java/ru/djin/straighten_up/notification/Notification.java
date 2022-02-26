package ru.djin.straighten_up.notification;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import ru.djin.straighten_up.MainActivity;
import ru.djin.straighten_up.R;
import android.app.NotificationManager;
import android.os.Build;

import java.util.concurrent.ThreadLocalRandom;

public class Notification {

    private static final int NOTIFY_ID = 1;

    private static final String CHANNEL_ID = "CHANNEL_ID";

    private Notification(){

    }

    public static class Messages {

        final static String[] titles;
        final static String[] texts;

        static {
            titles = new String[]{
                    "Выпрями спину", "Пора размяться", "Не сутулься", "Горб.нет"
            };
            texts = new String[]{
                    "Перестань быть вопросительным знаком", "Ты похож на шахматную фигуру",
                    "Хорошенько потянись", "Что с плечами?"
            };
        }

        private Messages(){

        }

        public static String getNotifyTitle(){
            return titles[ThreadLocalRandom.current().nextInt(titles.length)];
        }

        public static String getNotifyText(){
            return texts[ThreadLocalRandom.current().nextInt(texts.length)];
        }
    }

    public static void initNotify(Context contextApp, NotificationManager notificationManager){
        Intent intent = new Intent(contextApp, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

        @SuppressLint("UnspecifiedImmutableFlag")
        PendingIntent pendingIntent = PendingIntent.getActivity(
                contextApp,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        NotificationCompat.Builder notifyBuilder =
                new NotificationCompat.Builder(contextApp, CHANNEL_ID)
                        .setAutoCancel(false)
                        .setSmallIcon(R.drawable.ic_accessibility)
                        .setWhen(System.currentTimeMillis())
                        .setContentIntent(pendingIntent)
                        .setContentTitle(Messages.getNotifyTitle())
                        .setContentText(Messages.getNotifyText())
                        .setPriority(PRIORITY_HIGH);

        createChannelIfNeeded(notificationManager);
        notificationManager.notify(NOTIFY_ID, notifyBuilder.build());
    }

    static void createChannelIfNeeded(NotificationManager manager){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            manager.createNotificationChannel(notificationChannel);
        }
    }
}
