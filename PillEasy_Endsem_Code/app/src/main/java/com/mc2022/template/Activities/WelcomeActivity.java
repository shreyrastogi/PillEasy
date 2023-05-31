package com.mc2022.template.Activities;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.mc2022.template.Adapters.SliderAdapter;
import com.mc2022.template.R;
import com.mc2022.template.Services.JobSchedulingService;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";
    private ViewPager SlideViewPager;
    private LinearLayout linearLayout;
    private TextView[] Dots;

    private Button prev;
    private Button nxt;
    private int CurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        scheduleJob();

        SlideViewPager = findViewById(R.id.SlideViewPager);
        linearLayout = findViewById(R.id.linearLayout);
        prev = findViewById(R.id.textButton);
        nxt = findViewById(R.id.containedButton);
        SliderAdapter sliderAdapter = new SliderAdapter(this);

        SlideViewPager.setAdapter(sliderAdapter);
        addDotsIndicator(0);
        SlideViewPager.addOnPageChangeListener(viewListener);

        //Onclick listener

        nxt.setOnClickListener(view -> {
            if (CurrentPage == Dots.length - 1) {
                Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
                startActivity(intent);
                finish();
            } else {
                SlideViewPager.setCurrentItem(CurrentPage + 1);
            }
        });

        prev.setOnClickListener(view -> {
            Intent intent = new Intent(WelcomeActivity.this, SignUpActivity.class);
            startActivity(intent);
            finish();
        });

    }

    public void addDotsIndicator(int position) {
        Dots = new TextView[3];
        linearLayout.removeAllViews();
        for (int i = 0; i < Dots.length; i++) {
            Dots[i] = new TextView(this);
            Dots[i].setText(Html.fromHtml("&#8226"));
            Dots[i].setTextSize(40);
            Dots[i].setTextColor(getResources().getColor(R.color.darkgray));
            linearLayout.addView(Dots[i]);

        }
        if (Dots.length > 0) {
            Dots[position].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onPageSelected(int position) {
            addDotsIndicator(position);
            CurrentPage = position;
            if (position == 0) {
                nxt.setEnabled(true);
                prev.setEnabled(false);
                prev.setVisibility(View.INVISIBLE);
                nxt.setText("NEXT");
                prev.setText("");
            } else if (position == Dots.length - 1) {
                nxt.setEnabled(true);
                prev.setEnabled(true);
                prev.setVisibility(View.VISIBLE);
                nxt.setText("READY");
                prev.setText("SKIP");
            } else {
                nxt.setEnabled(true);
                prev.setEnabled(true);
                prev.setVisibility(View.VISIBLE);
                nxt.setText("NEXT");
                prev.setText("SKIP");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void scheduleJob() {
        @SuppressLint("JobSchedulerService") ComponentName componentName = new ComponentName(this, JobSchedulingService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setPersisted(true)
                .setPeriodic(24 * 60 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

}