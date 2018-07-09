package org.avm.lesson4.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import timber.log.Timber;

public class SQLiteDB extends SQLiteOpenHelper implements DataBaseManager {
    private static final String DATABASE_NAME = "alarms.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "alarms";
    private static final String ALARM_TIME = "alarm_time";
    private static final String ID = "id";

    private SQLiteDatabase db;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public List<Alarm> selectAllAlarms() {
        Timber.d("select all from DB");
        connect();
        List<Alarm> alarms = new ArrayList<>();
        Cursor cursor = db.query(TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        int idColumnIndex = cursor.getColumnIndex(ID);
        int hourColumnIndex = cursor.getColumnIndex(ALARM_TIME);
        while (cursor.moveToNext()) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(cursor.getLong(hourColumnIndex));
            Alarm alarm = new Alarm(calendar);
            alarm.setAlarmId(cursor.getInt(idColumnIndex));
            alarms.add(alarm);
        }
        cursor.close();
        close();
        return alarms;
    }

    @Override
    public void saveAlarms(Alarm alarm) {
        Timber.d("saveAlarms %s", alarm.getAlarmTime());
        connect();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALARM_TIME, alarm.getTimeAlarmInMillis());
        int id = (int) db.insert(TABLE_NAME, null, contentValues);
        alarm.setAlarmId(id);
        close();
    }

    @Override
    public void deleteAlarm(int id) {
        Timber.d("delete Alarms with id %s", id);
        connect();
        db.delete(TABLE_NAME, ID + " = " + id, null);
        close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER primary key autoincrement, " +
                ALARM_TIME + " INTEGER)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void connect() {
        db = getWritableDatabase();
    }
}
