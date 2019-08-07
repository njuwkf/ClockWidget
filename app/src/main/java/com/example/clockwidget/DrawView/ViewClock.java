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
    private static int mHour;
    private static int mMinute;
    private static int mSecond;
    private static int ismorning;
    private static float mDegrees;

    private static Paint mPaint = new Paint();
    public static void drawBitmap(Bitmap mbitmap,Canvas mCanvas, String str_time) {
        mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        radius = (float) (Math.min(mbitmap.getWidth()/2, mbitmap.getHeight()/2) - padding);
        Log.d(TAG,mbitmap.getHeight()+"");
        cx = mbitmap.getWidth() / 2;
        cy = mbitmap.getHeight() / 2;
        Log.d(TAG,radius+"");
        String[] split = str_time.split(":");

        //拿到当前时间
        mHour = Integer.parseInt(split[0]);
        mMinute = Integer.parseInt(split[1]);
        mSecond = Integer.parseInt(split[2]);
        ismorning = Integer.parseInt(split[3]);
        drawCircle(mCanvas);
        drawDegree(mCanvas,mbitmap);
        drawHourLine(mCanvas,mbitmap);
        drawMinLine(mCanvas,mbitmap);
        drawSecLine(mCanvas,mbitmap);
        drawCircleCenter(mCanvas,mbitmap);
        drawAMOrPM(mCanvas,mbitmap);
    }

    //画圆
    private static void drawCircle(Canvas mCanvas){
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        mPaint.setAntiAlias(true);
        mCanvas.drawCircle(cx, cy, radius, mPaint);
    }

    //画刻度和数字
    private static void drawDegree(Canvas mCanvas,Bitmap mbitmap){
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                //绘制整点刻度
                mPaint.setColor(Color.BLACK);
                mPaint.setStrokeWidth(1);
                mCanvas.drawLine(cx, cy - radius+2, cx, cy - radius + 15, mPaint);
                //绘制数字
                String text = ((i / 5) == 0 ? 12 : (i / 5)) + "";
                mPaint.setColor(Color.BLACK);
                mCanvas.drawText(text, mbitmap.getWidth() / 2 , 30, mPaint);

            } else {
                //绘制分钟刻度
                mPaint.setColor(Color.GRAY);
                mPaint.setStrokeWidth(1);
                mCanvas.drawLine(cx, cy - radius+2, cx, cy - radius + 12, mPaint);
            }
            //绕着(x,y)旋转6°
            mCanvas.rotate(6, cx, cy);
        }
    }

    //绘制时针
    private static void drawHourLine(Canvas mCanvas,Bitmap mbitmap){

        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mHourLine = (int) (mbitmap.getWidth()/2*0.4);
        mDegrees = mHour*30+mMinute/2;
        mCanvas.save();
        mCanvas.rotate(mDegrees, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2);
        mCanvas.drawLine(mbitmap.getWidth() / 2, mbitmap.getHeight() / 2+15, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2 - mHourLine, mPaint);
        mCanvas.restore();
    }

    //绘制分针
    private static void drawMinLine(Canvas mCanvas,Bitmap mbitmap){
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(3);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mMinuateLine = (int) (mbitmap.getWidth()/2*0.6);
        mDegrees = mMinute*6+mSecond/10;
        mCanvas.save();
        mCanvas.rotate(mDegrees, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2);
        mCanvas.drawLine(mbitmap.getWidth() / 2, mbitmap.getHeight() / 2+15, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2 - mMinuateLine, mPaint);
        mCanvas.restore();
    }

    //绘制秒针
    private static void drawSecLine(Canvas mCanvas,Bitmap mbitmap){
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mSecondLine = (int) (mbitmap.getWidth()/2*0.9);
        mDegrees = mSecond*6;
        mCanvas.save();
        mCanvas.rotate(mDegrees, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2);
        mCanvas.drawLine(mbitmap.getWidth() / 2, mbitmap.getHeight() / 2+20, mbitmap.getWidth() / 2, mbitmap.getWidth() / 2 - mSecondLine, mPaint);
        mCanvas.restore();
    }

    //绘制圆心
    private static void drawCircleCenter(Canvas mCanvas,Bitmap mbitmap){
        mPaint.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(cx, cy, 5, mPaint);
    }

    //绘制PM or AM
    private static void drawAMOrPM(Canvas mCanvas,Bitmap mbitmap) {
        mPaint.setColor(Color.BLACK);
        mPaint.setTextAlign(Paint.Align.CENTER);
        if (ismorning == 0) {
            mCanvas.drawText("PM", mbitmap.getWidth() / 2, mbitmap.getHeight() / 2 + 40, mPaint);
        } else {
            mCanvas.drawText("AM", mbitmap.getWidth() / 2, mbitmap.getHeight() / 2 + 40, mPaint);
        }
    }
}
