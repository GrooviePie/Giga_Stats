package com.example.giga_stats.fragments;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.giga_stats.R;

/**
 * Eine runde Fortschrittsleiste für das Fragment FragmentTimer, die in einem View dargestellt wird.
 */
public class FragmentTimerRoundProgressBar extends View {
    private int max = 100;
    private int progress = 0;
    private int color = getResources().getColor(R.color.timerBackground);
    private int backgroundColor = getResources().getColor(R.color.timerBackground);
    private int padding = 20;

    /**
     * Standardkonstruktor für die runde Fortschrittsleiste.
     *
     * @param context Der Kontext, in dem die Fortschrittsleiste erstellt wird.
     */
    public FragmentTimerRoundProgressBar(Context context) {
        super(context);
    }

    /**
     * Konstruktor für die runde Fortschrittsleiste mit Attributen.
     *
     * @param context Der Kontext, in dem die Fortschrittsleiste erstellt wird.
     * @param attrs   Die Attribute des XML-Elements, das die Fortschrittsleiste definiert.
     */
    public FragmentTimerRoundProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Konstruktor für die runde Fortschrittsleiste mit Attributen und Stilen.
     *
     * @param context      Der Kontext, in dem die Fortschrittsleiste erstellt wird.
     * @param attrs        Die Attribute des XML-Elements, das die Fortschrittsleiste definiert.
     * @param defStyleAttr Der Stil des XML-Elements, das die Fortschrittsleiste definiert.
     */
    public FragmentTimerRoundProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Wird aufgerufen, um die Fortschrittsleiste zu zeichnen.
     *
     * @param canvas Der Canvas, auf dem die Fortschrittsleiste gezeichnet wird.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();
        int diameter = Math.min(width, height);
        float centerX = width / 2;
        float centerY = height / 2;

        int backgroundDiameter = diameter - 2 * padding;

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(backgroundColor);
        backgroundPaint.setAntiAlias(true);
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(padding * 2);

        Paint foregroundPaint = new Paint();
        foregroundPaint.setColor(color);
        foregroundPaint.setAntiAlias(true);
        foregroundPaint.setStyle(Paint.Style.STROKE);
        foregroundPaint.setStrokeWidth(padding * 2);

        RectF backgroundRect = new RectF(centerX - backgroundDiameter / 2, centerY - backgroundDiameter / 2, centerX + backgroundDiameter / 2, centerY + backgroundDiameter / 2);

        canvas.drawOval(backgroundRect, backgroundPaint);

        float angle = 360 * progress / max;
        canvas.drawArc(backgroundRect, -90, angle, false, foregroundPaint);
    }

    /**
     * Setzt den maximalen Wert der Fortschrittsleiste.
     *
     * @param max Der maximale Wert der Fortschrittsleiste.
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Setzt den Fortschritt der Fortschrittsleiste und aktualisiert die Ansicht.
     *
     * @param progress Der Fortschritt der Fortschrittsleiste.
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    /**
     * Setzt die Farbe des Fortschrittsbalkens.
     *
     * @param color Die Farbe des Fortschrittsbalkens.
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * Setzt die Hintergrundfarbe der Fortschrittsleiste.
     *
     * @param backgroundColor Die Hintergrundfarbe der Fortschrittsleiste.
     */
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}