package org.avm.lesson4.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.model.AlarmTime;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends SQLiteOpenHelper implements DataBaseManager {
    private static final String DATABASE_NAME = "alarms.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "alarms";
    private static final String HOUR_COLUMN = "hour";
    private static final String MINUTE_COLUMN = "minute";
    private static final String ID = "id";

    private SQLiteDatabase db;

    public SQLiteDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID + " INTEGER primary key autoincrement, " +
                HOUR_COLUMN + " INTEGER, " +
                MINUTE_COLUMN + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void connect() {
        db = getWritableDatabase();
    }

    @Override
    public List<Alarm> selectAllAlarms() {
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
        int hourColumnIndex = cursor.getColumnIndex(HOUR_COLUMN);
        int minuteColumnIndex = cursor.getColumnIndex(MINUTE_COLUMN);
        while (cursor.moveToNext()) {
            Alarm alarm = new AlarmTime(cursor.getInt(hourColumnIndex),
                    cursor.getInt(minuteColumnIndex));
            alarm.setId(cursor.getInt(idColumnIndex));
            alarms.add(alarm);
        }
        close();
        return alarms;
    }

    @Override
    public void addAlarms(Alarm alarm) {
        connect();
        String[] alarmSet = alarm.getAlarmSet().split(":");
        ContentValues contentValues = new ContentValues();
        contentValues.put(HOUR_COLUMN, alarmSet[0]);
        contentValues.put(MINUTE_COLUMN, alarmSet[1]);
        int id = (int) db.insert(TABLE_NAME, null, contentValues);
        alarm.setId(id);
        close();
    }

    @Override
    public void deleteAlarm(String id) {
        connect();
        db.delete(TABLE_NAME, ID + " = " + id, null);
        close();
    }
}
