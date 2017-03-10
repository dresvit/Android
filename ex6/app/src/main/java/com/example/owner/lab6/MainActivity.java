package com.example.owner.lab6;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MainActivity extends AppCompatActivity {
    private MusicService musicService;
    private SeekBar seekBar;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private int angle = 1;
    private boolean rotate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button playBtn = (Button) findViewById(R.id.playBtn);
        final Button stopBtn = (Button) findViewById(R.id.stopBtn);
        final Button quitBtn = (Button) findViewById(R.id.quitBtn);
        final TextView state = (TextView) findViewById(R.id.state);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        state.setText("IDLE");

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state.getText().toString().equals("IDLE")) mHandler.post(mRunnable);
                musicService.play();
                if (musicService.mediaPlayer.isPlaying()) {
                    playBtn.setText("PAUSE");
                    state.setText("Playing");
                    rotate = true;
                } else {
                    playBtn.setText("PLAY");
                    state.setText("Pause");
                    rotate = false;
                }
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rotate = false;
                musicService.stop();
                playBtn.setText("PLAY");
                state.setText("Stop");
            }
        });

        quitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                state.setText("IDLE");
                mHandler.removeCallbacks(mRunnable);
                unbindService(serviceConnection);
                try {
                    MainActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            TextView currentTime = (TextView) findViewById(R.id.currentTime);
            TextView totalTime = (TextView) findViewById(R.id.totalTime);
            ImageView image = (ImageView) findViewById(R.id.image);
            currentTime.setText(time.format(musicService.mediaPlayer.getCurrentPosition()));
            totalTime.setText(time.format(musicService.mediaPlayer.getDuration()));
            if (rotate) image.setRotation(angle++);
            seekBar.setProgress(musicService.mediaPlayer.getCurrentPosition());
            seekBar.setMax(musicService.mediaPlayer.getDuration());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    TextView state = (TextView) findViewById(R.id.state);
                    if (state.getText().toString().equals("IDLE") || state.getText().toString().equals("Stop"))
                        return;
                    if (fromUser)
                        musicService.mediaPlayer.seekTo(seekBar.getProgress());
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            mHandler.postDelayed(mRunnable, 100);
        }
    };

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder)service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
