package org.avm.lesson4.view;

import org.avm.lesson4.model.Alarm;

public interface MainView {
    void onSuccessAddAlarm(Alarm alarm);

    void refreshAlarmList();
}
