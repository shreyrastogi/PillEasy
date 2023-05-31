package com.mc2022.template.Activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.mc2022.template.Adapters.BPSugarAdapter;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.Models.UserBPSugar;
import com.mc2022.template.R;
import com.mc2022.template.Utilities.SnackBarUtility;
import com.mc2022.template.Utilities.date_picker;

import java.util.ArrayList;
import java.util.Calendar;

public class BPSugar_RecyclerView extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    SQLiteHelper helper;
    RecyclerView recyclerView;
    ArrayList<UserBPSugar> dataholder;
    MaterialTextView date_start_end, start_date, end_date;
    MaterialButton generateData;
    BPSugarAdapter adapter;
    String start = "", end = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bpsugar_recycler_view);

        helper = new SQLiteHelper(this);
        start_date = findViewById(R.id.startdate);
        end_date = findViewById(R.id.enddate);
        generateData = findViewById(R.id.generateData);

        recyclerView = (RecyclerView) findViewById(R.id.bpSugarrecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataholder = helper.readalldata();
        adapter = new BPSugarAdapter(dataholder);
        recyclerView.setAdapter(adapter);

        generateData.setOnClickListener(view -> {
            start = start_date.getText().toString().trim();
            end = end_date.getText().toString().trim();

            if (start.equals("Start Date") || end.equals("End Date")) {
                SnackBarUtility.ShowSnackbar("Please provide a date range", findViewById(R.id.bp_sugar_recycler_view_layout));
            } else {
                dataholder = helper.readalldatainRange(start, end);
                adapter = new BPSugarAdapter(dataholder);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bp_sugar_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.trends: {
//                start = start_date.getText().toString().trim();
//                end = end_date.getText().toString().trim();

                if (start.equals("Start Date") || end.equals("End Date")) {
                    SnackBarUtility.ShowSnackbar("Please provide a date range", findViewById(R.id.bp_sugar_recycler_view_layout));
                } else {
                    Intent intent = new Intent(BPSugar_RecyclerView.this, BPSugarWeight_GraphView.class);
                    intent.putExtra("startdate", start);
                    intent.putExtra("enddate", end);
                    startActivity(intent);
                }
                return true;
            }
            case R.id.send_email: {
//                start = start_date.getText().toString().trim();
//                end = end_date.getText().toString().trim();

                if (start.equals("Start Date") || end.equals("End Date")) {
                    SnackBarUtility.ShowSnackbar("Please provide a date range", findViewById(R.id.bp_sugar_recycler_view_layout));
                } else {
                    dataholder = helper.readalldatainRange(start, end);
                    StringBuilder data = new StringBuilder();
                    for (int i = 0; i < dataholder.size(); i++) {
                        UserBPSugar temp = dataholder.get(i);
                        data.append("Data for date: ").append(temp.getDateMain()).append("\n ->Weight: ").append(temp.getWeight()).append("\n ->BP: ").append(temp.getBp()).append("\n ->Sugar: ").append(temp.getSugar()).append("\n \n");
                    }

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_TEXT, (CharSequence) data);
                    startActivity(intent);
                }
                return true;
            }
            default: {
                return true;
            }
        }
    }

    public void date_click(View view) {
        if (view.getId() == R.id.startdate) {
            date_start_end = findViewById(R.id.startdate);
            DialogFragment date_pick = new date_picker();
            date_pick.show(getSupportFragmentManager(), "date_picker");
        } else {
            date_start_end = findViewById(R.id.enddate);
            DialogFragment date_pick = new date_picker();
            date_pick.show(getSupportFragmentManager(), "date_picker");
        }
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, i2);
        cal.set(Calendar.YEAR, i);
        cal.set(Calendar.MONTH, i1);
        i1 = i1 + 1;
        int year = i;
        String month1 = i1 < 10 ? "0" + i1 : String.valueOf(i1);
        String day1 = i2 < 10 ? "0" + i2 : String.valueOf(i2);
        String current = year + "-" + month1 + "-" + day1;
        Log.e("Date", current);
        date_start_end.setText(current);
    }
}