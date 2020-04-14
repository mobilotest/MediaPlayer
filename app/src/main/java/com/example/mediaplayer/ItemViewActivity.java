package com.example.mediaplayer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

public class ItemViewActivity extends AppCompatActivity {

    private ImageView headerImage;

    private Button btnPlay, btnPause, btnStop, btnMainMenu;
    private TextView tv_Header, tv_Name, tvText, tvStartTime, tvFinalTime;
    private SeekBar seekBar;

    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;

    Proverb proverb;

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    private Handler myHandler = new Handler();
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mMediaPlayer.pause();
                        mMediaPlayer.seekTo(0);
                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mMediaPlayer.start();
                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        // Your app has been granted audio focus again
                        // Raise volume to normal, restart playback if necessary
                        releaseMediaPlayer();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemview);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        Intent intent = getIntent();

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPause.setEnabled(false);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnPlay.setBackgroundResource(R.drawable.ic_play_arrow_black_48dp);
        btnPause.setBackgroundResource(R.drawable.ic_pause_black_48dp);
        btnStop.setBackgroundResource(R.drawable.ic_stop_black_48dp);
        btnMainMenu = (Button) findViewById(R.id.btnMenu);

        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        tvFinalTime = (TextView) findViewById(R.id.tvStopTime);

        tvText = (TextView) findViewById(R.id.text_proverb);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (intent.getExtras() != null) {
            proverb = (Proverb) intent.getSerializableExtra("item");
        }

        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //getting view in list_item
        headerImage = findViewById(R.id.img_Header);
        tv_Header = findViewById(R.id.tv_Header);
        tv_Name = findViewById(R.id.tv_Name);
        tvText = findViewById(R.id.text_proverb);

        //set the data
        headerImage.setImageResource(proverb.getmImage());
        tv_Header.setText(proverb.getmProverb());
        tv_Name.setText(proverb.getmName());
        tvText.setText(proverb.getmText());
        tvText.setMovementMethod(new ScrollingMovementMethod());

        mMediaPlayer = MediaPlayer.create(this, R.raw.bludnyi_syn);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        btnPause.setEnabled(false);

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                mMediaPlayer.start();

                finalTime = mMediaPlayer.getDuration();
                startTime = mMediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                    oneTimeOnly = 1;
                }

                tvFinalTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                tvStartTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                seekBar.setProgress((int) startTime);
                btnPause.setEnabled(true);
                btnPlay.setEnabled(false);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                mMediaPlayer.pause();
                btnPause.setEnabled(false);
                btnPlay.setEnabled(true);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Stop sound", Toast.LENGTH_SHORT).show();
                mMediaPlayer.pause();
                btnPause.setEnabled(true);
                btnPlay.setEnabled(true);
            }
        });

        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemViewActivity.this, MainActivity.class));
            }
        });
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mMediaPlayer.getCurrentPosition();
            tvStartTime.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
            btnPause.setEnabled(true);
            btnPlay.setEnabled(false);
        }
    };

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Abandon audio focus when playback complete
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    //getting back to listview
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}