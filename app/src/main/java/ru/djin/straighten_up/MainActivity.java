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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import jp.shts.android.storiesprogressview.StoriesProgressView;


 public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener  {

     final String CHANNEL_ID = "notifyStraightenUp";

     private TextView amTime;
     private TextView pmTime;
     private TextView amLabel;
     private TextView pmLabel;

     private SeekBar seekBarStart;
     private SeekBar seekBarEnd;

     private StoriesProgressView storiesProgressView;

     private ImageView imageView;
     private ImageView betaToolsView;

     private ImageButton storiesBtn;
     private Button notifyBtn;

     private int counterStories = 0;

     private final int[] resources = new int[]{
             R.drawable.warmupfirst,
             R.drawable.warmupsecond
     };

     @SuppressLint("ClickableViewAccessibility")
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
         setTitle("Выпрями спину!");

         createNotificationChannelIfNeeded();

         notifyBtn = findViewById(R.id.startButton);
         storiesBtn = findViewById(R.id.showStoriesButton);
         amTime = findViewById(R.id.timeAMLabel);
         pmTime = findViewById(R.id.timePMLabel);
         amLabel = findViewById(R.id.chooseTimeAMLabel);
         pmLabel = findViewById(R.id.chooseTimePMLabel);
         seekBarStart = findViewById(R.id.seekBarStart);
         seekBarEnd = findViewById(R.id.seekBarEnd);
         betaToolsView = findViewById(R.id.betaToolsImage);
         imageView = findViewById(R.id.imageForStories);
         storiesProgressView = findViewById(R.id.stories);

         seekBarStart.setOnSeekBarChangeListener(this);
         seekBarEnd.setOnSeekBarChangeListener(this);

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

             long timeAtButtonClick = System.currentTimeMillis();
             long tenSecondsInMillis = 1000 * 10;

             alarmManager.set(
                     AlarmManager.RTC_WAKEUP,
                     timeAtButtonClick + tenSecondsInMillis,
                     pendingIntent
             );

         });



         storiesBtn.setOnClickListener(view -> {
             counterStories = 0;

             storiesProgressView.setStoriesCount(resources.length);
             storiesProgressView.setStoryDuration(10_000L);

             imageView.setVisibility(View.VISIBLE);

             amTime.setVisibility(View.INVISIBLE);
             pmTime.setVisibility(View.INVISIBLE);
             amLabel.setVisibility(View.INVISIBLE);
             pmLabel.setVisibility(View.INVISIBLE);
             seekBarStart.setVisibility(View.INVISIBLE);
             seekBarEnd.setVisibility(View.INVISIBLE);
             notifyBtn.setVisibility(View.INVISIBLE);
             storiesBtn.setVisibility(View.INVISIBLE);
             betaToolsView.setVisibility(View.INVISIBLE);

             storiesProgressView.startStories();
             imageView.setImageResource(resources[counterStories]);

             storiesProgressView.setStoriesListener(new StoriesProgressView.StoriesListener() {
                 @Override
                 public void onNext() {
                     imageView.setImageResource(resources[++counterStories]);
                 }

                 @Override
                 public void onPrev() { }

                 @Override
                 public void onComplete() {
                     Toast.makeText(MainActivity.this, "Истории закончились", Toast.LENGTH_SHORT).show();

                     amTime.setVisibility(View.VISIBLE);
                     pmTime.setVisibility(View.VISIBLE);
                     amLabel.setVisibility(View.VISIBLE);
                     pmLabel.setVisibility(View.VISIBLE);
                     seekBarStart.setVisibility(View.VISIBLE);
                     seekBarEnd.setVisibility(View.VISIBLE);
                     notifyBtn.setVisibility(View.VISIBLE);
                     storiesBtn.setVisibility(View.VISIBLE);
                     betaToolsView.setVisibility(View.VISIBLE);

                     imageView.setVisibility(View.INVISIBLE);
                 }
             });

             //Не скипаются при повторном воспроизводится потому что current возвращает ту же пикчу, а не следующую
             imageView.setOnClickListener(v -> {
                 if(imageView.getVisibility()==View.VISIBLE)
                    storiesProgressView.skip();
             });

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
     protected void onDestroy() {
         storiesProgressView.destroy();
         super.onDestroy();
     }

     @Override
     public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
         amTime.setText(getTimeProgressText(seekBarStart.getProgress()+1, true));
         pmTime.setText(getTimeProgressText(seekBarEnd.getProgress()+1, false));
     }

     @Override
     public void onStartTrackingTouch(SeekBar seekBar) { }

     @Override
     public void onStopTrackingTouch(SeekBar seekBar) { }

     private String getTimeProgressText(int progress, boolean am){
         if (am){
             return progress + ":00 AM (" + progress + ":00)";
         }
         else{
             return progress + ":00 PM (" + (progress + 12) + ":00)";
         }
     }
 }