package org.avm.lesson4.presenter;

import org.avm.lesson4.model.Alarm;

public interface MainPresenter {
    Alarm createAlarm(int hour, int minute);

    void setAlarm(Alarm alarm);

    void getAlarms();

    void deleteAlarm(String id);
}
