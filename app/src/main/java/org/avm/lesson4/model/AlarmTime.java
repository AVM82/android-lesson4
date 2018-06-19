package org.avm.lesson4.model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.Toast;

import org.avm.lesson4.R;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class AlarmTime extends BroadcastReceiver implements Alarm {
    private static final String TAG = "AlarmTime";
    public static final String TAG_ALARM = "ALARM";
    private final Calendar alarmTime;
    private int id;

    public AlarmTime() {
        this(Calendar.getInstance());
    }

    public AlarmTime(int hours, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                hours,
                minutes,
                0
        );
        this.alarmTime = calendar;
    }

    public AlarmTime(Calendar alarmTime) {
        this.alarmTime = alarmTime;
    }

    public String getAlarmSet() {
        String hour = String.valueOf(alarmTime.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(alarmTime.get(Calendar.MINUTE));
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return hour + ":" + minute;
    }

    @Override
    public long getTimeInMillis() {
        return alarmTime.getTimeInMillis();
    }

    public void scheduleAlarms(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, getClass());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    alarmTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "Wake UP!!!", Toast.LENGTH_LONG).show();
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.rock);
        if (mediaPlayer == null) {
            Log.v(TAG, "Create() on MediaPlayer failed.");
        } else {
            mediaPlayer.setOnCompletionListener(player -> {
                player.stop();
                player.release();
            });
            mediaPlayer.start();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
