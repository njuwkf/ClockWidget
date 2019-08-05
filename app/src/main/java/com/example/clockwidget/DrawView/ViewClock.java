package com.example.clockwidget.DrawView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.util.Log;


/**
 * @auther 吴科烽
 * @date 2019-08-01
 * @describle TODO
 **/

public class ViewClock {
    private static final String TAG = "ViewClock";
    private static float cx,cy;           //外圆圆心
    private static float radius;               //外圆半径
    private static int padding = 5;
    private static int mHourLine;
    private static int mMinuateLine;
    private static int mSecondLine;

    public static void drawBitmap(Bitmap mbitmap, String str_time) {

        Canvas mCanvas = new Canvas(mbitmap);
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        radius = (float) (Math.min(mbitmap.getWidth()/2, mbitmap.getHeight()/2) - padding);
        Log.d(TAG,mbitmap.getHeight()+"");
        cx = mbitmap.getWidth() / 2;
        cy = mbitmap.getHeight() / 2;
        Log.d(TAG,radius+"");
        String[] split = str_time.split(":");

        //拿到当前时间
        int mHour = Integer.parseInt(split[0]);
        int mMinute = Integer.parseInt(split[1]);
        int mSecond = Integer.parseInt(split[2]);
        float mDegrees;

        //画圆
        Paint paint_circle = new Paint();
        paint_circle.setColor(Color.BLACK);
        paint_circle.setStyle(Paint.Style.STROKE);
        paint_circle.setStrokeWidth(3);
        paint_circle.setAntiAlias(true);
        mCanvas.drawCircle(cx, cy, radius, paint_circle);


        //画刻度
        Paint paint_scale = new Paint();
        paint_scale.setColor(Color.BLACK);
        paint_scale.setStyle(Paint.Style.FILL);
        paint_scale.setStrokeWidth(3);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                //绘制整点刻度
                paint_scale.setStrokeWidth(3);
                mCanvas.drawLine(cx, cy - radius, cx, cy - radius + 15, paint_scale);
            } else {
                //绘制分钟刻度
                paint_scale.setStrokeWidth(1);
                mCanvas.drawLine(cx, cy - radius, cx, cy - radius + 8, paint_scale);
            }
            //绕着(x,y)旋转6°
            mCanvas.rotate(6, cx, cy);
        }

        //绘制时针
        Paint paint_hour = new Paint();
        paint_hour.setColor(Color.RED);
        paint_hour.setStrokeWidth(3);
        paint_hour.setStrokeCap(Paint.Cap.ROUND);
        paint_hour.setAntiAlias(true);
        mHourLine = (int) (mbitmap.getWidth()/2*0.4);
        mDegrees = mHour*30+mMinute/2;
        mCanvas.save();
        mCanvas.rotate(mDegrees, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2);
        mCanvas.drawLine(mbitmap.getWidth() / 2, mbitmap.getHeight() / 2, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2 - mHourLine, paint_hour);
        mCanvas.restore();

        //绘制分针
        Paint paint_min = new Paint();
        paint_min.setColor(Color.BLUE);
        paint_min.setStrokeWidth(2);
        paint_min.setStrokeCap(Paint.Cap.ROUND);
        paint_min.setAntiAlias(true);
        mMinuateLine = (int) (mbitmap.getWidth()/2*0.6);
        mDegrees = mMinute*6+mSecond/10;
        mCanvas.save();
        mCanvas.rotate(mDegrees, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2);
        mCanvas.drawLine(mbitmap.getWidth() / 2, mbitmap.getHeight() / 2, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2 - mMinuateLine, paint_min);
        mCanvas.restore();

        //绘制秒针
        Paint paint_sec = new Paint();
        paint_sec.setColor(Color.GRAY);
        paint_sec.setStrokeWidth(1);
        paint_sec.setStrokeCap(Paint.Cap.ROUND);
        paint_sec.setAntiAlias(true);
        mSecondLine = (int) (mbitmap.getWidth()/2*0.8);
        mDegrees = mSecond*6;
        mCanvas.save();
        mCanvas.rotate(mDegrees, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2);
        mCanvas.drawLine(mbitmap.getWidth() / 2, mbitmap.getHeight() / 2, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2 - mSecondLine, paint_sec);
        mCanvas.restore();

        //绘制圆心
        paint_circle.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(cx, cy, 2, paint_circle);
    }
}
