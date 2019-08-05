package com.example.clockwidget.Utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @auther 吴科烽
 * @date 2019-07-31
 * @describle TODO
 **/

public class SaveUtils {
    private static final String TAG = "SaveUtils";

    //提取字体颜色
    public static String getFontColor(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String fontcolor=mSharedPreferences.getString("fontcolor","黑");
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
        String fontsize=mSharedPreferences.getString("fontsize","40sp");
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
        String timeformat=mSharedPreferences.getString("timeformat","24小时制");
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
        String clockstyle=mSharedPreferences.getString("clockstyle","数字时钟");
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
        String timezone=mSharedPreferences.getString("timezone","北京/中国");
        return timezone;
    }

    //保存时区
    public static void saveZoneId(Context context, String timezone) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("zoneid", timezone);
        edit.commit();
    }

    //提取时区
    public static String getZoneId(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String zoneid=mSharedPreferences.getString("zoneid","Asia/Shanghai");
        return zoneid;
    }
}
