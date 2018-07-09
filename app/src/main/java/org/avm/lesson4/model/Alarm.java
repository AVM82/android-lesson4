package org.avm.lesson4.model;

import java.util.Calendar;

public class Alarm {

    private long timeAlarmInMillis;
    private int alarmId;
    private String alarmTime;


    public Alarm(Calendar calendar) {
        this.timeAlarmInMillis = calendar.getTimeInMillis();
        alarmTime = getAlarmTime(calendar);

    }

    public long getTimeAlarmInMillis() {
        return timeAlarmInMillis;
    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    private String getAlarmTime(Calendar calendar) {
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


}
