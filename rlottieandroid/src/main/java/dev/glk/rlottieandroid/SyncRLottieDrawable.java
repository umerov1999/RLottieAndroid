package dev.glk.rlottieandroid;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

public class SyncRLottieDrawable extends RLottieDrawable {

    private static final String TAG = "SyncRLottieDrawable";

    private final Bitmap bitmap;

    private long lastInvalidateTime;

    SyncRLottieDrawable(@NonNull String lottieJson) {
        super(lottieJson);
        bitmap = Bitmap.createBitmap(getIntrinsicWidth(), getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void renderFrame(@NonNull Canvas canvas) {
        final long currentTime = SystemClock.elapsedRealtime();
        final long invalidateTimeDiff = currentTime - lastInvalidateTime;
        if (DEBUG_LOG) {
            Log.d(TAG, "Invalidate diff: " + invalidateTimeDiff + "ms");
        }
        lastInvalidateTime = currentTime;

        if (invalidateTimeDiff >= (frameRate - 6)) {
            final int frame = currentFrame.get();
            long tsBegin = SystemClock.elapsedRealtime();
            RLottie.nativeRenderFrame(ptr, frame, bitmap);
            long tsFinish = SystemClock.elapsedRealtime();
            if (DEBUG_LOG) {
                Log.d(TAG, "Rendering: " + (tsFinish - tsBegin) + " ms");
            }

            lastRenderTime = SystemClock.elapsedRealtime();
            if (frame + 1 >= framesCount) {
                currentFrame.set(0);
            } else {
                currentFrame.set(frame + 1);
            }
        }

        canvas.drawBitmap(bitmap, 0, 0, bitmapPaint);
    }
}
