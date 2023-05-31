package com.mc2022.template.Utilities;

import static android.content.Context.ALARM_SERVICE;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import com.mc2022.template.BroadcastReceivers.NotifyBroadcast;
import com.mc2022.template.Database.SQLiteHelper;
import com.mc2022.template.Models.Medicine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Utilities {
    public static ArrayList<String> getDates(String startDate, String endDate) {
        ArrayList<String> dates = new ArrayList<>();
        Calendar start = getDate1(startDate);
        Calendar end = getDate1(endDate);
        while (start.before(end)) {
            int year = start.get(Calendar.YEAR);
            int month = start.get(Calendar.MONTH) + 1;
            int day = start.get(Calendar.DATE);
            String month1 = month < 10 ? "0" + month : String.valueOf(month);
            String day1 = day < 10 ? "0" + day : String.valueOf(day);
            String d = year + "-" + month1 + "-" + day1;
            dates.add(d);
            start.add(Calendar.DATE, 1);
        }
        return dates;
    }

    @SuppressLint("SimpleDateFormat")
    private static Calendar getDate1(String date) {
        Calendar calendar = Calendar.getInstance();
        Date date1 = null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date1);
        return calendar;
    }

    public static int convertDay(String s) {
        switch (s) {
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
            case "Saturday":
                return 6;
            case "Sunday":
                return 0;
        }
        return -1;
    }


    @SuppressLint("LongLogTag")
    public static void setAlarms(Context context, int new_entry) {

        createNotificationChannel(context);
        SQLiteHelper helper = new SQLiteHelper(context);

        Log.e("service", "Here");
        // Toast.makeText(context, "Yo yo", Toast.LENGTH_LONG).show();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdf.format(new Date());

        Log.e("CurrentDate12345", currentDate);

        ArrayList<Medicine> filteredMedicine = helper.getMedicines(currentDate);
        Log.e("Medicine from Scheduling service", String.valueOf(filteredMedicine));

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Log.e("CurrentDate in milliseconds", String.valueOf(sdf1.parse(currentDate + " 00:00").getTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long currentTimeinmillis = System.currentTimeMillis();
        int count = 0;
        for (Medicine m : filteredMedicine) {
            String dateandTime = currentDate + " " + m.getDose().getTiming();
            try {
                Date date = sdf1.parse(dateandTime);
                long timeinmilliseconds = 0;
                if (date != null) {
                    timeinmilliseconds = date.getTime();
                }
                Log.e("Time in milliseconds", String.valueOf(timeinmilliseconds));
                if (new_entry == 1) {
                    if (timeinmilliseconds >= currentTimeinmillis) {
                        Log.e("New entry one", String.valueOf(timeinmilliseconds));
                        Intent intent = new Intent(context, NotifyBroadcast.class);
                        intent.putExtra("medicine_name", m.getMedicine_name());
                        intent.putExtra("dosage", String.valueOf(m.getDose().getDose()));
                        @SuppressLint("InlinedApi") PendingIntent pendingIntent = PendingIntent.getBroadcast(context, count++, intent, PendingIntent.FLAG_MUTABLE);
                        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                        alarmManager.set(AlarmManager.RTC_WAKEUP, timeinmilliseconds, pendingIntent);
                    }
                } else {
                    Intent intent = new Intent(context, NotifyBroadcast.class);
                    intent.putExtra("medicine_name", m.getMedicine_name());
                    intent.putExtra("dosage", String.valueOf(m.getDose().getDose()));
                    @SuppressLint("InlinedApi") PendingIntent pendingIntent = PendingIntent.getBroadcast(context, count++, intent, PendingIntent.FLAG_MUTABLE);
                    AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, timeinmilliseconds, pendingIntent);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence seqNme = "ReminderForMedicine";
            String des = "Channel for Medicine Reminder";
            Log.e("in", "in set notify");
            int imp = NotificationManagerCompat.IMPORTANCE_HIGH;
            @SuppressLint("WrongConstant") NotificationChannel notificationChannel = new NotificationChannel("pleasetakemedicine", seqNme, imp);
            notificationChannel.setDescription(des);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    public static ArrayList<String> convertAllDays(ArrayList<String> days_of_week) {
        ArrayList<String> answer = new ArrayList<>();
        for (String s : days_of_week) {
            switch (s) {
                case "1":
                    answer.add("Monday");
                    break;
                case "2":
                    answer.add("Tuesday");
                    break;
                case "3":
                    answer.add("Wednesday");
                    break;
                case "4":
                    answer.add("Thursday");
                    break;
                case "5":
                    answer.add("Friday");
                    break;
                case "6":
                    answer.add("Saturday");
                    break;
                case "0":
                    answer.add("Sunday");
                    break;
            }
        }
        return answer;
    }
}
