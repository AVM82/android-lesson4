package org.avm.lesson4.view;

import android.databinding.DataBindingUtil;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.avm.lesson4.R;
import org.avm.lesson4.databinding.ActivityMainBinding;
import org.avm.lesson4.databinding.ListviewItemBinding;
import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.presenter.MainPresenter;
import org.avm.lesson4.presenter.MainPresenterImpl;
import org.avm.lesson4.view.adapter.AlarmAdapter;
import org.avm.lesson4.view.dialog.AddAlarmDialog;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainView {
    private List<Alarm> alarms;
    private MainPresenter mainPresenter;
    private AlarmAdapter alarmAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainPresenter = new MainPresenterImpl(this);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ListView listView = binding.alarmsListView;
        listView.setOnItemLongClickListener(onItemLongClickListener);
        alarms = mainPresenter.getAllAlarmsFromDb();
        alarmAdapter = new AlarmAdapter(this, alarms);
        listView.setAdapter(alarmAdapter);
        Timber.d("[onCreate MainActivity]");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Timber.d("Click add new alarm");
        AddAlarmDialog addAlarmDialog = new AddAlarmDialog();
        addAlarmDialog.show(getSupportFragmentManager(), "addNewAlarm");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessAddAlarm(Alarm alarm) {
        Timber.d("Selected time -> %s", alarm.getAlarmTime());
        mainPresenter.saveAlarmToDb(alarm);
        alarms.add(alarm);
        alarmAdapter.notifyDataSetChanged();
        mainPresenter.scheduleAlarms(alarm);
    }

    @Override
    public void refreshAlarmList() {
        alarms.clear();
        alarms.addAll(mainPresenter.getAllAlarmsFromDb());
        alarmAdapter.notifyDataSetChanged();
    }

    private void showBottomMenu(View view) {
        View bottomMenuView = getLayoutInflater().inflate(R.layout.bottom_menu, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(bottomMenuView);
        LinearLayout bottomDelete = bottomMenuView.findViewById(R.id.bottom_sheet_delete);
        bottomDelete.setOnClickListener(v -> {
            Timber.d("Click delete alarm");
            TextView idAlarm = view.findViewById(R.id.alarm_id);
            mainPresenter.deleteAlarm(Integer.valueOf(idAlarm.getText().toString()));
            Timber.d("The alarm was deleted");
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.show();
    }

    AdapterView.OnItemLongClickListener onItemLongClickListener = (parent, view, position, id) -> {
        Timber.d("OnItemLongClick -> %s", position);
        showBottomMenu(view);
        return true;
    };


}
