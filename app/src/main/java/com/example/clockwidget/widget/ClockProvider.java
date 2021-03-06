package com.example.clockwidget.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;
import com.example.clockwidget.activity.DateSettingsActivity;
import com.example.clockwidget.R;

/**
 * @author 吴科烽
 * @date 2019-07-30
 **/

public class ClockProvider extends AppWidgetProvider {
    private static final String TAG = "ClockProvider";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {
        Log.d(TAG,"ClockProvider_onUpdate");
        for(int i=0;i<appWidgetIds.length;i++){
            Intent intent = new Intent(context, DateSettingsActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            RemoteViews remoteViews =new RemoteViews(context.getPackageName(), R.layout.clockwidge_activity);
            remoteViews.setOnClickPendingIntent(R.id.ll_clockwidge,pendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        // 启动Service
        Log.d(TAG,"ClockProvider_onEnabled");
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Log.d(TAG,"ClockProvider_onEnabled Android 8");
            context.startForegroundService(new Intent(context,ClockService.class));
        }else{
            Log.d(TAG,"ClockProvider_onEnabled Android 7");
            context.startService(new Intent(context, ClockService.class));
        }
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        //停止Service
        Log.d(TAG,"ClockProvider_onDisabled");
        context.stopService(new Intent(context, ClockService.class));
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.d(TAG,"ClockProvider_onDeleted");
        super.onDeleted(context, appWidgetIds);
    }
}
