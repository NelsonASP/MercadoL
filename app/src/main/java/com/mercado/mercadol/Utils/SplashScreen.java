package com.mercado.mercadol.Utils;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mercado.mercadol.MainActivity;
import com.mercado.mercadol.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends Activity {

    public static int SPLASH_TIME_OUT = 5000;
    private ImageView ivLogo, ivLogo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ivLogo  = findViewById(R.id.imageicon);
        ivLogo2 = findViewById(R.id.imageicon2);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.transition);
        ivLogo.startAnimation(myanim);
        ivLogo2.startAnimation(myanim);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask,SPLASH_TIME_OUT);
    }
}