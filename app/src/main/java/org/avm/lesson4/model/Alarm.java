package org.avm.lesson4.model;

import android.content.Context;

public interface Alarm {
    String getAlarmSet();

    long getTimeInMillis();

    void scheduleAlarms(Context context);

    int getId();

    void setId(int id);
}
