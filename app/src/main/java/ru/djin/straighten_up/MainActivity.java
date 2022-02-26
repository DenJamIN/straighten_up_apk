package ru.djin.straighten_up;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import ru.djin.straighten_up.notification.Notification;

public class MainActivity extends AppCompatActivity {

    private NotificationManager notificationManager;
    Button notifyBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notifyBtn = findViewById(R.id.button);
        notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notifyBtn.setOnClickListener(view -> Notification.initNotify(getApplicationContext(), notificationManager));
    }
}