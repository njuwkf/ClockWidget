package com.example.clockwidget.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateUtils {
    /**
     * 日期选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     */
    public static void showDatePickerDialog(Activity activity, int themeResId, final TextView tv){
        Calendar mCalendar = Calendar.getInstance();
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity, themeResId,
                // 绑定监听器
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        tv.setText(year + "." + (monthOfYear + 1) + "." + dayOfMonth );
                    }
                }
                // 设置初始日期
                ,mCalendar.get(Calendar.YEAR)
                , mCalendar.get(Calendar.MONTH)
                , mCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    /**
     * 时间选择
     *
     * @param activity
     * @param themeResId
     * @param tv
     */
    public static void showTimePickerDialog(Activity activity, int themeResId, final TextView tv) {
        Calendar mCalendar = Calendar.getInstance();
        // 创建一个TimePickerDialog实例，并把它显示出来
        // 解释一哈，Activity是context的子类
        new TimePickerDialog(activity, themeResId,
                // 绑定监听器
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tv.setText(hourOfDay + ":" + minute );
                    }
                }
                // 设置初始时间
                , mCalendar.get(Calendar.HOUR_OF_DAY)
                , mCalendar.get(Calendar.MINUTE)
                // true表示采用24小时制
                , true).show();
    }
}
