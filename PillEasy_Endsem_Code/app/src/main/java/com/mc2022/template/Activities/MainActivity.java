package com.mc2022.template.Activities;

import static com.mc2022.template.Utilities.Utilities.getDates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mc2022.template.Adapters.ViewPagerDateAdapter;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    RelativeLayout NoMedicines;
    public static final String MyPREFERENCES = "login_check";
    SharedPreferences sp;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpagerday);
        NoMedicines = findViewById(R.id.NoMedicines);
        FloatingActionButton fab = findViewById(R.id.fab);

        sp = this.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        SQLiteHelper helper = new SQLiteHelper(MainActivity.this);
        String[] start_end_dates = helper.getDateRange();

        if (start_end_dates == null) {
            NoMedicines.setVisibility(View.VISIBLE);
            viewPager.setVisibility(View.INVISIBLE);
        } else {
            NoMedicines.setVisibility(View.INVISIBLE);
            viewPager.setVisibility(View.VISIBLE);
            Date start = null;
            Date end = null;
            try {
                start = new SimpleDateFormat("yyyy-MM-dd").parse(start_end_dates[0]);
                end = new SimpleDateFormat("yyyy-MM-dd").parse(start_end_dates[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            Log.e("MainActivity", date);
            ArrayList<String> dates = getDates(start_end_dates[0], start_end_dates[1]);
            int pos = dates.indexOf(date);
            ViewPagerDateAdapter adapter = new ViewPagerDateAdapter(this, dates);
            viewPager.setAdapter(adapter);
            new Handler().postDelayed(() -> viewPager.setCurrentItem(pos, false), 100);
        }

        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddMedicine.class);
            startActivity(intent);
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.detailsPage: {
                Intent intent = new Intent(MainActivity.this, UserBPSugarForm.class);
                startActivity(intent);
                return true;
            }
            case R.id.Prescriptions: {
                Intent intent = new Intent(MainActivity.this, Prescriptions_Activity.class);
                startActivity(intent);
                return true;
            }
            case R.id.logout: {
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("Status", "");
                editor.apply();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            default: {
                return true;
            }
        }
    }
}