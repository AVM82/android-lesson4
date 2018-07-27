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

        try {
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
            builder.create().show();
        } catch (IllegalArgumentException e) {
            Timber.d(e);
        }
    }

    private void runMediaPlayer() throws IllegalArgumentException {
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.rock);
        if (mediaPlayer != null) {
            mediaPlayer.setOnCompletionListener(player -> {
                Timber.d("stop sound");
                player.stop();
                player.release();
            });
            Timber.d("Play sound");
            mediaPlayer.start();
        } else {
            throw new IllegalArgumentException("Create() on MediaPlayer failed. because of it's reference is null");
        }
    }
}
