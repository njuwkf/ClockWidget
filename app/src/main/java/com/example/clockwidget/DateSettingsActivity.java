package com.example.clockwidget;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class DateSettingsActivity extends Activity implements View.OnClickListener {
    private LinearLayout mdate_linearLayout;
    private LinearLayout mtime_linearLayout;
    private TextView mdate_text;
    private TextView mtime_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datesettings_activity);
        mdate_linearLayout = findViewById(R.id.ll_date);
        mtime_linearLayout = findViewById(R.id.ll_time);
        mdate_text=findViewById(R.id.tv_selected_date);
        mtime_text=findViewById(R.id.tv_selected_time);
        mdate_linearLayout.setOnClickListener(this);
        mtime_linearLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_date:
                DateUtil.showDatePickerDialog(DateSettingsActivity.this,2, mdate_text);
                break;
            case R.id.ll_time:
                DateUtil.showTimePickerDialog(DateSettingsActivity.this,2, mtime_text);
                break;
            default:
                break;
        }
    }

}