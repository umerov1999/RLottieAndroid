package dev.glk.rlottieandroid;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class RLottieDrawable extends Drawable {

    protected static final boolean DEBUG_LOG = true;

    @NonNull
    protected final Paint bitmapPaint = new Paint(Paint.FILTER_BITMAP_FLAG);
    protected final AtomicInteger currentFrame = new AtomicInteger(0);
    protected final long ptr;
    protected final int frameRate;
    protected final int frameTimeDiff;
    protected final int framesCount;
    protected long lastRenderTime;

    private final int sourceWidth;
    private final int sourceHeight;
    private boolean isStatic;

    RLottieDrawable(@NonNull String lottieJson) {
        ptr = RLottie.nativeCreateAnimation(lottieJson);
        if (ptr == 0) {
            sourceWidth = 0;
            sourceHeight = 0;
            frameRate = 0;
            frameTimeDiff = 0;
            framesCount = 0;
            return;
        }

        sourceWidth = RLottie.nativeGetWidth(ptr);
        sourceHeight = RLottie.nativeGetHeight(ptr);
        frameRate = RLottie.nativeGetFrameRate(ptr);
        frameTimeDiff = 16;
        framesCount = RLottie.nativeGetFramesCount(ptr);
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

    @Override
    public void draw(@NonNull Canvas canvas) {
        renderFrame(canvas);
        if (!isStatic) {
            invalidateSelf();
        }
    }

    public boolean isValid() {
        return ptr != 0;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
        invalidateSelf();
    }

    public void setCurrentFrame(int currentFrame) {
        if (currentFrame < 0 || framesCount <= currentFrame) {
            return;
        }
        this.currentFrame.set(currentFrame);
        invalidateSelf();
    }

    protected abstract void renderFrame(@NonNull Canvas canvas);

    @Override
    protected void finalize() throws Throwable {
        if (ptr != 0) {
            try {
                RLottie.nativeDestroyAnimation(ptr);
            } catch (Throwable throwable) {
                // ignore
            }
        }
        super.finalize();
    }
}
