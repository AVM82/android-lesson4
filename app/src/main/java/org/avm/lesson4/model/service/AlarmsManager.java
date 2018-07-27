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

public class AlarmsManager {

    public static void scheduleAlarm(Alarm alarm, Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra("time", alarm.getAlarmTime());

        Calendar calendar = getAlarmCalendar(alarm);

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
    private static Calendar getAlarmCalendar(Alarm alarm) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(alarm.getTimeAlarmInMillis());

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        return calendar;
    }

    public static void unscheduleAlarm(int alarmId, Context context) {
        Timber.d("UnscheduleAlarm");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }
}
