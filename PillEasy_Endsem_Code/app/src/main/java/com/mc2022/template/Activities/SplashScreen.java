package com.mc2022.template.Activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.R;


@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {

    SQLiteHelper helper;
    public static final String MyPREFERENCES = "login_check";
    SharedPreferences sp;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        sp = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        status = sp.getString("Status", "");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        helper = new SQLiteHelper(this);
//        helper.onUpgrade();
        int SPLASH_TIME_OUT = 2000;
        new Handler().postDelayed(() -> {
            if (helper.checkFirst()) {
                helper.markFirst();
                Intent intent = new Intent(SplashScreen.this, WelcomeActivity.class);
                startActivity(intent);
            } else {
                if (!status.equals("")) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreen.this, SignUpActivity.class);
                    startActivity(intent);
                }
            }
            // close this activity
            finish();
        }, SPLASH_TIME_OUT);
    }
}
