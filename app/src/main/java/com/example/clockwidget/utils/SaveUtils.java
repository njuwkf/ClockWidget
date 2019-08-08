package com.example.clockwidget.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static com.example.clockwidget.constutils.DefaultSettingsConstUtils.BOOL_DEFAULT_FESTIVAL_STATE;
import static com.example.clockwidget.constutils.DefaultSettingsConstUtils.STR_DEFAULT_CLOCK_STYLE;
import static com.example.clockwidget.constutils.DefaultSettingsConstUtils.STR_DEFAULT_COLOR;
import static com.example.clockwidget.constutils.DefaultSettingsConstUtils.STR_DEFAULT_FONT_SIZE;
import static com.example.clockwidget.constutils.DefaultSettingsConstUtils.STR_DEFAULT_TIME_FORMAT;
import static com.example.clockwidget.constutils.DefaultSettingsConstUtils.STR_DEFAULT_CLOCK_TIME_ZONE;
import static com.example.clockwidget.constutils.DefaultSettingsConstUtils.STR_DEFAULT_ZONE_ID;

/**
 * @author 吴科烽
 * @date 2019-07-31
 **/

public class SaveUtils {
    /**
     * 提取字体颜色
     * @param context context
     * @return 字体颜色
     */
    public static String getFontColor(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String fontcolor=mSharedPreferences.getString("fontcolor",STR_DEFAULT_COLOR);
        return fontcolor;
    }
    public static void saveFontColor(Context context, String fontcolor) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("fontcolor", fontcolor);
        edit.commit();
    }

    /**
     * 保存字体大小
     * @param context context
     * @return 字体大小
     */
    public static void saveFontSize(Context context, String fontsize) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("fontsize", fontsize);
        edit.commit();
    }
    public static String getFontSize(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String fontsize=mSharedPreferences.getString("fontsize",STR_DEFAULT_FONT_SIZE);
        return fontsize;
    }

    /**
     * 提取时间格式
     * @param context context
     * @return 时间格式
     */
    public static String getTimeFormat(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String timeformat=mSharedPreferences.getString("timeformat",STR_DEFAULT_TIME_FORMAT);
        return timeformat;
    }
    public static void saveTimeFormat(Context context, String timeformat) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("timeformat", timeformat);
        edit.commit();
    }

    /**
     * 提取时钟样式
     * @param context context
     * @return 时钟样式
     */
    public static String getClockStyle(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String clockstyle=mSharedPreferences.getString("clockstyle",STR_DEFAULT_CLOCK_STYLE);
        return clockstyle;
    }
    public static void saveClockStyle(Context context, String clockstyle) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("clockstyle", clockstyle);
        edit.commit();
    }

    /**
     * 提取时区
     * @param context context
     * @return 时区
     */
    public static String getTimeZone(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String timezone=mSharedPreferences.getString("timezone",STR_DEFAULT_CLOCK_TIME_ZONE);
        return timezone;
    }
    public static void saveTimeZone(Context context, String timezone) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("timezone", timezone);
        edit.commit();
    }
    /**
     * 提取时区id
     * @param context context
     * @return 时区id
     */
    public static String getZoneId(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String zoneid=mSharedPreferences.getString("zoneid",STR_DEFAULT_ZONE_ID);
        return zoneid;
    }
    public static void saveZoneId(Context context, String timezone) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("zoneid", timezone);
        edit.commit();
    }

    /**
     * 提取节日提醒状态
     * @param context context
     * @return 节日提醒状态
     */
    public static boolean getFestivalState(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        boolean festivalstate=mSharedPreferences.getBoolean("festivalstate",BOOL_DEFAULT_FESTIVAL_STATE);
        return festivalstate;
    }
    public static void saveFestivalState(Context context, boolean festivalstate) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean("festivalstate", festivalstate);
        edit.commit();
    }
}
