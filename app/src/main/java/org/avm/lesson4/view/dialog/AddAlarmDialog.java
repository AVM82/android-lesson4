package org.avm.lesson4.view.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import org.avm.lesson4.model.Alarm;
import org.avm.lesson4.view.MainView;

import java.util.Calendar;

import timber.log.Timber;

public class AddAlarmDialog extends DialogFragment {

    MainView mainView;
    private TimePickerDialog.OnTimeSetListener callback = (timePicker, hourOfDay, minute) -> {
        if (timePicker.isShown()) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            Timber.d("Close add new alarm dialog");
            mainView.onSuccessAddAlarm(new Alarm(calendar));
        }
    };

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Timber.d("Create add new alarm dialog");
        return new TimePickerDialog(getContext(), callback, 0, 0, true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainView = (MainView) context;
    }
}
