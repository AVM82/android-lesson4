package org.avm.lesson4.view.dialog;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import org.avm.lesson4.R;

import timber.log.Timber;

public class AlertDialogActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        runMediaPlayer();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alarm")
                .setMessage("STOP PLAY.")
                .setCancelable(false)
                .setNegativeButton("OK", (dialog, id) -> {
                    mediaPlayer.stop();
                    dialog.cancel();
                    finish();
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void runMediaPlayer() {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rock);
        if (mediaPlayer == null) {
            Timber.d("Create() on MediaPlayer failed.");
        } else {
            mediaPlayer.setOnCompletionListener(player -> {
                Timber.d("stop sound");
                player.stop();
                player.release();
            });
            Timber.d("Play sound");
            mediaPlayer.start();
        }
    }
}
