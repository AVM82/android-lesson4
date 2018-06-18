package org.avm.lesson4.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

public class AddAlarmDialog extends DialogFragment {
    MainView mainView;
    int hour = 0;
    int minute = 0;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new TimePickerDialog(getContext(), callBack, hour, minute, true);
    }

    TimePickerDialog.OnTimeSetListener callBack = (view, hourOfDay, minute) -> {
        if (view.isShown()) {
            this.hour = hourOfDay;
            this.minute = minute;
            mainView.onSuccessAddAlarm(hourOfDay, minute);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainView = (MainView) context;
    }
}
