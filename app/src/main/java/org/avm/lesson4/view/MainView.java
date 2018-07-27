package org.avm.lesson4.view;

import android.content.Context;

import org.avm.lesson4.model.Alarm;

public interface MainView {
    void onSuccessAddAlarm(Alarm alarm);

    void refreshAlarmList();

    Context getContext();
}
