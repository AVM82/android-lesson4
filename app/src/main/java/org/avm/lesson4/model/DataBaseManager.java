package org.avm.lesson4.model;

import java.util.List;

public interface DataBaseManager {

    List<Alarm> selectAllAlarms();

    void saveAlarms(Alarm alarm);

    void deleteAlarm(int id);
}
