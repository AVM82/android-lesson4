package org.avm.lesson4.model.db;

import org.avm.lesson4.model.Alarm;

import java.util.List;

public interface DataBaseManager {

    List<Alarm> selectAllAlarms();

    void addAlarms(Alarm alarm);

    void deleteAlarm(String id);
}
