package org.avm.lesson4.presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.model.AlarmTime;
import org.avm.lesson4.model.db.DataBaseManager;
import org.avm.lesson4.model.db.SQLiteDB;
import org.avm.lesson4.view.MainView;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;

public class MainPresenterImp implements MainPresenter {
    private MainView mainView;
    private DataBaseManager db;
    private Context context;

    public MainPresenterImp(Context context) {
        this.mainView = (MainView) context;
        this.context = context;
        db = new SQLiteDB(context);

    }

    @Override
    public Alarm createAlarm(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                hour,
                minute,
                0
        );
        return new AlarmTime(calendar);
    }

    @Override
    public void setAlarm(Alarm alarm) {
        db.addAlarms(alarm);
        alarm.scheduleAlarms(context);
    }

    @Override
    public void getAlarms() {
        List<Alarm> alarms = new SQLiteDB(context).selectAllAlarms();
        mainView.onSuccessGetAllAlarmsFromDb(alarms);
    }

    @Override
    public void deleteAlarm(String id) {
        db.deleteAlarm(id);
        unscheduleAlarms(context, Integer.parseInt(id));
        getAlarms();
    }

    private void unscheduleAlarms(Context context, int alarmId) {
        Intent intent = new Intent(context, getClass());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                alarmId, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }

}
