package org.avm.lesson4.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import org.avm.lesson4.R;
import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.view.MainView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class AlarmAdapter extends BaseAdapter {
    private final MainView mainView;
    private List<Alarm> alarmTimes;

    public AlarmAdapter(List<Alarm> alarmTimes, MainView mainView) {
        this.alarmTimes = alarmTimes;
        this.mainView = mainView;
    }

    @Override
    public int getCount() {
        return alarmTimes.size();
    }

    @Override
    public Object getItem(int position) {
        return alarmTimes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mainView.getItemView();
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (viewHolder.textView != null) {
            viewHolder.textView.setText(alarmTimes.get(position).getAlarmSet());
        }
        if (viewHolder.textIdView != null) {
            viewHolder.textIdView.setText(String.valueOf(alarmTimes.get(position).getId()));
        }
        return convertView;
    }

    static class ViewHolder {
        @Nullable
        @BindView(R.id.id)
        TextView textIdView;

        @Nullable
        @BindView(R.id.time)
        TextView textView;

        @Nullable
        @BindView(R.id.alarm_on)
        CheckBox alarmOn;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        @OnCheckedChanged(R.id.alarm_on)
        void onAlarmOn(boolean checked) {

            System.out.println("ENTER");
            if (checked) {
                System.out.println("ON");
            } else {
                System.out.println("OFF");
            }
        }
    }
}