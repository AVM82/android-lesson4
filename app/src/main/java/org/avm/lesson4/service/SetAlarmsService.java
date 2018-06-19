package org.avm.lesson4.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.model.db.SQLiteDB;

import java.util.List;

public class SetAlarmsService extends IntentService {

    public static final String AFTER_REBOOT = "[AFTER REBOOT]";

    public SetAlarmsService() {
        super("SetAlarmsService");

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        SQLiteDB db = new SQLiteDB(this);
        List<Alarm> alarms = db.selectAllAlarms();
        for (Alarm alarm : alarms) {
            alarm.scheduleAlarms(this);
            Log.d(AFTER_REBOOT, "I ran!");
            Toast.makeText(this, "I ran", Toast.LENGTH_SHORT).show();
        }
    }
}
