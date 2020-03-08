package com.myapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    //basic button
    Button FunctionCalling;

    SpeechExample speechExample;
    SoundExample soundExampleD;
    SoundExample soundExampleE;
    SoundExample soundExampleF;
    SoundExample soundExampleG;
    SoundExample notes[];
    int current;
    int count_correct;

    String info = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechExample = new SpeechExample(getApplicationContext());
        soundExampleD = new SoundExample(293,0.3);
        soundExampleE = new SoundExample(329,0.3);  //329 261
        soundExampleF = new SoundExample(349,0.3);
        soundExampleG = new SoundExample(392,0.3);

        ((Button) findViewById(R.id.btn_D)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doToast("Sound", "Un sonido breve");
//                soundExampleD.playTestSound();
                evaluateClick("D", 0);
            }
        });

        ((Button) findViewById(R.id.btn_E)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doToast("TTS", "Un saludito");
//                speechExample.createSpeak();
//                soundExampleE.playTestSound();
                evaluateClick("E", 1);
            }
        });

        ((Button) findViewById(R.id.btn_F)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                soundExampleF.playTestSound();
                evaluateClick("F", 2);
            }
        });

        ((Button) findViewById(R.id.btn_G)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                soundExampleG.playTestSound();
                evaluateClick("G", 3);
            }
        });

        notes = new SoundExample[4];
        notes[0] = soundExampleD;
        notes[1] = soundExampleE;
        notes[2] = soundExampleF;
        notes[3] = soundExampleG;

        pickNote();
        notes[current].isPlayWhenReady = true;

        ((Button) findViewById(R.id.btn_Current)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                doToast("Sound", "Un sonido breve");
                info = current +" SOUND\n" + info;
                ((EditText) findViewById(R.id.txt_Info)).setText(info);
                playCurrent();
            }
        });

    }


    private void pickNote(){
        current = (int) Math.floor(Math.random()*4);
    }

    private void playCurrent(){
        notes[current].playTestSound();
    }


    private void evaluateClick(String noteLetter, int noteIndex){
        boolean isValid = current == noteIndex;
        String label;

        if(isValid){
            label = "GOOD";
            count_correct++;
            pickNote();
            playCurrent();
        } else {
            label = "BAD ("+count_correct+")";
            speechExample.doSpeak(""+count_correct);
            count_correct = 0;
        }

        info = noteLetter + " " + label + "\n" + info;
        ((EditText) findViewById(R.id.txt_Info)).setText(info);
    }


    private void doToast(String btnName, String message){
        info += btnName + " clicked\n";
        ((EditText) findViewById(R.id.txt_Info)).setText(info);
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    // function declaration
    private void functionCallingExample() {
//        speechExample.createSpeak();
//        soundExample.playTestSound();
    }



    @Override
    protected void onResume() {
        super.onResume();
//        soundExample.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        speechExample.onDestroy();
    }


    private void killApp() {

    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) return;

        Toast.makeText(MainActivity.this, "Presionaste HOME", Toast.LENGTH_SHORT).show();
        finish();
        System.exit(1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // your code
            finish();
            System.exit(1);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_HOME) {
            // your code
            Toast.makeText(MainActivity.this, "Presionaste HOME", Toast.LENGTH_SHORT).show();
            finish();
            System.exit(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}