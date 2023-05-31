package com.mc2022.template.BroadcastReceivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mc2022.template.Activities.MainActivity;
import com.mc2022.template.R;

public class NotifyBroadcast extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent redirectIntent = new Intent(context, MainActivity.class);
        PendingIntent redirectPendingIntent = PendingIntent.getActivity(context, 100, redirectIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "pleasetakemedicine")
                .setSmallIcon(R.drawable.ic_baseline_add_alert_24)
                .setContentTitle("Please take " + intent.getStringExtra("dosage") + " doses of " + intent.getStringExtra("medicine_name"))
                .setContentText("A gentle reminder")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(redirectPendingIntent);
//                .addAction(R.drawable.ic_baseline_check_24, "Taken",pendingIntent );
        Log.e("in", "in broadcast reciever");
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());
    }
}
