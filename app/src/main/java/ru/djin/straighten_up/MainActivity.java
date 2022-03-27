package ru.djin.straighten_up;

import static androidx.core.app.NotificationCompat.PRIORITY_HIGH;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import ru.djin.straighten_up.notification.Notification;

public class MainActivity extends AppCompatActivity {

    final String CHANNEL_ID = "notify";

    //TODO форматировать
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannelIfNeeded();

        Button notifyBtn = findViewById(R.id.button);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_accessibility)
                .setContentTitle(Notification.Messages.getNotifyTitle())
                .setContentText(Notification.Messages.getNotifyText())
                .setPriority(PRIORITY_HIGH);


        notifyBtn.setOnClickListener(view -> notificationManager.notify(100, builder.build()));
    }

    private void createNotificationChannelIfNeeded(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            String description = "Channel for notify";

            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            notificationChannel.setDescription(description);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }
}