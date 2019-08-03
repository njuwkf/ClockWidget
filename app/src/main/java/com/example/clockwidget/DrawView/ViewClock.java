package com.example.clockwidget.DrawView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;


/**
 * @auther 吴科烽
 * @date 2019-08-01
 * @describle TODO
 **/

public class ViewClock {
    private static float cx,cy;           //外圆圆心
    private static int radius;               //外圆半径

    public static void drawBitmap(Bitmap mbitmap, String str_time) {
        Canvas mCanvas = new Canvas(mbitmap);
        radius = Math.min(mbitmap.getWidth(), mbitmap.getHeight());
        cx = mbitmap.getWidth() / 2;
        cy = mbitmap.getHeight() / 2;
        String[] split = str_time.split(":");

        //拿到当前时间
        int hour = Integer.parseInt(split[0]);
        int minute = Integer.parseInt(split[1]);
        int second = Integer.parseInt(split[2]);
        float fHour = hour + minute / 60f;   //得到精确的小时

        //画圆
        Paint paint_circle = new Paint();
        paint_circle.setColor(Color.BLACK);
        paint_circle.setStyle(Paint.Style.STROKE);
        paint_circle.setStrokeWidth(6);
        paint_circle.setAntiAlias(true);
        mCanvas.drawCircle(cx, cy, radius, paint_circle);

        //绘制数字
        Paint paint_num = new Paint();
        paint_num.setColor(Color.BLACK);
        paint_num.setTextSize(50);
        for (int i = 1; i < 13; i++) {
            float num_width = paint_num.measureText(i + "") / 2;   //测量要画的字符的宽度
            mCanvas.drawText(i + "", (float) (cx + radius * 0.9 * Math.sin(Math.toRadians(i * 30))) - num_width, (float) (cy - radius * 0.9 * Math.cos(Math.toRadians(i * 30))) + num_width, paint_num);

        }


        //绘制时针
        Paint paint_hour = new Paint();
        paint_hour.setColor(Color.GRAY);
        paint_hour.setStrokeWidth(15);
        paint_hour.setStrokeCap(Paint.Cap.ROUND);
        paint_hour.setAntiAlias(true);
        mCanvas.drawLine(cx, cy, (float) (cx + radius * 0.5 * Math.sin(Math.toRadians(fHour * 30))), (float) (cy - radius * 0.5 * Math.cos(Math.toRadians(fHour * 30))), paint_hour);
        //绘制分针

        Paint paint_min = new Paint();
        paint_hour.setColor(Color.GRAY);
        paint_min.setStrokeWidth(10);
        paint_min.setStrokeCap(Paint.Cap.ROUND);
        paint_min.setAntiAlias(true);
        mCanvas.drawLine(cx, cy, (float) (cx + radius * 0.7 * Math.sin(Math.toRadians(minute * 6))), (float) (cy - radius * 0.7 * Math.cos(Math.toRadians(minute * 6))), paint_min);

        //绘制秒针
        Paint paint_sec = new Paint();
        paint_sec.setColor(Color.GRAY);
        paint_sec.setStrokeWidth(7);
        paint_sec.setStrokeCap(Paint.Cap.ROUND);
        paint_sec.setAntiAlias(true);
        mCanvas.drawLine(cx, cy, (float) (cx + radius * 0.9 * Math.sin(Math.toRadians(second * 6))), (float) (cy - radius * 0.9 * Math.cos(Math.toRadians(second * 6))), paint_sec);

        //绘制圆心
        paint_circle.setStyle(Paint.Style.FILL);
        mCanvas.drawCircle(cx, cy, radius / 13, paint_circle);
    }
}
