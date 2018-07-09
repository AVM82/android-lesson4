package org.avm.lesson4;

import android.app.Application;

import timber.log.Timber;

public class Lesson4App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
//            Timber.plant(new Timber.DebugTree());
            /*Logging to the file*/
            Timber.plant(new FileLoggingTree(getApplicationContext()));
        }
        Timber.d("[Start application]");
    }
}
