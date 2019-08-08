package com.example.clockwidget.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clockwidget.R;
import com.example.clockwidget.utils.SaveUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 吴科烽
 * @date 2019-08-05
 **/
public class ZonePickerActivity extends Activity implements OnItemClickListener, TextWatcher {
    /**
     * 显示时区的列表
     */
    private ListView timeZoneListView;
    /**
     * 存放时区信息的HashMap
     */
    private HashMap<String, String> timeZoneHashMap = new HashMap<>();
    /**
     * 存放时区名，用于列表显示
     */
    private ArrayList<String> timeZoneArrayList = new ArrayList<>();
    /**
     * 搜索输入框
     */
    private EditText searchEditText;
    /**
     * 列表显示的适配器
     */
    private TimeZoneAdapter timeZoneAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zonepicker_activity);
        timeZoneListView = findViewById(R.id.zone_list);
        searchEditText = findViewById(R.id.search_zone);
        searchEditText.addTextChangedListener(this);
        getData();
        //列表显示
        timeZoneAdapter = new TimeZoneAdapter();
        timeZoneListView.setAdapter(timeZoneAdapter);
        //列表单击事件监听
        timeZoneListView.setOnItemClickListener(this);
    }

    /**
     * 从xml文件获取信息
     */
    public void getData() {
        try {
            timeZoneHashMap.clear();
            timeZoneArrayList.clear();
            Resources mResources = getResources();
            XmlResourceParser xrp = mResources.getXml(R.xml.timezones);
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String name = xrp.getName();
                    if ("timezone".equals(name)) {
                        //关键词搜索
                        if (xrp.getAttributeValue(1).contains(searchEditText.getText().toString()) ) {
                            //0标识id，1标识名称
                            timeZoneHashMap.put(xrp.getAttributeValue(1),
                                    xrp.getAttributeValue(0));
                            timeZoneArrayList.add(xrp.getAttributeValue(1));
                        }
                    }
                }
                xrp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class TimeZoneAdapter extends BaseAdapter {
        Holder holder;

        @Override
        public int getCount() {
            return timeZoneArrayList.size();
        }

        @Override
        public Object getItem(int arg0) {
            return null;
        }

        @Override
        public long getItemId(int arg0) {
            return 0;
        }

        @Override
        public View getView(int pos, View view, ViewGroup arg2) {
            holder = new Holder();
            if (view == null) {
                view = LayoutInflater.from(ZonePickerActivity.this).inflate(R.layout.timezone_list_item, null);
                holder.view = view.findViewById(R.id.item_zone);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            holder.view.setText(timeZoneArrayList.get(pos));
            return view;
        }

        class Holder {
            private TextView view;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
        TextView textView = view.findViewById(R.id.item_zone);
        SaveUtils.saveTimeZone(this, textView.getText().toString());
        SaveUtils.saveZoneId(this, timeZoneHashMap.get(textView.getText().toString()));
        Intent mIntent = new Intent();
        mIntent.putExtra("timezone", textView.getText().toString());
        setResult(2, mIntent);
        finish();
    }

    @Override
    public void afterTextChanged(Editable arg0) {}

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        getData();
        timeZoneAdapter.notifyDataSetChanged();
    }


}