package org.avm.lesson4.model.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import timber.log.Timber;

public class RebootListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            Timber.d("Restore Alarm Service");
            context.startService(new Intent(context, RestoreAlarmService.class));
        }
    }
}
