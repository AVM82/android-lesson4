package org.avm.lesson4;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.avm.lesson4.adapter.AlarmAdapter;
import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.presenter.MainPresenter;
import org.avm.lesson4.presenter.MainPresenterImp;
import org.avm.lesson4.view.AddAlarmDialog;
import org.avm.lesson4.view.MainView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemLongClick;

public class MainActivity extends AppCompatActivity implements MainView {
    public static final String TAG = "MainActivity";
    private MainPresenter mainPresenter;
    private List<Alarm> alarms = new ArrayList<>();
    private AlarmAdapter adapter;
    private BottomSheetDialog mBottomSheetDialog;

    @Nullable
    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        adapter = new AlarmAdapter(alarms, this);
        Objects.requireNonNull(listView).setAdapter(adapter);
        mBottomSheetDialog = new BottomSheetDialog(this);
        mainPresenter = new MainPresenterImp(this);
        mainPresenter.getAlarms();
    }

    @OnItemLongClick(R.id.list_view)
    boolean longClickOnAlarm(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "Click");
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        mBottomSheetDialog.setContentView(sheetView);
        LinearLayout bottomDelete = sheetView.findViewById(R.id.bottom_sheet_delete);
        bottomDelete.setOnClickListener(v -> {
            Log.d(TAG, "Click delete alarm");
                TextView idAlarm = view.findViewById(R.id.id);
                mainPresenter.deleteAlarm(idAlarm.getText().toString());
            mBottomSheetDialog.dismiss();
        });
        mBottomSheetDialog.show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        AddAlarmDialog addAlarmDialog = new AddAlarmDialog();
        addAlarmDialog.show(getSupportFragmentManager(), "addAlarm");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSuccessAddAlarm(int hour, int minute) {
        Alarm alarmTime = mainPresenter.createAlarm(hour, minute);
        alarms.add(alarmTime);
        adapter.notifyDataSetChanged();
        mainPresenter.setAlarm(alarmTime);
    }

    @Override
    public void onFailAddAlarm() {
        Toast.makeText(this, "Alarm was not added", Toast.LENGTH_SHORT).show();
    }

    @Override
    public View getItemView() {
        return getLayoutInflater().inflate(R.layout.listview_item, null);
    }

    @Override
    public void onSuccessGetAllAlarmsFromDb(List<Alarm> alarms) {
        this.alarms.clear();
        this.alarms.addAll(alarms);
        adapter.notifyDataSetChanged();
    }
}
