package com.example.clockwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class ClockProvider extends AppWidgetProvider {
    //刷新执行
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
        super.onUpdate(context,appWidgetManager,appWidgetIds);
    }

    //第一个添加到屏幕上
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
        context.startService(new Intent(context,ClockService.class));
    }

    //最后一个widget从屏幕移除
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
        context.stopService(new Intent(context,ClockService.class));
    }

    //从屏幕移除

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
}
