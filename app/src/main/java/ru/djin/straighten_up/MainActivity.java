 package ru.djin.straighten_up;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener  {

    final String CHANNEL_ID = "notifyStraightenUp";
    private TextView timeAM;
    private TextView timePM;
    private SeekBar seekBarStart;
    private SeekBar seekBarEnd;

    //TODO форматировать
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannelIfNeeded();

        Button notifyBtn = findViewById(R.id.startButton);
        timeAM = (TextView)findViewById(R.id.timeAMLabel);
        timePM = (TextView)findViewById(R.id.timePMLabel);
        seekBarStart = (SeekBar)findViewById(R.id.seekBarStart);
        seekBarEnd = (SeekBar)findViewById(R.id.seekBarEnd);
        seekBarStart.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);
        seekBarEnd.setOnSeekBarChangeListener((SeekBar.OnSeekBarChangeListener) this);

        notifyBtn.setOnClickListener(view -> {
            Toast.makeText(this, "Reminder Set!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
            @SuppressLint("UnspecifiedImmutableFlag") PendingIntent pendingIntent = PendingIntent.getBroadcast(
                    MainActivity.this,
                    0,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
            );

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            //TODO цикличность и рандомизация времени
            long timeAtButtonClick = System.currentTimeMillis();
            long tenSecondsInMillis = 1000 * 10;

            alarmManager.set(
                    AlarmManager.RTC_WAKEUP,
                    timeAtButtonClick + tenSecondsInMillis,
                    pendingIntent
            );

        });
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        timeAM.setText(getTimeProgressText(seekBarStart.getProgress()+1, true));
        timePM.setText(getTimeProgressText(seekBarEnd.getProgress()+1, false));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private String getTimeProgressText(int progress, boolean am){
        if (am){
            return String.valueOf(progress) + ":00 AM (" + String.valueOf(progress) + ":00)";
        }
        else{
            return String.valueOf(progress) + ":00 PM (" + String.valueOf(progress+12) + ":00)";
        }
    }
}