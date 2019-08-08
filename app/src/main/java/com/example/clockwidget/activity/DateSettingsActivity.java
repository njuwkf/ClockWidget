package com.example.clockwidget.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clockwidget.R;
import com.example.clockwidget.utils.SaveUtils;
import com.example.clockwidget.drawview.WheelView;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;

import static com.example.clockwidget.constutils.SettingsConstUtils.str_color_black;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_color_blue;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_color_green;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_color_red;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_color_white;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_color_yellow;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_digital_clock;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_twelvehour;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_twentyfourhour;
import static com.example.clockwidget.constutils.SettingsConstUtils.str_view_clock;

/**
 * @author 吴科烽
 * @date 2019-07-31
 **/

public class DateSettingsActivity extends Activity implements View.OnClickListener {

    private LinearLayout timeFormatLinearLayout;
    private LinearLayout fontColorLinearLayout;
    private LinearLayout fontSizeLinearLayout;
    private LinearLayout clockStyleLinearLayout;
    private LinearLayout timeZoneLinearLayout;

    private TextView timeFormatText;
    private TextView fontColorText;
    private TextView fontSizeText;
    private TextView clockStyleText;
    private TextView timeZoneText;
    private SwitchButton festivalReminderButton;

    private String selectText = "";

    private ArrayList<String> fontColorList = new ArrayList<>();
    private ArrayList<String> fontSizeList = new ArrayList<>();
    private final static int RESULTCODE = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datesettings_activity);
        initData();
        initView();
        initListeners();
    }

    /**
     * 初始化组件
     */
    private void initView() {
        timeFormatLinearLayout = findViewById(R.id.ll_timeformat);
        fontColorLinearLayout = findViewById(R.id.ll_fontcolor);
        fontSizeLinearLayout = findViewById(R.id.ll_fontsize);
        clockStyleLinearLayout = findViewById(R.id.ll_clockstyle);
        timeZoneLinearLayout = findViewById(R.id.ll_timezones);

        timeFormatText = findViewById(R.id.tv_selected_timeformat);
        fontColorText = findViewById(R.id.tv_selected_color);
        fontSizeText = findViewById(R.id.tv_selected_size);
        clockStyleText = findViewById(R.id.tv_selected_clockstyle);
        timeZoneText = findViewById(R.id.tv_selected_timezone);
        festivalReminderButton = findViewById(R.id.festival_button);

        fontColorText.setText(SaveUtils.getFontColor(DateSettingsActivity.this));
        fontSizeText.setText(SaveUtils.getFontSize(DateSettingsActivity.this));
        timeFormatText.setText(SaveUtils.getTimeFormat(DateSettingsActivity.this));
        clockStyleText.setText(SaveUtils.getClockStyle(DateSettingsActivity.this));
        timeZoneText.setText(SaveUtils.getTimeZone(DateSettingsActivity.this));
        festivalReminderButton.setChecked(SaveUtils.getFestivalState(DateSettingsActivity.this));
        festivalReminderButton.isChecked();
        festivalReminderButton.toggle();
        festivalReminderButton.toggle(true);
        festivalReminderButton.setShadowEffect(false);
        festivalReminderButton.setEnabled(true);
        festivalReminderButton.setEnableEffect(true);
    }

    /**
     * 初始化列表数据
     */
    private void initData() {
        // 填充列表
        fontColorList.clear();
        fontColorList.add(str_color_black);
        fontColorList.add(str_color_white);
        fontColorList.add(str_color_red);
        fontColorList.add(str_color_yellow);
        fontColorList.add(str_color_green);
        fontColorList.add(str_color_blue);
        fontColorList.clear();
        for (int i = 25; i <= 45; i++) {
            fontSizeList.add(i + "sp");
        }
    }

    /**
     * 初始化监听器
     */
    private void initListeners() {
        clockStyleLinearLayout.setOnClickListener(this);
        timeZoneLinearLayout.setOnClickListener(this);
        festivalReminderButton.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SaveUtils.saveFestivalState(DateSettingsActivity.this,isChecked);
                if(isChecked){
                    Toast.makeText(DateSettingsActivity.this, "节日提醒开启", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DateSettingsActivity.this, "节日提醒关闭", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (str_digital_clock.equals(SaveUtils.getClockStyle(this))) {
            timeFormatLinearLayout.setOnClickListener(this);
            fontColorLinearLayout.setOnClickListener(this);
            fontSizeLinearLayout.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_timeformat:
                timeFormatChoice();
                break;
            case R.id.ll_fontcolor:
                showDialog(fontColorText, fontColorList, 3);
                break;
            case R.id.ll_fontsize:
                showDialog(fontSizeText, fontSizeList, 3);
                break;
            case R.id.ll_clockstyle:
                clockStyleChoice();
                break;
            case R.id.ll_timezones:
                showZoneActivity();
                break;
            default:
                break;
        }
    }


    /**
     * 滚动选择器接口
     */
    private void showDialog(TextView textView, ArrayList<String> list, int selected) {
        showChoiceDialog(list, textView, selected,
                new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        selectText = item;
                    }
                });
    }


    /**
     * 滚动选择器自定义功能
     */
    private void showChoiceDialog(ArrayList<String> dataList, final TextView textView, int selected,
                                  WheelView.OnWheelViewListener listener) {
        selectText = "";
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheelview_dialog, null);
        final WheelView wheelView = outerView.findViewById(R.id.wheel_view);
        // 对话框中当前项上面和下面的项数
        wheelView.setOffset(2);
        // 设置数据源
        wheelView.setItems(dataList);
        // 默认选中项
        wheelView.setSeletion(selected);
        wheelView.setOnWheelViewListener(listener);
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(outerView)
                .setPositiveButton("确认",
                        (dialogInterface, i) -> {
                            textView.setText(selectText);
                            switch (textView.getId()) {
                                case R.id.tv_selected_color:
                                    SaveUtils.saveFontColor(DateSettingsActivity.this, selectText);
                                    break;
                                case R.id.tv_selected_size:
                                    SaveUtils.saveFontSize(DateSettingsActivity.this, selectText);
                                    break;
                                default:
                                    break;
                            }

                        })
                .setNegativeButton("取消", null).create();
        alertDialog.show();
        int green = this.getResources().getColor(R.color.colorPrimary);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(green);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(green);
    }

    /**
     * 日期格式改变
     */
    private void timeFormatChoice() {
        final String []items = {str_twelvehour, str_twentyfourhour};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 0);
        builder.setTitle("时间格式选择");
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaveUtils.saveTimeFormat(DateSettingsActivity.this, items[which]);
                        fontSizeText.setText(items[which]);
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    /**
     * 时钟样式改变
     */
    private void clockStyleChoice() {
        final String []items = {str_digital_clock, str_view_clock};
        AlertDialog.Builder builder = new AlertDialog.Builder(this, 0);
        builder.setTitle("时钟样式选择");
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaveUtils.saveClockStyle(DateSettingsActivity.this, items[which]);
                        clockStyleText.setText(items[which]);
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


    /**
     * 跳转到时区选择界面
     */
    private void showZoneActivity() {
        Intent mIntent = new Intent(DateSettingsActivity.this, ZonePickerActivity.class);
        startActivityForResult(mIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULTCODE) {
            if (requestCode == 1) {
                String zone = data.getStringExtra("timezone");
                timeZoneText.setText(zone);
            }
        }
    }
}