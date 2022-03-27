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
                    "Выпрями спину", "Пора размяться", "Горб.нет"
            };
            texts = new String[]{
                    "О, это ты вопросительный знак?", "Расправь плечи!", "Не сутулься"
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
}
