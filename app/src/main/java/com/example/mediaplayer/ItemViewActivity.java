package com.example.mediaplayer;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ItemViewActivity extends AppCompatActivity {

    private ImageView headerImage;

    private Button btnPlay, btnPause, btnStop, btnMainMenu;
    private TextView tv_Header, tv_Name, tvStartTime, tvFinalTime;
    private SeekBar seekBar;

    private double startTime = 0;
    private double finalTime = 0;

    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    Proverb proverb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemview);

        headerImage = findViewById(R.id.imageView);
        tv_Header = findViewById(R.id.tv_Header);
        tv_Name = findViewById(R.id.tv_Name);

        Intent intent = getIntent();

        if (intent.getExtras() != null) {
            proverb = (Proverb) intent.getSerializableExtra("item");
        }

        headerImage.setImageResource(proverb.getmImage());
        tv_Header.setText(proverb.getmProverb());
        tv_Name.setText(proverb.getmName());


        //enable back Button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


//public class ListDataActivity extends AppCompatActivity {
//
//    /**
//     * Handles playback of all the sound files
//     */
//    private ImageView headerImage;
//
//    private Button btnPlay,btnPause,btnStop,btnMainMenu;
//    private TextView tv_Header, tv_Name, tvStartTime, tvFinalTime;
//    private SeekBar seekBar;
//
//    private double startTime = 0;
//    private double finalTime = 0;
//
//    private MediaPlayer mMediaPlayer;
//    private AudioManager mAudioManager;
//
//    public static int oneTimeOnly = 0;
//    private MediaPlayer.OnCompletionListener mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
//        @Override
//        public void onCompletion(MediaPlayer mp) {
//            releaseMediaPlayer();
//        }
//    };
//
//    private Handler handler = new Handler();
//    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
//            new AudioManager.OnAudioFocusChangeListener() {
//                public void onAudioFocusChange(int focusChange) {
//                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
//                        mMediaPlayer.pause();
//                        mMediaPlayer.seekTo(0);
//                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
//                        mMediaPlayer.start();
//                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
//                        // Your app has been granted audio focus again
//                        // Raise volume to normal, restart playback if necessary
//                        releaseMediaPlayer();
//                    }
//                }
//            };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        headerImage = (ImageView) findViewById(R.id.img_Header);
//        tv_Header = (TextView) findViewById(R.id.tv_Header);
//        tv_Name = (TextView) findViewById(R.id.tv_Name);
//
//
//        Intent intent = getIntent();
//        headerImage.setImageResource(intent.getIntExtra("image", 0));
//        tv_Header.setText(R.string.header_3_parable);
//        tv_Name.setText(intent.getStringExtra("name"));
//
//
//
//        btnPlay = (Button) findViewById(R.id.btnPlay);
//        btnPause = (Button) findViewById(R.id.btnPause);
//        btnPause.setEnabled(false);
//        btnStop = (Button) findViewById(R.id.btnStop);
//        btnMainMenu = (Button) findViewById(R.id.btnMenu);
//
//        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
//        seekBar = (SeekBar) findViewById(R.id.seekBar);
//        seekBar.setClickable(false);
//        tvFinalTime = (TextView) findViewById(R.id.tvStopTime);
//
//        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//
//
//        btnPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Playing sound",Toast.LENGTH_SHORT).show();
//                mMediaPlayer.start();
//
//                finalTime = mMediaPlayer.getDuration();
//                startTime = mMediaPlayer.getCurrentPosition();
//
//                if (oneTimeOnly == 0) {
//                    seekBar.setMax((int) finalTime);
//                    oneTimeOnly = 1;
//                }
//
//                tvFinalTime.setText(String.format("%d min, %d sec",
//                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
//                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
//                                        finalTime)))
//                );
//
//                tvStartTime.setText(String.format("%d min, %d sec",
//                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
//                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
//                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
//                                        startTime)))
//                );
//
//                seekBar.setProgress((int)startTime);
//                btnPause.setEnabled(true);
//                btnPlay.setEnabled(false);
//            }
//        });
//
//        btnPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Pausing sound",Toast.LENGTH_SHORT).show();
//                mMediaPlayer.pause();
//                btnPause.setEnabled(false);
//                btnPlay.setEnabled(true);
//            }
//        });
//
//        // Request audio focus for playback
//        int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
//                // Use the music stream.
//                AudioManager.STREAM_MUSIC,
//                // Request permanent focus.
//                AudioManager.AUDIOFOCUS_GAIN);
//
//        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
//
//            // Create and setup the {@link MediaPlayer} for the audio resource associated
//            // with the current word
//            mMediaPlayer = MediaPlayer.create(TenVirgins.this, R.raw.ten_dev);
//
//            // Start the audio file
//            mMediaPlayer.start();
//
//            mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        // When the activity is stopped, release the media player resources because we won't
//        // be playing any more sounds.
//        releaseMediaPlayer();
//    }
//
//    /**
//     * Clean up the media player by releasing its resources.
//     */
//    private void releaseMediaPlayer() {
//        // If the media player is not null, then it may be currently playing a sound.
//        if (mMediaPlayer != null) {
//            // Regardless of the current state of the media player, release its resources
//            // because we no longer need it.
//            mMediaPlayer.release();
//
//            // Set the media player back to null. For our code, we've decided that
//            // setting the media player to null is an easy way to tell that the media player
//            // is not configured to play an audio file at the moment.
//            mMediaPlayer = null;
//
//            // Abandon audio focus when playback complete
//            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
//        }
//    }
//
//    private Runnable UpdateSongTime = new Runnable() {
//        public void run() {
//            startTime = mMediaPlayer.getCurrentPosition();
//            tvStartTime.setText(String.format("%d min, %d sec",
//                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
//                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
//                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
//                                    toMinutes((long) startTime)))
//            );
//            seekBar.setProgress((int)startTime);
//        }
//    };
//}
