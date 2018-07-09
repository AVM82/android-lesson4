package org.avm.lesson4.presenter;

import android.content.Context;

import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.model.SQLiteDB;
import org.avm.lesson4.model.service.AlarmsService;
import org.avm.lesson4.view.MainView;

import java.util.Calendar;
import java.util.List;

public class MainPresenterImpl implements MainPresenter {
    private MainView mainView;
    private SQLiteDB sqLiteDB;
    private Context context;

    public MainPresenterImpl(Context context) {
        this.mainView = (MainView) context;
        this.context = context;
        sqLiteDB = new SQLiteDB(context);
    }

    @Override
    public String getAlarmSet(Calendar calendar) {
        String hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = String.valueOf(calendar.get(Calendar.MINUTE));
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return hour + ":" + minute;
    }

    @Override
    public void saveAlarmToDb(Alarm alarm) {
        sqLiteDB.saveAlarms(alarm);
    }

    @Override
    public List<Alarm> getAllAlarmsFromDb() {
        return sqLiteDB.selectAllAlarms();
    }

    @Override
    public void deleteAlarm(int id) {
        sqLiteDB.deleteAlarm(id);
        new AlarmsService(context).unscheduleAlarm(id);
        mainView.refreshAlarmList();
    }

    @Override
    public void scheduleAlarms(Alarm alarm) {
        new AlarmsService(context).scheduleAlarm(alarm);
    }
}
