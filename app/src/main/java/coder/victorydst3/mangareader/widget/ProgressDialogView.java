package coder.victorydst3.mangareader.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import coder.victorydst3.mangareader.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by VinhHLB on 10/4/16.
 */

public class ProgressDialogView extends View {
    private final Context mContext;
    private final int mDialogRadius;
    private int mX;

    public ProgressDialogView(Context context) {
        this(context, null);
    }

    public ProgressDialogView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressDialogView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mDialogRadius = context.getResources().getDimensionPixelOffset(R.dimen.dialog_radius);
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawDialogBackground(canvas);
        invalidate();
    }

    private void drawDialogBackground(Canvas canvas) {
        Bitmap bitmap = getBitmapFromDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_dialog));
        canvas.drawBitmap(bitmap, (getWidth() - bitmap.getWidth()) / 2, (getHeight() - bitmap.getHeight()) / 2, null);

        Path circlePath = new Path();

        RectF rectF = new RectF((getWidth() - bitmap.getWidth() - 4) / 2, (getHeight() - bitmap.getHeight() - 4) / 2, (getWidth() + bitmap.getWidth() + 4) / 2, (getHeight() + bitmap.getHeight() + 4) / 2);
        circlePath.addArc(rectF, mX, 180);

        Paint textPain = new Paint();
        textPain.setAntiAlias(true);
        textPain.setDither(true);
        textPain.setTextSize(20f);

        canvas.drawTextOnPath("Now Loading...", circlePath, 0, 0, textPain);
        mX = mX + 3;
    }

    private Bitmap getBitmapFromDrawable(Drawable drawable) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        return Bitmap.createScaledBitmap(bitmap, mDialogRadius, mDialogRadius, false);
    }
}
