package com.example.mediaplayer;

import java.io.Serializable;

public class Proverb implements Serializable {

    /** Image resource ID for the proverb */
    private int mImage;

    /** Default header - same for each proverb */
    private String mProverb;

    /** Exact name for the proverb */
    private String mName;

    /** Audio resource ID for the proverb */
    private int mAudio;

    /** Text of the proverb */
    private String mText;

    /**
     * Create a new Proverb object.
     * @param mImage    is the images for each proverb
     * @param mProverb   is the just default prefix 'The proverb ...'
     * @param mName  is the second part with proverb exact name
     * @param mAudio  is the audio file id for each proverb
     * @param mText  is the full text for each proverb
     */
    public Proverb(int mImage, String mProverb, String mName, String mText, int mAudio) {
        this.mImage = mImage;
        this.mProverb = mProverb;
        this.mName = mName;
        this.mText = mText;
        this.mAudio = mAudio;
    }

    /**
     * Get the image for each proverb.
     * @return
     */
    public int getmImage() {
        return mImage;
    }

    /**
     * Get the default part of the proverb name.
     * @return
     */
    public String getmProverb() {
        return mProverb;
    }

    /**
     * Get the second part with the exact proverb's name.
     * @return
     */
    public String getmName() {
        return mName;
    }

    /**
     * Get the image for each proverb.
     * @return
     */
    public String getmText() {
        return mText;
    }

    /**
     * Get the image for each proverb.
     * @return
     */
    public int getmAudio() {
        return mAudio;
    }
}
