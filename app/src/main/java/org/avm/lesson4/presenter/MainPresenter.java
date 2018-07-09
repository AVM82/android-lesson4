package org.avm.lesson4.presenter;

import org.avm.lesson4.model.Alarm;

import java.util.Calendar;
import java.util.List;

public interface MainPresenter {
    String getAlarmSet(Calendar calendar);

    void saveAlarmToDb(Alarm alarm);

    List<Alarm> getAllAlarmsFromDb();

    void deleteAlarm(int id);

    void scheduleAlarms(Alarm alarm);
}
