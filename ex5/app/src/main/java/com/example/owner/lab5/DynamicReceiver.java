package com.example.owner.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

public class DynamicReceiver extends BroadcastReceiver {
    public static final String DYNAMICACTION = "com.example.owner.lab5.dynamicreceiver";
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

            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_demo);
            rv.setImageViewResource(R.id.appwidget_image, bundle.getInt("image"));
            rv.setTextViewText(R.id.appwidget_text, bundle.getString("content"));
            ComponentName me = new ComponentName(context, WidgetDemo.class);
            AppWidgetManager.getInstance(context).updateAppWidget(me, rv);
        }
    }
}
