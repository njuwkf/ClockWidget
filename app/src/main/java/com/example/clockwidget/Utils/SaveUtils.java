package com.example.clockwidget.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.clockwidget.ConstUtils.DefaultSettingsConstUtils.default_festival_state;
import static com.example.clockwidget.ConstUtils.DefaultSettingsConstUtils.str_default_clockstyle;
import static com.example.clockwidget.ConstUtils.DefaultSettingsConstUtils.str_default_color;
import static com.example.clockwidget.ConstUtils.DefaultSettingsConstUtils.str_default_fontsize;
import static com.example.clockwidget.ConstUtils.DefaultSettingsConstUtils.str_default_timeformat;
import static com.example.clockwidget.ConstUtils.DefaultSettingsConstUtils.str_default_timezone;
import static com.example.clockwidget.ConstUtils.DefaultSettingsConstUtils.str_default_zoneid;

/**
 * @auther 吴科烽
 * @date 2019-07-31
 * @describle 保存信息
 **/

public class SaveUtils {
    private static final String TAG = "SaveUtils";

    //提取字体颜色
    public static String getFontColor(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String fontcolor=mSharedPreferences.getString("fontcolor",str_default_color);
        return fontcolor;
    }
    //保存字体颜色
    public static void saveFontColor(Context context, String fontcolor) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("fontcolor", fontcolor);
        edit.commit();
    }

    //保存字体大小
    public static void saveFontSize(Context context, String fontsize) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("fontsize", fontsize);
        edit.commit();
    }

    //提取字体大小
    public static String getFontSize(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String fontsize=mSharedPreferences.getString("fontsize",str_default_fontsize);
        return fontsize;
    }

    //保存日期格式
    public static void saveTimeFormat(Context context, String timeformat) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("timeformat", timeformat);
        edit.commit();
    }

    //提取日期格式
    public static String getTimeFormat(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String timeformat=mSharedPreferences.getString("timeformat",str_default_timeformat);
        return timeformat;
    }

    //保存时钟样式
    public static void saveClockStyle(Context context, String clockstyle) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("clockstyle", clockstyle);
        edit.commit();
    }

    //提取时钟样式
    public static String getClockStyle(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String clockstyle=mSharedPreferences.getString("clockstyle",str_default_clockstyle);
        return clockstyle;
    }

    //保存时区
    public static void saveTimeZone(Context context, String timezone) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("timezone", timezone);
        edit.commit();
    }

    //提取时区
    public static String getTimeZone(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String timezone=mSharedPreferences.getString("timezone",str_default_timezone);
        return timezone;
    }

    //保存时区id
    public static void saveZoneId(Context context, String timezone) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("zoneid", timezone);
        edit.commit();
    }

    //提取时区id
    public static String getZoneId(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String zoneid=mSharedPreferences.getString("zoneid",str_default_zoneid);
        return zoneid;
    }

    //保存节日提醒状态
    public static void saveFestivalState(Context context, boolean festivalstate) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean("festivalstate", festivalstate);
        edit.commit();
    }

    //提取节日提醒状态
    public static boolean getFestivalState(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        boolean festivalstate=mSharedPreferences.getBoolean("festivalstate",default_festival_state);
        return festivalstate;
    }
}
