package com.cowge.anim.bloodview;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tomjang on 17/3/13
 * 血条View
 */
public class BloodView extends View {
    public BloodView(Context context) {
        super(context);
    }

    private Bitmap dstBmp;
    private Bitmap srcBmp;
    private Paint mPaint;
    private float positions[] = new float[2];
    private LinearGradient shader;
    private RectF rect = new RectF();
    private ValueAnimator valueAnimator;
    float rx;

    public BloodView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PloodView);
        rx = ta.getDimension(R.styleable.PloodView_rxy, dip2px(context, 23.5f));
        ta.recycle();
    }

    public void startAnim(float precent, int duration) {
        if (valueAnimator == null) {
            valueAnimator = ValueAnimator.ofFloat(0.5f, precent);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    invalidate();
                }
            });
        }
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        drawRightBlood(canvas, width, height);
        float precent = (valueAnimator != null ? (float) valueAnimator.getAnimatedValue() : 0.5f);
        if (precent != 0) {
            if (dstBmp == null && getWidth() > 0 && getHeight() > 0) {
                dstBmp = makeDst(width, height);
            }
            if (srcBmp == null && getWidth() > 0 && getHeight() > 0) {
                srcBmp = makeSrc(width, height);
            }
            int layerID = canvas.saveLayer(0, 0, width + height / 2.5f, height, mPaint, Canvas.ALL_SAVE_FLAG);
            canvas.drawBitmap(dstBmp, 0, 0, mPaint);
            mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            canvas.drawBitmap(srcBmp, -width * precent, 0, mPaint);
            mPaint.setXfermode(null);
            canvas.restoreToCount(layerID);
        }
        super.onDraw(canvas);
    }

    private void drawRightBlood(Canvas canvas, int width, int height) {
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        int colors[] = new int[2]; // 第1个点
        colors[0] = Color.parseColor("#FF7A5B");
        positions[0] = 0;
        // 第2个点
        colors[1] = Color.parseColor("#FF5B61");
        positions[1] = 1;
        if (shader == null && width > 0) {
            shader = new LinearGradient(
                    0, height / 2,
                    width, height / 2,
                    colors,
                    positions,
                    Shader.TileMode.REPEAT);
        }
        mPaint.setShader(shader);
        rect.left = 0;
        rect.top = 0;
        rect.right = width;
        rect.bottom = height;
        canvas.drawRoundRect(rect, rx, rx, mPaint);
    }

    private Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);

        paint.setStrokeWidth(2);

        int colors[] = new int[2];
        float positions[] = new float[2];
        // 第1个点
        colors[0] = Color.parseColor("#3BD5FF");
        positions[0] = 0;

        // 第2个点
        colors[1] = Color.parseColor("#3BBDFF");
        positions[1] = 1;
        LinearGradient shader = new LinearGradient(
                0, h / 2,
                w, h / 2,
                colors,
                positions,
                Shader.TileMode.REPEAT);
        paint.setShader(shader);
        RectF rect = new RectF();
        rect.left = 0;
        rect.top = 0;
        rect.right = w;
        rect.bottom = h;
        canvas.drawRoundRect(rect, rx, rx, paint);
        return bm;
    }

    private Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 你可以绘制很多任意多边形，比如下面画六连形
        p.reset();//重置
        p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.FILL);//设置空心
        Path path1 = new Path();
        path1.moveTo(0, 0);
        path1.lineTo(w, 0);
        path1.lineTo(w - h / 2.5f, h);
        path1.lineTo(0, h);
        path1.close();//封闭
        canvas.drawPath(path1, p);
        return bm;
    }
}
