package com.example.clockwidget.ClockWidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
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

import com.example.clockwidget.DrawView.ViewClock;
import com.example.clockwidget.R;
import com.example.clockwidget.Utils.DateToFestivalsUtil;
import com.example.clockwidget.Utils.SaveUtils;
import java.util.TimeZone;

import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_am;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_color_black;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_color_blue;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_color_green;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_color_red;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_color_white;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_color_yellow;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_digital_clock;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_pm;
import static com.example.clockwidget.ConstUtils.SettingsConstUtils.str_twelvehour;


/**
 * @auther 吴科烽
 * @date 2019-07-30
 * @describle WidgetService
 **/

public class ClockService extends Service {
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final String TAG = "ClockService";
    private static final String TAG_LifeCycle = "ClockService_LifeCycle";
    private String str_zone = "";
    private TimeZone mtimeZone;
    private String str_time = "";
    private String str_date = "";

    private Bitmap mbitmap = Bitmap.createBitmap(180,180, Bitmap.Config.ARGB_8888);
    private Canvas mCanvas = new Canvas(mbitmap);
    @Override
    public IBinder onBind(Intent intent){
        return null;
    }

    @Override
    public void onCreate() {
        Log.d(TAG_LifeCycle, "ClockService_onCreate");
        super.onCreate();
        mHandler.postAtTime(mTicker,SystemClock.uptimeMillis()+(1000-SystemClock.uptimeMillis()%1000));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void updateView(){
        RemoteViews rViews = new RemoteViews(getPackageName(), R.layout.clockwidge_activity);
        str_zone = SaveUtils.getZoneId(this);
        mtimeZone = TimeZone.getTimeZone(str_zone);
        Time time = new Time(mtimeZone.getID());

        if(str_digital_clock.equals(SaveUtils.getClockStyle(this))) {
            rViews.setViewVisibility(R.id.view_clock,View.GONE);
            rViews.setViewVisibility(R.id.date_time,View.VISIBLE);
            rViews.setViewVisibility(R.id.clock_time,View.VISIBLE);
            str_date = getDateString(time) + " " + getDateInWeek(time)+ " " + setTimeFormat(time,rViews);
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
        Log.d(TAG_LifeCycle,"ClockService_onDestroy");
        super.onDestroy();
        mHandler.removeCallbacks(mTicker);
    }

    /**
     * 获得周几(基姆拉尔森公式)
     * @param
     * @return
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
     * @param
     * @return
     */
    private String getDateString(Time time){
        time.setToNow();
        int month = time.month + 1;
        int day = time.monthDay;
        return  month + "月" + day + "日" ;
    }

    //时间格式（24小时制or12小时制）
    private String setTimeFormat(Time time,RemoteViews rViews){
        time.setToNow();
        int hour = time.hour;
        int minute = time.minute;
        int sec = time.second;
        String str_format = "";
        if(str_twelvehour.equals(SaveUtils.getTimeFormat(this))){
            if(hour<13 && hour >0){
                //上午
                str_format = str_am;
                str_time = String.format("%02d:%02d:%02d",hour,minute,sec);
            }else if(hour == 0){
                str_format = str_pm;
                str_time = String.format("12:%02d:%02d",minute,sec);
            }else{
                //下午
                str_format = str_pm;
                str_time = String.format("%02d:%02d:%02d",hour-12,minute,sec);
            }
        }else{
            str_time = String.format("%02d:%02d:%02d",hour,minute,sec);
        }
        rViews.setTextViewText(R.id.clock_time,str_time);
        return str_format;
    }


    //时钟字体颜色大小设置（从sharepreferences中读取数据）
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setText(RemoteViews rViews){
        Log.d(TAG,"ClockService_fontcolor:"+SaveUtils.getFontColor(this));
        switch (SaveUtils.getFontColor(this)) {
            case str_color_black:
                rViews.setTextColor(R.id.clock_time, Color.BLACK);
                break;
            case str_color_white:
                rViews.setTextColor(R.id.clock_time, Color.WHITE);
                break;
            case str_color_red:
                rViews.setTextColor(R.id.clock_time, Color.RED);
                break;
            case str_color_yellow:
                rViews.setTextColor(R.id.clock_time, Color.YELLOW);
                break;
            case str_color_green:
                rViews.setTextColor(R.id.clock_time, Color.GREEN);
                break;
            case str_color_blue:
                rViews.setTextColor(R.id.clock_time, Color.BLUE);
                break;
            default:
                break;
        }

        //时钟字体大小变化（从sharepreferences中读取数据）
        Log.d(TAG,"ClockService_fontsize"+SaveUtils.getFontSize(this));
        String str_fontsize = SaveUtils.getFontSize(this).substring(0, 2);
        int font_size = Integer.parseInt(str_fontsize);
        rViews.setTextViewTextSize(R.id.clock_time, TypedValue.COMPLEX_UNIT_SP, font_size);
    }


    //节日提醒
    private void setFestival(Time time,RemoteViews rViews){
        time.setToNow();
        int year = time.year;
        int month = time.month + 1;
        int day = time.monthDay;
        String str_fest = year + "-" + month + "-" +day;
        if(SaveUtils.getFestivalState(this)){
            str_date = str_date + " " +DateToFestivalsUtil.DateToYearMothDay(str_fest);
        }else{
            str_date = str_date;
        }
        rViews.setTextViewText(R.id.date_time,str_date);
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


}
