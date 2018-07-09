package org.avm.lesson4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class FileLoggingTree extends Timber.DebugTree {
    private static final String TAG = "[FileLoggingTree]";
    private final String FILENAME;

    FileLoggingTree(Context context) {
        FILENAME = context.getFilesDir() + "/lesson4.log";
    }

    @SuppressLint("LogNotTimber")
    @Override
    public void d(String message, Object... args) {

        String logTimeStamp = new SimpleDateFormat("E MMM dd yyyy 'at' hh:mm:ss:SSS aaa",
                Locale.getDefault()).format(new Date());
        try {
            File file = new File(FILENAME);
            file.createNewFile();
            if (file.exists()) {
                OutputStream fileOutputStream = new FileOutputStream(file, true);
                fileOutputStream.write(String.format((logTimeStamp + " : " + message + "\r\n"), args).getBytes());
                fileOutputStream.close();
            }
        } catch (Exception e) {
            Log.e(TAG, "Error while logging into file : " + e);
        }
    }


}
