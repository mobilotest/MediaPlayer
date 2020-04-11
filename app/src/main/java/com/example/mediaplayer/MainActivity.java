package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        // create an array of words
        final ArrayList<Name> words = new ArrayList<Name>();
        words.add(new Name("father", "әpә", R.drawable.family_father, R.raw.family_father));
        words.add(new Name("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        words.add(new Name("son", "angsi", R.drawable.family_son, R.raw.family_son));
        words.add(new Name("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Name("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Name("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Name("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Name("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}