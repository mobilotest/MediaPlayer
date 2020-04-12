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


        // create an array of words
        final ArrayList<Proverb> names = new ArrayList<Proverb>();
        names.add(new Proverb(R.string.header_3_parable, R.string.proverb_contractors, R.drawable.nayomniki);
        names.add(new Proverb(R.string.header_3_parable, R.string.proverb_dig_talent, R.drawable.talent));
        names.add(new Proverb(R.string.header_3_parable, R.string.good_samaritan, R.drawable.good));
        names.add(new Proverb(R.string.header_3_parable, R.string.proverb_invited_chosen, R.drawable.pir));
        names.add(new Proverb(R.string.header_3_parable, R.string.proverb_prodigal_son, R.drawable.prodigal));
        names.add(new Proverb(R.string.header_3_parable, R.string.proverb_sower, R.drawable.sower));
        names.add(new Proverb(R.string.header_3_parable, R.string.proverb_ten_virgins, R.drawable.virgins));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_family);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}