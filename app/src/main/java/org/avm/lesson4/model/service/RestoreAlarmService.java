package org.avm.lesson4.model.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.model.SQLiteDB;

import java.util.List;

import timber.log.Timber;

public class RestoreAlarmService extends IntentService {

    public RestoreAlarmService() {
        super("RestoreAlarmService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Timber.d("RestoreAlarmService");
        SQLiteDB db = new SQLiteDB(this);
        List<Alarm> alarms = db.selectAllAlarms();
        for (Alarm alarm : alarms) {
            Timber.d("I ran!");
            Timber.d("Alarm set: %s", alarm.getAlarmTime());
            AlarmsManager.scheduleAlarm(alarm, this);
        }

    }
}
