package com.rc.devkit.foundation.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

public class CircleNumberView extends View
{
    private final int SELECTED_STROKE_WIDTH = 2;
    private Paint paint;
    private Paint textPaint;
    private String numberToWrote = "";

    public CircleNumberView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(SELECTED_STROKE_WIDTH);
        paint.setStyle(Paint.Style.STROKE);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        textPaint.setTextSize(22f * context.getResources().getDisplayMetrics().density);
    }

    public void setColor(int color)
    {
        paint.setColor(color);
    }

    public void setNumber(int number)
    {
        numberToWrote = "" + number;
    }

    public void setTypeface(Typeface typeface)
    {
        textPaint.setTypeface(typeface);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
        drawCircle(canvas);
        drawNumber(canvas);
    }

    private void drawCircle(Canvas canvas)
    {
        RectF oval = new RectF(canvas.getClipBounds());
        float r = (oval.width() / 2.0f) - SELECTED_STROKE_WIDTH;
        float centerX = oval.centerX();
        float centerY = oval.centerY();
        canvas.drawCircle(centerX, centerY, r, paint);
    }

    private void drawNumber(Canvas canvas)
    {
        Rect bounds = new Rect();
        textPaint.getTextBounds(numberToWrote, 0, numberToWrote.length(), bounds);
        int x = ((canvas.getWidth() / 2) - (bounds.width() / 2));
        int y = ((canvas.getHeight() / 2) + (bounds.height() / 2));
        canvas.drawText(numberToWrote, x, y, textPaint);
    }
}
