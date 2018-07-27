package org.avm.lesson4.presenter;

import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.model.SQLiteDB;
import org.avm.lesson4.model.service.AlarmsManager;
import org.avm.lesson4.view.MainView;

import java.util.List;

public class MainPresenterImpl implements MainPresenter {
    private final MainView mainView;
    private final SQLiteDB sqLiteDB;

    public MainPresenterImpl(MainView mainView) throws IllegalArgumentException {
            this.mainView = mainView;
            sqLiteDB = new SQLiteDB(mainView.getContext());
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
        AlarmsManager.unscheduleAlarm(id, mainView.getContext());
        mainView.refreshAlarmList();
    }

    @Override
    public void scheduleAlarms(Alarm alarm) {
        AlarmsManager.scheduleAlarm(alarm, mainView.getContext());
    }
}
