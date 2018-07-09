package org.avm.lesson4.model.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import org.avm.lesson4.model.Alarm;

import java.util.Calendar;

import timber.log.Timber;

import static android.content.Context.ALARM_SERVICE;

public class AlarmsService {
    private Context context;

    public AlarmsService(Context context) {
        this.context = context;
    }

    public void scheduleAlarm(Alarm alarm) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmListener.class);
        intent.putExtra("time", alarm.getAlarmTime());

        Calendar calendar = checkAlarmTime(alarm);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                alarm.getAlarmId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
        Timber.d("Date %s", calendar.get(Calendar.DATE));
        Timber.d("Alarm schedule time %s:%s", calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));

    }

    @NonNull
    private Calendar checkAlarmTime(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarm.getTimeAlarmInMillis());

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        return calendar;
    }

    public void unscheduleAlarm(int alarmId) {
        Timber.d("UnscheduleAlarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmListener.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
