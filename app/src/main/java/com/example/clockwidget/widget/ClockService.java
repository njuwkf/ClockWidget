package com.example.clockwidget.widget;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.text.format.Time;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RemoteViews;

import androidx.annotation.RequiresApi;

import com.example.clockwidget.drawview.ViewClock;
import com.example.clockwidget.R;
import com.example.clockwidget.utils.DateToFestivalsUtil;
import com.example.clockwidget.utils.SaveUtils;
import java.util.TimeZone;

import static com.example.clockwidget.constutils.SettingsConstUtils.STR_AM;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_COLOR_BLACK;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_COLOR_BLUE;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_COLOR_GREEN;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_COLOR_RED;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_COLOR_WHITE;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_COLOR_YELLOW;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_DIGITAL_CLOCK;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_PM;
import static com.example.clockwidget.constutils.SettingsConstUtils.STR_TWELVE_HOUR;


/**
 * @author 吴科烽
 * @date 2019-07-30
 **/

public class ClockService extends Service {
    private Handler mHandler = new Handler();
    private static final String TAG = "ClockService";
    private static final String TAG_LIFE_CYCLE = "ClockService_LifeCycle";
    private String strZoneId = "";
    private TimeZone timeZone;
    private String strTime = "";
    private String strDate = "";
    private static final String CHANNEL_IN_STRING = "clockServiceChannel";
    private Bitmap mbitmap = Bitmap.createBitmap(180,180, Bitmap.Config.ARGB_8888);
    private Canvas mCanvas = new Canvas(mbitmap);
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }


    /**
     *
     */
    @Override
    public void onCreate() {
        Log.d(TAG_LIFE_CYCLE, "ClockService_onCreate");
        super.onCreate();
        setService();
        mHandler.postAtTime(mTicker,SystemClock.uptimeMillis()+(1000-SystemClock.uptimeMillis()%1000));
    }


    /**
     * 更新界面
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void updateView(){
        RemoteViews rViews = new RemoteViews(getPackageName(), R.layout.clockwidge_activity);
        strZoneId = SaveUtils.getZoneId(this);
        timeZone = TimeZone.getTimeZone(strZoneId);
        Time time = new Time(timeZone.getID());

        if(STR_DIGITAL_CLOCK.equals(SaveUtils.getClockStyle(this))) {
            rViews.setViewVisibility(R.id.view_clock,View.GONE);
            rViews.setViewVisibility(R.id.date_time,View.VISIBLE);
            rViews.setViewVisibility(R.id.clock_time,View.VISIBLE);
            strDate = getDateString(time) + " " + getDateInWeek(time)+ " " + setTimeFormat(time,rViews);
            setFestival(time,rViews);
            setText(rViews);
        }else{
            rViews.setViewVisibility(R.id.clock_time,View.GONE);
            rViews.setViewVisibility(R.id.date_time,View.GONE);
            rViews.setViewVisibility(R.id.view_clock,View.VISIBLE);

            ViewClock.drawBitmap(mbitmap,mCanvas,time,SaveUtils.getFestivalState(this));
            rViews.setImageViewBitmap(R.id.view_clock,mbitmap);
        }

        //刷新
        AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
        ComponentName cName = new ComponentName(getApplicationContext(), ClockProvider.class);
        manager.updateAppWidget(cName,rViews);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG_LIFE_CYCLE,"ClockService_onDestroy");
        super.onDestroy();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            stopForeground(true);
        }
        mHandler.removeCallbacks(mTicker);
    }

    /**
     * 获得周几(基姆拉尔森公式)
     * @param time 当地时间
     * @return 周几
     */
    private String getDateInWeek(Time time) {
        time.setToNow();
        int year = time.year;
        int month = time.month + 1;
        int day = time.monthDay;
        String[] mweekdays = { "周日", "周一", "周二", "周三", "周四", "周五", "周六" };
        int weekday = (1+day+2*month+3*(month+1)/5+year+year/4-year/100+year/400)%7;
        return mweekdays[weekday];
    }

    /**
     * 获得mm月dd日类型的日期
     * @param time 当地时间
     * @return mm月dd日类型的日期
     */
    private String getDateString(Time time){
        time.setToNow();
        int month = time.month + 1;
        int day = time.monthDay;
        return  month + "月" + day + "日" ;
    }

    /**
     * 时间格式设置（24小时制or12小时制）
     * @param time 当地时间
     * @param rViews clockwidge_activity的View
     * @return AM or PM
     */
    private String setTimeFormat(Time time,RemoteViews rViews){
        time.setToNow();
        int hour = time.hour;
        int minute = time.minute;
        int sec = time.second;
        String strFormat = "";
        if(STR_TWELVE_HOUR.equals(SaveUtils.getTimeFormat(this))){
            if(hour<13 && hour >0){
                //上午
                strFormat = STR_AM;
                strTime = String.format("%02d:%02d:%02d",hour,minute,sec);
            }else if(hour == 0){
                strFormat = STR_PM;
                strTime = String.format("12:%02d:%02d",minute,sec);
            }else{
                //下午
                strFormat = STR_PM;
                strTime = String.format("%02d:%02d:%02d",hour-12,minute,sec);
            }
        }else{
            strTime = String.format("%02d:%02d:%02d",hour,minute,sec);
        }
        rViews.setTextViewText(R.id.clock_time,strTime);
        return strFormat;
    }

    /**
     * 时钟字体颜色、大小设置（从sharepreferences中读取数据）
     * @param rViews clockwidge_activity的View
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setText(RemoteViews rViews){
        Log.d(TAG,"ClockService_fontcolor:"+SaveUtils.getFontColor(this));
        switch (SaveUtils.getFontColor(this)) {
            case STR_COLOR_BLACK:
                rViews.setTextColor(R.id.clock_time, Color.BLACK);
                break;
            case STR_COLOR_WHITE:
                rViews.setTextColor(R.id.clock_time, Color.WHITE);
                break;
            case STR_COLOR_RED:
                rViews.setTextColor(R.id.clock_time, Color.RED);
                break;
            case STR_COLOR_YELLOW:
                rViews.setTextColor(R.id.clock_time, Color.YELLOW);
                break;
            case STR_COLOR_GREEN:
                rViews.setTextColor(R.id.clock_time, Color.GREEN);
                break;
            case STR_COLOR_BLUE:
                rViews.setTextColor(R.id.clock_time, Color.BLUE);
                break;
            default:
                break;
        }

        //时钟字体大小变化（从sharepreferences中读取数据）
        Log.d(TAG,"fontSize:"+SaveUtils.getFontSize(this));
        String strFontSize = SaveUtils.getFontSize(this).substring(0, 2);
        int fontSize = Integer.parseInt(strFontSize);
        rViews.setTextViewTextSize(R.id.clock_time, TypedValue.COMPLEX_UNIT_SP, fontSize);
    }

    /**
     * 节日提醒设置
     * @param time 当地时间
     * @param rViews clockwidge_activity的View
     */
    private void setFestival(Time time,RemoteViews rViews){
        time.setToNow();
        int year = time.year;
        int month = time.month + 1;
        int day = time.monthDay;
        String strFestDate = year + "-" + month + "-" +day;
        if(SaveUtils.getFestivalState(this)){
            strDate = strDate + " " +DateToFestivalsUtil.dateToYearMothDay(strFestDate);
        }
        rViews.setTextViewText(R.id.date_time,strDate);
    }

    private final Runnable mTicker = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void run() {
            updateView();
            Long now = SystemClock.uptimeMillis();
            Long next =  now + (1000 - now % 1000);
            mHandler.postAtTime(mTicker, next);
            Log.d(TAG,now + " " + next);
        }
    };


    /**
     * Service启用设置
     */
    private void setService(){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = null;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            mChannel = new NotificationChannel(CHANNEL_IN_STRING,getString(R.string.app_name),NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChannel);
            Notification mNotification = new Notification.Builder(getApplicationContext(),CHANNEL_IN_STRING).build();
            startForeground(1,mNotification);
        }
    }
}
