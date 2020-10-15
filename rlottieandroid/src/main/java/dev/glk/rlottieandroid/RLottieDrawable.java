package dev.glk.rlottieandroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class RLottieDrawable extends Drawable {

    private final Paint bitmapPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
    private final long ptr;
    private final int sourceWidth;
    private final int sourceHeight;
    private final int frameRate;
    private final int framesCount;

    private int currFrame;
    private Bitmap currBitmap;

    public RLottieDrawable(@NonNull String lottieJson) {
        ptr = RLottieNative.nativeCreateAnimation(lottieJson);
        if (ptr == 0) {
            sourceWidth = 0;
            sourceHeight = 0;
            frameRate = 0;
            framesCount = 0;
            return;
        }

        sourceWidth = RLottieNative.nativeGetWidth(ptr);
        sourceHeight = RLottieNative.nativeGetHeight(ptr);
        frameRate = RLottieNative.nativeGetFrameRate(ptr);
        framesCount = RLottieNative.nativeGetFramesCount(ptr);

        currBitmap = Bitmap.createBitmap(sourceWidth, sourceHeight, Bitmap.Config.ARGB_8888);
    }

    @Override
    public int getIntrinsicWidth() {
        return sourceWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return sourceHeight;
    }

    @Override
    protected void finalize() throws Throwable {
        if (ptr != 0) {
            try {
                RLottieNative.nativeDestroyAnimation(ptr);
            } catch (Throwable throwable) {
                // ignore
            }
        }
        super.finalize();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        RLottieNative.nativeRenderFrame(ptr, currFrame, currBitmap);
        canvas.drawBitmap(currBitmap, 0, 0, bitmapPaint);
        ++currFrame;
        if (currFrame > framesCount) {
            currFrame = 0;
        }
        invalidateSelf();
    }

    @Override
    public void setAlpha(int alpha) {
        bitmapPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        bitmapPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSPARENT;
    }


    public boolean isValid() {
        return ptr != 0;
    }
}
