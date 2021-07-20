package com.example.synthwavesyntesator;

import android.app.Activity;
import android.graphics.Color;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity implements OnTouchListener {

    //private View C4_but, D4_but, E4_but, F4_but, G4_but, A4_but, B4_but;
    Button C4_but, D4_but, E4_but, F4_but, G4_but, A4_but, B4_but;
    private MediaPlayer C4sample, D4sample, E4sample, F4sample, G4sample, A4sample, B4sample;

    TextView tv;
    LinearLayout linearLayout;
    float x;
    float y;
    String sDown;
    String sMove;
    String sUp;

    int width;
    int height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        C4sample = MediaPlayer.create(this, R.raw.c4);
        D4sample = MediaPlayer.create(this, R.raw.d4);
        E4sample = MediaPlayer.create(this, R.raw.e4);
        F4sample = MediaPlayer.create(this, R.raw.f4);
        G4sample = MediaPlayer.create(this, R.raw.g4);
        A4sample = MediaPlayer.create(this, R.raw.a4);
        B4sample = MediaPlayer.create(this, R.raw.b4);

        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();  // deprecated
        height = display.getHeight();  // deprecated

        tv = new TextView(this);
        tv.setOnTouchListener(this);
        setContentView(tv);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                sDown = "Down: " + x + "," + y;
                sMove = ""; sUp = "";
                break;
            case MotionEvent.ACTION_MOVE: // движение
                sMove = "Move: " + x + "," + y;
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                sMove = "";
                sUp = "Up: " + x + "," + y;
                break;
        }
        int millis = 70;
        tv.setText(sMove + "HEIGHT:" + height);
        AudioTrack at;
        if(y < height*0.14){
            tv.setTextColor(Color.YELLOW);
            at = generateTone(261, millis);
            at.play();
        } else if (y < height*0.14*2){
            tv.setTextColor(Color.BLUE);
            at = generateTone(293, millis);
            at.play();
        } else if (y < height*0.14*3){
            tv.setTextColor(Color.RED);
            at = generateTone(330, millis);
            at.play();
        } else if (y < height*0.14*4){
            tv.setTextColor(Color.BLACK);
            at = generateTone(349, millis);
            at.play();
        } else if (y < height*0.14*5){
            tv.setTextColor(Color.GRAY);
            at = generateTone(392, millis);
            at.play();
        } else if (y < height*0.14*6){
            tv.setTextColor(Color.GRAY);
            at = generateTone(440, millis);
            at.play();
        } else if (y < height*0.14*7){
            tv.setTextColor(Color.MAGENTA);
            at = generateTone(494, millis);
            at.play();
        }
        return true;
    }
    /*
    С4 - 261
    D4 - 293
    E4 - 330
    F4 - 349
    G4 - 392
    A4 - 440
    B4 - 494
     */

    private AudioTrack generateTone(double freqHz, int durationMs)
    {
        int sampleRate = 48000;  // 44100 Hz 48000
        int count = (int)( sampleRate * 2.0 * (durationMs / 1000.0)) & ~1;
        short[] samples = new short[count];
        for(int i = 0; i < count; i += 2){
            short sample = (short)(Math.sin(2 * Math.PI * i / (sampleRate / freqHz)) * 0x7FFF);
            samples[i + 0] = sample;
            samples[i + 1] = sample;
        }
        AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
                AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,
                count * (Short.SIZE / 8), AudioTrack.MODE_STATIC);
        track.write(samples, 0, count);
        return track;
    }
}
