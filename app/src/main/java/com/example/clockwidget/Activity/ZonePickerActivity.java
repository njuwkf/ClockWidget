package com.example.clockwidget.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clockwidget.R;
import com.example.clockwidget.Utils.SaveUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimeZone;

/**
 * @auther 吴科烽
 * @date 2019-08-05
 * @describle 时区选择界面
 **/
public class ZonePickerActivity extends Activity implements OnItemClickListener, TextWatcher {
    //显示时区的列表
    private ListView listView;
    //存放时区信息的HashMap
    private HashMap<String, String> map = new HashMap<String, String>();
    //存放时区名，用于列表显示
    private ArrayList<String> list = new ArrayList<String>();
    //搜索输入框
    private EditText editText;
    //列表显示的适配器
    private myadapter name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zonepicker_activity);
        listView = findViewById(R.id.zone_list);
        editText = findViewById(R.id.search_zone);
        editText.addTextChangedListener(this);
        getdata();
        //列表显示
        name = new myadapter();
        listView.setAdapter(name);
        //列表单击事件监听
        listView.setOnItemClickListener(this);
    }


    /**
     * 根据时区id值获取该时区当地时间
     * @param id 时区id值
     * @return 该时区时间
     */
    public String getTime(String id) {
        TimeZone tz = TimeZone.getTimeZone(id);
        Time time = new Time(tz.getID());
        time.setToNow();
        int year = time.year;
        int month = time.month;
        int day = time.monthDay;
        int minute = time.minute;
        int hour = time.hour;
        int sec = time.second;
        return  "当前时间为：" + year +
                "年 " + (month+1) +
                "月 " + day +
                "日 " + hour +
                "时 " + minute +
                "分 " + sec +
                "秒";
    }

    /**
     * 从xml文件获取信息
     */
    public void getdata() {
        try {
            map.clear();
            list.clear();
            Resources mResources = getResources();
            XmlResourceParser xrp = mResources.getXml(R.xml.timezones);
            while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) {
                if (xrp.getEventType() == XmlResourceParser.START_TAG) {
                    String name = xrp.getName();
                    if (name.equals("timezone")) {
                        //关键词搜索
                        if(xrp.getAttributeValue(1).indexOf(editText.getText().toString()) != -1) {
                            //0标识id，1标识名称
                            map.put(xrp.getAttributeValue(1),
                                    xrp.getAttributeValue(0));
                            list.add(xrp.getAttributeValue(1));
                        }
                    }
                }
                xrp.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class myadapter extends BaseAdapter{
        Holder holder;
        @Override
        public int getCount() {
            return list.size();
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
            if(view == null) {
                view = LayoutInflater.from(ZonePickerActivity.this).inflate(R.layout.timezone_list_item, null);
                holder.view = view.findViewById(R.id.item_zone);
                view.setTag(holder);
            } else {
                holder = (Holder) view.getTag();
            }
            holder.view.setText(list.get(pos));
            return view;
        }
        class Holder{
            public TextView view;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int arg2, long arg3) {
        TextView textView = view.findViewById(R.id.item_zone);
        SaveUtils.saveTimeZone(this,textView.getText().toString());
        SaveUtils.saveZoneId(this,map.get(textView.getText().toString()));
        Intent mIntent = new Intent();
        mIntent.putExtra("timezone",textView.getText().toString());
        setResult(2,mIntent);
        finish();
    }

    @Override
    public void afterTextChanged(Editable arg0) {
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        getdata();
        name.notifyDataSetChanged();
    }


}