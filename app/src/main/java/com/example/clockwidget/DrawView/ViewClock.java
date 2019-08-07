package com.example.clockwidget.DrawView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.text.format.Time;

import com.example.clockwidget.Utils.DateToFestivalsUtil;


/**
 * @auther 吴科烽
 * @date 2019-08-01
 * @describle 自定义时钟View
 **/

public class ViewClock {

    //外圆圆心
    private static float cx,cy;
    //外圆半径
    private static float radius;
    private static int mHourLine;
    private static int mMinuateLine;
    private static int mSecondLine;
    private static int mHour;
    private static int mMinute;
    private static int mSecond;
    //AM,PM判断值
    private static boolean ismorning;
    //黑夜模式判断值
    private static boolean drak_mode;
    private static float mDegrees;

    private static Paint mPaint = new Paint();

    public static void drawBitmap(Bitmap mbitmap, Canvas mCanvas, Time time, boolean mre_festival) {
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        radius = (float) (Math.min(mbitmap.getWidth()/2, mbitmap.getHeight()/2) - 5);
        cx = mbitmap.getWidth() / 2;
        cy = mbitmap.getHeight() / 2;

        //拿到当前时间
        time.setToNow();
        mHour = time.hour;
        mMinute = time.minute;
        mSecond = time.second;
        init(time);
        drawCircle(mCanvas);
        drawDegree(mCanvas);
        drawHourLine(mCanvas);
        drawMinLine(mCanvas);
        drawSecLine(mCanvas);
        drawCircleCenter(mCanvas);
        drawAMOrPM(mCanvas);
        if(mre_festival){
            drawFestival(mCanvas,time);
        }
    }

    /**
     * 绘制圆盘
     * @param mCanvas
     */
    private static void drawCircle(Canvas mCanvas){
        if(drak_mode){
            mPaint.setColor(Color.BLACK);
        }else{
            mPaint.setColor(Color.WHITE);
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mCanvas.drawCircle(cx, cy, radius, mPaint);
    }

    /**
     * 绘制刻度和数字
     * @param mCanvas
     */
    private static void drawDegree(Canvas mCanvas){
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                //绘制整点刻度
                if(drak_mode){
                    mPaint.setColor(Color.WHITE);
                }else{
                    mPaint.setColor(Color.BLACK);
                }
                mPaint.setStrokeWidth(1);
                mCanvas.drawLine(cx, cy - radius+2, cx, cy - radius + 15, mPaint);
                //绘制数字
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                mCanvas.drawText(text, cx , 30, mPaint);

            } else {
                //绘制分钟刻度
                if(drak_mode){
                    mPaint.setColor(Color.WHITE);
                }else{
                    mPaint.setColor(Color.GRAY);
                }
                mPaint.setStrokeWidth(1);
                mCanvas.drawLine(cx, cy - radius+2, cx, cy - radius + 12, mPaint);
            }
            //绕着(x,y)旋转6°
            mCanvas.rotate(6, cx, cy);
        }
    }

    /**
     * 绘制时针（下面分针，秒针类似）
     * @param mCanvas
     */
    private static void drawHourLine(Canvas mCanvas){
        if(drak_mode){
            mPaint.setColor(Color.WHITE);
        }else{
            mPaint.setColor(Color.BLACK);
        }
        mPaint.setStrokeWidth(4);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mHourLine = (int) (cx * 0.4);
        mDegrees = mHour * 30 + mMinute/2;
        mCanvas.save();
        mCanvas.rotate(mDegrees, cx, cy);
        mCanvas.drawLine(cx, cy + 15, cx, cy - mHourLine, mPaint);
        mCanvas.restore();
    }

    private static void drawMinLine(Canvas mCanvas){
        if(drak_mode){
            mPaint.setColor(Color.WHITE);
        }else{
            mPaint.setColor(Color.BLACK);
        }
        mPaint.setStrokeWidth(3);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mMinuateLine = (int) (cx * 0.6);
        mDegrees = mMinute * 6 + mSecond/10;
        mCanvas.save();
        mCanvas.rotate(mDegrees, cx, cx);
        mCanvas.drawLine(cx, cy+15, cx, cy - mMinuateLine, mPaint);
        mCanvas.restore();
    }

    private static void drawSecLine(Canvas mCanvas){
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mSecondLine = (int) (cx * 0.9);
        mDegrees = mSecond * 6;
        mCanvas.save();
        mCanvas.rotate(mDegrees, cx, cx);
        mCanvas.drawLine(cx, cy + 20, cx, cy - mSecondLine, mPaint);
        mCanvas.restore();
    }

    /**
     * 绘制圆心
     * @param mCanvas
     */
    private static void drawCircleCenter(Canvas mCanvas){
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(cx, cy, 5, mPaint);
    }

    /**
     * 绘制节日提醒
     * @param mCanvas
     * @param time 当前时间
     */
    private static void drawFestival(Canvas mCanvas,Time time) {
        if(drak_mode){
            mPaint.setColor(Color.WHITE);
        }else{
            mPaint.setColor(Color.BLACK);
        }
        mPaint.setTextAlign(Paint.Align.CENTER);
        String str_fest = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
        mCanvas.drawText(DateToFestivalsUtil.DateToYearMothDay(str_fest), cx,cy - 30, mPaint);
    }

    /**
     * 绘制PM or AM
     * @param mCanvas
     */
    private static void drawAMOrPM(Canvas mCanvas) {
        if(drak_mode){
            mPaint.setColor(Color.WHITE);
        }else{
            mPaint.setColor(Color.BLACK);
        }
        mPaint.setTextAlign(Paint.Align.CENTER);
        if (ismorning ) {
            mCanvas.drawText("AM", cx,cy + 40, mPaint);
        } else {
            mCanvas.drawText("PM", cx, cy + 40, mPaint);
        }
    }

    /**
     * 上下午和黑夜模式判断
     * @param time 当前时间
     */
    private static void init(Time time){
        time.setToNow();
        int hour = time.hour;
        if(mHour < 13 && mHour > 0){
            //上午
            ismorning = true;
        }else {
            //下午
            ismorning = false;
        }
        if(mHour >= 20 || mHour <= 6){
            //黑夜模式
            drak_mode = true;
        }else{
            drak_mode = false;
        }
    }
}
