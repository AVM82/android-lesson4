package org.avm.lesson4.view;

import android.view.View;

import org.avm.lesson4.model.Alarm;

import java.util.List;

public interface MainView {

    void onSuccessAddAlarm(int hour, int minute);

    void onFailAddAlarm();

    View getItemView();

    void onSuccessGetAllAlarmsFromDb(List<Alarm> alarms);

}