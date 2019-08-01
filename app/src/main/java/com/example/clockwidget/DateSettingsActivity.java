package com.example.clockwidget;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clockwidget.Utils.DateUtils;
import com.example.clockwidget.Utils.SaveUtils;

import java.util.ArrayList;


public class DateSettingsActivity extends Activity implements View.OnClickListener {
    private LinearLayout mdate_linearLayout;
    private LinearLayout mtime_linearLayout;
    private LinearLayout mtimeformat_linearLayout;
    private LinearLayout mfontcolor_linearLayout;
    private LinearLayout mfontsize_linearLayout;
    private TextView mdate_text;
    private TextView mtime_text;
    private TextView mtimeformat_text;
    private TextView mfontcolor_text;
    private TextView mfontsize_text;

    private String selectText = "";
    private ArrayList<String> mfontcolorList = new ArrayList<>();
    private ArrayList<String> mfontsizeList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datesettings_activity);
        initView();
        initData();
        initListeners();
    }


    //初始化组件
    private void initView() {
        mdate_linearLayout = findViewById(R.id.ll_date);
        mtime_linearLayout = findViewById(R.id.ll_time);
        mtimeformat_linearLayout = findViewById(R.id.ll_timeformat);
        mfontcolor_linearLayout = findViewById(R.id.ll_fontcolor);
        mfontsize_linearLayout = findViewById(R.id.ll_fontsize);

        mdate_text = findViewById(R.id.tv_selected_date);
        mtime_text = findViewById(R.id.tv_selected_time);
        mtimeformat_text = findViewById(R.id.tv_selected_timeformat);
        mfontcolor_text = findViewById(R.id.tv_selected_color);
        mfontsize_text = findViewById(R.id.tv_selected_size);

        if("".equals(SaveUtils.getFontColor(DateSettingsActivity.this))){
            mfontcolor_text.setText("黑");
        }else{
            mfontcolor_text.setText(SaveUtils.getFontColor(DateSettingsActivity.this));
        }

        if("".equals(SaveUtils.getFontSize(DateSettingsActivity.this))){
            mfontsize_text.setText("40sp");
        }else{
            mfontsize_text.setText(SaveUtils.getFontSize(DateSettingsActivity.this));
        }

        if("".equals(SaveUtils.getTimeFormat(DateSettingsActivity.this))){
            mtimeformat_text.setText("24小时制");
        }else{
            mtimeformat_text.setText(SaveUtils.getTimeFormat(DateSettingsActivity.this));
        }
    }


    //初始化列表数据
    private void initData() {
        // 填充列表
        mfontcolorList.clear();
        mfontcolorList.add("黑");
        mfontcolorList.add("白");
        mfontcolorList.add("红");
        mfontcolorList.add("黄");
        mfontcolorList.add("绿");
        mfontcolorList.add("蓝");
        mfontsizeList.clear();
        for(int i=25;i<=45;i++) {
            mfontsizeList.add(i + "dp");
        }
    }

    //初始化监听器
    private void initListeners(){
        mdate_linearLayout.setOnClickListener(this);
        mtime_linearLayout.setOnClickListener(this);
        mtimeformat_linearLayout.setOnClickListener(this);
        mfontcolor_linearLayout.setOnClickListener(this);
        mfontsize_linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                DateUtils.showDatePickerDialog(DateSettingsActivity.this,2, mdate_text);
                break;
            case R.id.ll_time:
                DateUtils.showTimePickerDialog(DateSettingsActivity.this,2, mtime_text);
            case R.id.ll_timeformat:
                timeformatChoice();
                break;
            case R.id.ll_fontcolor:
                showDialog(mfontcolor_text,mfontcolorList,6);
                break;
            case R.id.ll_fontsize:
                showDialog(mfontsize_text,mfontsizeList,21);
                break;
            default:
                break;
        }
    }


    //选择器接口
    private void showDialog(TextView textView, ArrayList<String> list, int selected){
        showChoiceDialog(list, textView, selected,
                new WheelView.OnWheelViewListener() {
                    @Override
                    public void onSelected(int selectedIndex, String item) {
                        selectText = item;
                    }
                });
    }



    //选择器功能
    private void showChoiceDialog(ArrayList<String> dataList,final TextView textView,int selected,
                                  WheelView.OnWheelViewListener listener){
        selectText = "";
        View outerView = LayoutInflater.from(this).inflate(R.layout.wheelview_dialog,null);
        final WheelView wheelView = outerView.findViewById(R.id.wheel_view);
        wheelView.setOffset(2);// 对话框中当前项上面和下面的项数
        wheelView.setItems(dataList);// 设置数据源
        wheelView.setSeletion(selected);// 默认选中第三项
        wheelView.setOnWheelViewListener(listener);

        // 显示对话框，点击确认后将所选项的值显示到Button上
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(outerView)
                .setPositiveButton("确认",
                        (dialogInterface, i) -> {
                            textView.setText(selectText);
                            switch (textView.getId()){
                                case R.id.tv_selected_color:
                                    SaveUtils.saveFontColor(DateSettingsActivity.this,selectText);
                                    break;
                                case R.id.tv_selected_size:
                                    SaveUtils.saveFontSize(DateSettingsActivity.this,selectText);
                                    break;
                                default:
                                    break;
                            }

                        })
                .setNegativeButton("取消",null).create();
        alertDialog.show();
        int green = this.getResources().getColor(R.color.colorPrimary);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(green);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(green);
    }


    //日期格式改变
    private void timeformatChoice() {
        final String items[] = {"24小时制", "12小时制"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this,0);
        builder.setTitle("时间格式选择");
        builder.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SaveUtils.saveTimeFormat(DateSettingsActivity.this,items[which]);
                        mtimeformat_text.setText(items[which]);
                        dialog.dismiss();
                    }
                });
        builder.show();
    }


}