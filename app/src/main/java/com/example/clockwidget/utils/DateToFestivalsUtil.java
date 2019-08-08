package com.example.clockwidget.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author 吴科烽
 * @date 2019-08-06
 **/
public class DateToFestivalsUtil {

    private int year;
    private int month;
    private int day;
    /**
     * 闰月
     */
    public int leapMonth = 0;
    final static String []CHINESE_NUMBER = {"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
    static SimpleDateFormat chineseDateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
    /**
     * 1900-2049年
     */
    final static long[] LUNARINFO = new long[]{
            0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,
        0x055d2,0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,
        0x095b0,0x14977,0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,
        0x09570,0x052f2,0x04970,0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,
        0x186e3,0x092e0,0x1c8d7,0x0c950,0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,
        0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,0x06ca0,0x0b550,0x15355,0x04da0,
        0x0a5d0,0x14573,0x052d0,0x0a9a8,0x0e950,0x06aa0,0x0aea6,0x0ab50,0x04b60,
        0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0,0x096d0,0x04dd5,
        0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b5a0,0x195a6,0x095b0,
        0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,
        0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x055c0,0x0ab60,0x096d5,
        0x092e0,0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,
        0x092d0,0x0cab5,0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,
        0x15176,0x052b0,0x0a930,0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,
        0x0a4e0,0x0d260,0x0ea65,0x0d530,0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,
        0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,0x0b5a0,0x056d0,0x055b2,0x049b0,
        0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0
    };
    /**
     * 农历部分假日
     */
    final static String[]LUNARHOLIDAY = new String[]{"0100 除夕","0101 春节","0115 元宵","0505 端午","0707 七夕","0715 中元","0815 中秋","0909 重阳","1208 腊八","1224 小年"};
    /**
     * 公历部分节假日
     */
    final static String[]SOLARHOLIDAY = new String[]{"0101 元旦","0214 情人节","0308 妇女节","0312 植树节","0401 愚人节","0501 劳动节","0601 儿童节","0701 建党节","0801 建军节","0809 最终展示","0910 教师节", "1001 国庆节","1111 双11","1225 圣诞节"};

    /**
     * 传回农历y年的总天数
     * @param y 年
     * @return 农历y年的总天数
     */
    private static int yearDays(int y){
        int i,sum=348;
        for(i=0x8000;i>0x8;i>>=1){
            if((LUNARINFO[y-1900]&i)!=0) {
                sum+=1;
            }
        }
        return(sum+leapDays(y));
    }

    /**
     * 传回农历 y年闰月的天数
     * @param y 年
     * @return 农历 y年闰月的天数
     */
    private static int leapDays(int y){
        if(leapMonth(y)!=0){
            if((LUNARINFO[y-1900]&0x10000)!=0) {
                return 30;
            } else {
                return 29;
            }
        }else {
            return 0;
        }
    }

    /**
     * 传回农历 y年闰哪个月 1-12 , 没闰传回 0
     * @param y 年
     * @return 农历y年闰哪个月
     */
    private static int leapMonth(int y){
        int result=(int)(LUNARINFO[y-1900]&0xf);
            return result;
    }

    /**
     * 传回农历 y年m月的总天数
     * @param y 年
     * @param m 月
     * @return 农历y年m月的总天数
     */
    private static int monthDays(int y,int m){
        if((LUNARINFO[y-1900]&(0x10000>>m))==0) {
            return 29;
        } else {
            return 30;
        }
    }

    /**
     * 根据日期返回节假日
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 节假日名称
     */
    public String getLunarDate(int year,int month,int day){
            String nowadays;
            Date baseDate=null;
            Date nowaday=null;
            try{
                baseDate=chineseDateFormat.parse("1900年1月31日");
            }catch(ParseException e){
                e.printStackTrace();
            }
            nowadays=year + "年" + month + "月" + day + "日";
            try{
                nowaday=chineseDateFormat.parse(nowadays);
            }catch(ParseException e){
                e.printStackTrace();
            }
            // 求出和1900年1月31日相差的天数
            int offset=(int)((nowaday.getTime()-baseDate.getTime())/86400000L);
            // 用offset减去每农历年的天数, 计算当天是农历第几天,i最终结果是农历的年份,offset是当年的第几天
            int iYear,daysOfYear=0;
            for(iYear=1900;iYear< 10000&&offset>0;iYear++){
                daysOfYear=yearDays(iYear);
                offset-=daysOfYear;
            }
            if(offset< 0){
                offset+=daysOfYear;
                iYear--;
            }
            // 农历年份
            year=iYear;
            leapMonth=leapMonth(iYear);
            boolean leap=false;
            // 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
            int iMonth,daysOfMonth=0;
            for(iMonth=1;iMonth< 13&&offset>0;iMonth++){
                // 闰月
                if(leapMonth>0&&iMonth==(leapMonth+1)&&!leap){
                    --iMonth;
                    leap=true;
                    daysOfMonth=leapDays(year);
                }else{
                    daysOfMonth=monthDays(year,iMonth);
                }
                offset-=daysOfMonth;
                 // 解除闰月
                if(leap&&iMonth==(leapMonth+1)){
                    leap=false;
                }
             }
            // offset为0时，并且刚才计算的月份是闰月，要校正
             if(offset==0&&leapMonth>0&&iMonth==leapMonth+1){
                if(leap){
                    leap=false;
                }else{
                    leap=true;
                    --iMonth;
                }
             }
             // offset小于0时，也要校正
            if(offset< 0){
                offset+=daysOfMonth;
                --iMonth;
            }
            month=iMonth;
            day=offset+1;
            // 返回公历节假日名称
            for(int i=0;i < SOLARHOLIDAY.length;i++){
                String solarHolidayId = SOLARHOLIDAY[i].split(" ")[0];
                String solarHolidayName = SOLARHOLIDAY[i].split(" ")[1];
                String strSolarMonth = month + "";
                String strSolarDay = day + "";
                String solarDayId = "";
                if(month < 10){
                    strSolarMonth = "0" + month;
                }
                if(day < 10){
                    strSolarDay = "0" + day;
                }
                solarDayId = strSolarMonth + strSolarDay;
                if(solarHolidayId.trim().equals(solarDayId.trim())){
                    return solarHolidayName;
                }
            }
            // 返回农历节假日名称
            for(int i=0;i < LUNARHOLIDAY.length;i++){
                String lunarHolidayId = LUNARHOLIDAY[i].split(" ")[0];
                String lunarHolidayName = LUNARHOLIDAY[i].split(" ")[1];
                String strLunarMonth = month + "";
                String strLunarDay = day + "";
                String lunarDayId = "";
                if(month < 10){
                    strLunarMonth = "0" + month;
                }
                if(day< 10){
                    strLunarDay = "0" + day;
                }
                lunarDayId = strLunarMonth + strLunarDay;
                // 除夕夜需要特殊处理
                if("12".equals(strLunarMonth)){
                    if((daysOfMonth == 29 && day == 29)||(daysOfMonth == 30 && day == 30)){
                        return lunarHolidayName;
                    }
                }
                if(lunarHolidayId.trim().equals(lunarDayId.trim())){
                    return lunarHolidayName;
                }
            }
            String tombSweeping=DateToFestivalsUtil.isTombSweeping(year,day);
            if(tombSweeping!=null){
                return tombSweeping;
            }
            String motherOrFatherDay=DateToFestivalsUtil.getMotherOrFatherDay(year,month,day);
            if(motherOrFatherDay!=null){
                return motherOrFatherDay;
            }
            return "";
        }

    /**
     * 判断是否是清明节
     * @param year 年
     * @param day 日
     * @return 清明节
     */
    private static String isTombSweeping(int year,int day){
        int tempYear = (year % 10) + (((year/10) % 10) * 10);
        int tombSweepingDay = (int)((tempYear * 0.2422 + 4.81) - (tempYear/4));
        if(tombSweepingDay == day){
            return"清明节";
        }
        return null;
    }

    /**
     * 判断是否是母亲节和父亲节
     * @param year 年
     * @param month 月
     * @param day 日
     * @return 母亲节和父亲节字符串
     */
    private static String getMotherOrFatherDay(int year,int month,int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int weekDate = calendar.get(Calendar.DAY_OF_WEEK);
        weekDate = (weekDate == 1) ? 7 : weekDate - 1;
        switch (month) {
            case 5:
                if (day == 15 - weekDate) {
                    return "母亲节";
                }
                break;
            case 6:
                if (day == 22 - weekDate) {
                    return "父亲节";
                }
                break;
            default:
                break;
        }
        return null;
    }

    /**
     * 统一调用的接口 
     * @param dateString yyyy-mm-dd的日期
     * @return 节日名
     */
    public static String dateToYearMothDay(String dateString){
        String[]dates=dateString.split("-");
        DateToFestivalsUtil deUtil=new DateToFestivalsUtil();
        String name=deUtil.getLunarDate(Integer.parseInt(dates[0]),Integer.parseInt(dates[1]),Integer.parseInt(dates[2]));
        return name;
    }

}
