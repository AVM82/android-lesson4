package org.avm.lesson4.model.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import org.avm.lesson4.view.dialog.AlertDialogActivity;

import java.util.Calendar;

import timber.log.Timber;

public class AlarmListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String time = intent.getStringExtra("time");
        Toast.makeText(context, "Wake UP!!! It's now " + time, Toast.LENGTH_LONG).show();
        Timber.d("Wake UP!!! It's now %s", time);
        Calendar calendar = Calendar.getInstance();
        Timber.d("%s : %s", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        Intent i = new Intent(context, AlertDialogActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
