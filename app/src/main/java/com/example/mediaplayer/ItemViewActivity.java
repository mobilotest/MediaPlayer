package com.example.mediaplayer;

import android.content.Intent;
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

    private Button btnPlay, btnPause, btnMainMenu;
    private TextView tv_Header, tv_Name, tvText, tvStartTime, tvFinalTime;
    private SeekBar seekBar;

    private double startTime = 0;
    private double finalTime = 0;
    public static int oneTimeOnly = 0;

    Proverb proverb;

    private Handler myHandler = new Handler();

    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mMediaPlayer;
    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemview);

        Intent intent = getIntent();

        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnPause.setEnabled(false);
        btnPlay.setBackgroundResource(R.drawable.ic_play_arrow_black_48dp);
        btnPause.setBackgroundResource(R.drawable.ic_pause_black_48dp);
        btnMainMenu = (Button) findViewById(R.id.btnMenu);

        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        tvFinalTime = (TextView) findViewById(R.id.tvStopTime);

        tvText = (TextView) findViewById(R.id.text_proverb);

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

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setClickable(false);
        btnPause.setEnabled(false);

        // Start playback
        // Create and setup the {@link MediaPlayer} for the audio resource associated with the current proverb
        mMediaPlayer = MediaPlayer.create(ItemViewActivity.this, proverb.getmAudio());
        // Start the audio file
        mMediaPlayer.start();
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);

        finalTime = mMediaPlayer.getDuration() - mMediaPlayer.getCurrentPosition();
        startTime = mMediaPlayer.getCurrentPosition();

        if (oneTimeOnly == 0) {
            seekBar.setMax((int) finalTime);
        }

        tvFinalTime.setText(String.format("%d min, %d sec",
                TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                startTime)))
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
        myHandler.postDelayed(UpdateSongTime, 100);

        /**
         *   This method responsible for Audion behavior when PLAY btn has clicked
         * */
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Playing sound", Toast.LENGTH_SHORT).show();
                mMediaPlayer.start();

                startTime = mMediaPlayer.getCurrentPosition();
                finalTime = mMediaPlayer.getDuration() - mMediaPlayer.getCurrentPosition();

                if (oneTimeOnly == 0) {
                    seekBar.setMax((int) finalTime);
                }

                tvStartTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );

                tvFinalTime.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                seekBar.setProgress((int) startTime);
                myHandler.postDelayed(UpdateSongTime, 100);

                btnPause.setEnabled(true);
                btnPlay.setEnabled(false);

            }
        });

        /**
         *   This method responsible for Audion behavior when PAUSE btn has clicked
         * */
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Pausing sound", Toast.LENGTH_SHORT).show();
                mMediaPlayer.pause();
                btnPause.setEnabled(false);
                btnPlay.setEnabled(true);
            }
        });

        /**
         *   This method responsible for back to the MAIN MENU navigation when btn has clicked
         * */
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
                                    toMinutes((long) startTime))));

            finalTime = mMediaPlayer.getDuration() - mMediaPlayer.getCurrentPosition();
            tvFinalTime.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) finalTime))));
            seekBar.setProgress((int) startTime);
            myHandler.postDelayed(this, 100);
        }
    };

    //getting back to listview
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
        }
        if (myHandler != null) myHandler.removeCallbacks(null);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (myHandler != null)
            myHandler.removeCallbacks(null);
        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (myHandler != null)
            myHandler.removeCallbacks(UpdateSongTime);
        // When the activity is stopped, release the media player resources because we won't be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.stop();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;
        }
    }
}