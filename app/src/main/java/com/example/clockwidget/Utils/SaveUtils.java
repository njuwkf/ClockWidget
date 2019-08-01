package com.example.clockwidget.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @auther 吴科烽
 * @date 2019-07-31
 * @describle TODO
 **/

public class SaveUtils {
    //保存字体颜色
    public static boolean saveFontColor(Context context, String fontcolor) {
        SharedPreferences sp = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("fontcolor", fontcolor);
        edit.commit();
        return true;
    }

    //提取字体颜色
    public static String getFontColor(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String fontcolor=mSharedPreferences.getString("fontcolor",null);
        return fontcolor;
    }

    //保存字体大小
    public static boolean saveFontSize(Context context, String fontsize) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("fontsize", fontsize);
        edit.commit();
        return true;
    }

    //提取字体大小
    public static String getFontSize(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String fontsize=mSharedPreferences.getString("fontsize",null);
        return fontsize;
    }

    //保存日期格式
    public static boolean saveTimeFormat(Context context, String timeformat) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("timeformat", timeformat);
        edit.commit();
        return true;
    }

    //提取日期格式
    public static String getTimeFormat(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String timeformat=mSharedPreferences.getString("timeformat",null);
        return timeformat;
    }

    //保存日期格式
    public static boolean saveClockStyle(Context context, String clockstyle) {
        SharedPreferences mSharedPreferences = context.getSharedPreferences("data", Context.MODE_MULTI_PROCESS);
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        edit.putString("clockstyle", clockstyle);
        edit.commit();
        return true;
    }

    //提取日期格式
    public static String getClockStyle(Context context){
        SharedPreferences mSharedPreferences=context.getSharedPreferences("data",Context.MODE_MULTI_PROCESS);
        String clockstyle=mSharedPreferences.getString("clockstyle",null);
        return clockstyle;
    }

}
