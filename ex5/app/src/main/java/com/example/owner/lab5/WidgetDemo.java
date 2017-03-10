package com.example.owner.lab5;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetDemo extends AppWidgetProvider {
    public static final String STATICACTION = "com.example.owner.lab5.staticreceiver";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Intent clickInt = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, clickInt, 0);
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_demo);
        rv.setOnClickPendingIntent(R.id.appwidget_image, pi);
        appWidgetManager.updateAppWidget(appWidgetIds, rv);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(STATICACTION)) {
            Bundle bundle = intent.getExtras();
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_demo);
            rv.setImageViewResource(R.id.appwidget_image, bundle.getInt("image"));
            rv.setTextViewText(R.id.appwidget_text, bundle.getString("content"));
            ComponentName me = new ComponentName(context, WidgetDemo.class);
            AppWidgetManager.getInstance(context).updateAppWidget(me, rv);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

