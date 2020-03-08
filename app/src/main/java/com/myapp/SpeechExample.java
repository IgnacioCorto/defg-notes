package com.myapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

/**
 * Created by Ignacio on 15/02/2020.
 */

public class SpeechExample {

    // text to speech
    private TextToSpeech textToSpeech;
    Context appContext;
    boolean isReady = false;


    public SpeechExample(Context context) {
        appContext = context;

        textToSpeech = new TextToSpeech(appContext, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {

//                    Log.i("TTS", Arrays.toString(Locale.getAvailableLocales()));

                    boolean lang_found = false;

                    for(Locale ll: Locale.getAvailableLocales()) {
                        int ttsLang = textToSpeech.setLanguage(ll);  //Locale.US

                        if (ttsLang == TextToSpeech.LANG_MISSING_DATA
                                || ttsLang == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "The Language is not supported!");
                        } else {
                            Log.i("TTS", "Language Supported.");
                            lang_found = true;
                            break;
                        }
                    }


//                    if(!lang_found) {
//                        // no language data available, prompt for install
//                        Intent installIntent = new Intent();
//                        installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
//                        startActivity(installIntent);
//                    }




                    Log.i("TTS", "Initialization success.");
                    isReady = true;
                } else {
                    Toast.makeText(appContext, "TTS Initialization failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void doSpeak(String data) {
        if(!isReady || textToSpeech.isSpeaking()) return;
        Log.i("TTS", "button clicked: " + data);
        int speechStatus = textToSpeech.speak(data, TextToSpeech.QUEUE_FLUSH, null);

        if (speechStatus == TextToSpeech.ERROR) {
            Log.e("TTS", "Error in converting Text to Speech!");
        }
    }

    public void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

}
