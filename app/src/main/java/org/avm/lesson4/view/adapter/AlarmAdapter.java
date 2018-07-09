package org.avm.lesson4.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.avm.lesson4.R;
import org.avm.lesson4.databinding.ListviewItemBinding;
import org.avm.lesson4.model.Alarm;

import java.util.List;

public class AlarmAdapter extends ArrayAdapter<Alarm> {
    private Context context;

    public AlarmAdapter(Context context, List<Alarm> alarmList) {
        super(context, android.R.layout.activity_list_item, alarmList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ListviewItemBinding listviewItemBinding;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
            listviewItemBinding = DataBindingUtil.bind(convertView);
            convertView.setTag(listviewItemBinding);
        } else {
            listviewItemBinding = (ListviewItemBinding) convertView.getTag();
        }
        listviewItemBinding.setAlarm(getItem(position));
        return listviewItemBinding.getRoot();
    }
}
