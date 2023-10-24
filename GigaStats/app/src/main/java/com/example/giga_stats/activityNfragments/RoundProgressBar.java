package com.example.giga_stats.activityNfragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.giga_stats.R;

public class RoundProgressBar extends View {
    private int max = 100;
    private int progress = 0;
    private int color = R.color.light_blue_400; // Blau
    private int backgroundColor = 0x22000000; // Hintergrundfarbe
    private int padding = 20; // Abstand von den Rändern

    public RoundProgressBar(Context context) {
        super(context);
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height);
        float centerX = width / 2;
        float centerY = height / 2;

        // Durchmesser der Hintergrund-Oval
        int backgroundDiameter = diameter - 2 * padding;

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(padding * 2); // Dicke des Hintergrunds

        Paint foregroundPaint = new Paint();
        foregroundPaint.setColor(color);
        foregroundPaint.setAntiAlias(true);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(padding * 2); // Dicke des Fortschrittsbalkens

        RectF backgroundRect = new RectF(centerX - backgroundDiameter / 2, centerY - backgroundDiameter / 2, centerX + backgroundDiameter / 2, centerY + backgroundDiameter / 2);

        // Zeichne den Hintergrundkreis
        canvas.drawOval(backgroundRect, backgroundPaint);

        float angle = 360 * progress / max;
        canvas.drawArc(backgroundRect, -90, angle, false, foregroundPaint);
    }

    public void setMax(int max) {
        this.max = max;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
