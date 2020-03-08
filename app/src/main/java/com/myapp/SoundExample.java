package com.myapp;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;

/**
 * Created by Ignacio on 15/02/2020.
 */

public class SoundExample {

    public boolean isPlayWhenReady = false;

    // originally from http://marblemice.blogspot.com/2010/04/generate-and-play-tone-in-android.html
    // and modified by Steve Pomeroy <steve@staticfree.info>
    private int sampleRate = 8000;
    private double freqOfTone = 440; // hz
    private double duration = 1.0; // seconds
    private int numSamples;
    private double sample[];
    private byte generatedSnd[];

    private Handler handler;
    private AudioTrack audioTrack;

    private boolean isReady = false;
    private boolean isPlaying = false;

    private class CreatorThread implements Runnable {
        public void run() {
            genTone();
            handler.post(new PlayerThread());
        }
    }

    private class PlayerThread implements Runnable {
        public void run() {
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC,
                    sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO, //CHANNEL_OUT_MONO
                    AudioFormat.ENCODING_PCM_16BIT, generatedSnd.length,
                    AudioTrack.MODE_STATIC);
            audioTrack.write(generatedSnd, 0, generatedSnd.length);
            isReady = true;
            if(isPlayWhenReady) playTestSound();
        }
    }

    // CONSTRUCTOR
    public SoundExample(double freq, double duration){
        this.freqOfTone = freq;
        this.duration = duration;

        this.numSamples = (int) (duration * sampleRate);
        this.sample = new double[numSamples];
        this.generatedSnd = new byte[2 * numSamples];

        this.handler = new Handler();
        (new Thread(new CreatorThread())).start();
    }



    public void playTestSound(){
        if(isReady) playSound();
    }

    protected void onResume() {
        if(isReady) playSound();
    }



    void genTone(){
        // fill out the array
        for (int i = 0; i < numSamples; ++i) {
            sample[i] = Math.sin(2 * Math.PI * i / (sampleRate/freqOfTone));
        }

        // convert to 16 bit pcm sound array
        // assumes the sample buffer is normalised.
        int idx = 0;
        for (final double dVal : sample) {
            // scale to maximum amplitude
            final short val = (short) ((dVal * 32767));
            // in 16 bit wav PCM, first byte is the low order byte
            generatedSnd[idx++] = (byte) (val & 0x00ff);
            generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);

        }
    }

    void playSound(){
        if(isPlaying) {
            audioTrack.stop();
            audioTrack.reloadStaticData();
        }

        isPlaying = true;
        audioTrack.play();
    }

}
