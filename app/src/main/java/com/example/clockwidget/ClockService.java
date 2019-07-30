package com.example.clockwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ClockService extends Service {
    private Timer timer;
    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                updateView();
            }
        },0,1000);
    }

    private void updateView(){
        //时间
        String time = sdf.format(new Date());
        RemoteViews rViews = new RemoteViews(getPackageName(),R.layout.clockwidge_activity);
        //显示当前时间
        rViews.setTextViewText(R.id.id_time,time);

        //刷新
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cName = new ComponentName(getApplicationContext(),ClockProvider.class);
        manager.updateAppWidget(cName,rViews);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer = null;
    }
}
