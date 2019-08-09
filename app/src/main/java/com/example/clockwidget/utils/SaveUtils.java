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
        return mSharedPreferences.getString("fontColor",STR_DEFAULT_COLOR);
    }
    public static void saveFontColor(Context context, String fontcolor) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("fontColor", fontcolor);
        edit.apply();
    }

    /**
     * 保存字体大小
     * @param context context
     * @return 字体大小
     */
    public static String getFontSize(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        return mSharedPreferences.getString("fontSize",STR_DEFAULT_FONT_SIZE);
    }

    public static void saveFontSize(Context context, String fontSize) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("fontSize", fontSize);
        edit.apply();
    }

    /**
     * 提取时间格式
     * @param context context
     * @return 时间格式
     */
    public static String getTimeFormat(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        return mSharedPreferences.getString("timeFormat",STR_DEFAULT_TIME_FORMAT);
    }
    public static void saveTimeFormat(Context context, String timeFormat) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("timeFormat", timeFormat);
        edit.apply();
    }

    /**
     * 提取时钟样式
     * @param context context
     * @return 时钟样式
     */
    public static String getClockStyle(Context context){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        return mSharedPreferences.getString("clockStyle",STR_DEFAULT_CLOCK_STYLE);
    }
    public static void saveClockStyle(Context context, String clockStyle) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("clockStyle", clockStyle);
        edit.apply();
    }

    /**
     * 提取时区
     * @param context context
     * @return 时区
     */
    public static String getTimeZone(Context context){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        return mSharedPreferences.getString("timeZone",STR_DEFAULT_CLOCK_TIME_ZONE);
    }
    public static void saveTimeZone(Context context, String timeZone) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("timeZone", timeZone);
        edit.apply();
    }
    /**
     * 提取时区id
     * @param context context
     * @return 时区id
     */
    public static String getZoneId(Context context){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        return mSharedPreferences.getString("zoneId",STR_DEFAULT_ZONE_ID);
    }
    public static void saveZoneId(Context context, String zoneId) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("zoneId", zoneId);
        edit.apply();
    }

    /**
     * 提取节日提醒状态
     * @param context context
     * @return 节日提醒状态
     */
    public static boolean getFestivalState(Context context){
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        return mSharedPreferences.getBoolean("festivalState",BOOL_DEFAULT_FESTIVAL_STATE);
    }
    public static void saveFestivalState(Context context, boolean festivalState) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putBoolean("festivalState", festivalState);
        edit.apply();
    }
}