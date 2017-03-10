package com.example.owner.lab4;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

public class DynamicReceiver extends BroadcastReceiver {
    public static final String DYNAMICACTION = "com.example.owner.lab4.dynamicreceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DYNAMICACTION)) {
            Bundle bundle = intent.getExtras();
            Intent myIntent = new Intent(context, MainActivity.class);
            PendingIntent myPendingIntent = PendingIntent.getActivity(context, 0, myIntent, 0);
            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), (int)bundle.get("image"));
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification.Builder builder = new Notification.Builder(context);
            builder.setContentTitle((String)bundle.get("title"))
                    .setContentText((String)bundle.get("content"))
                    .setLargeIcon(bm)
                    .setSmallIcon((int)bundle.get("image"))
                    .setAutoCancel(true)
                    .setContentIntent(myPendingIntent);
            Notification notify = builder.build();
            manager.notify(0, notify);
        }
    }
}
