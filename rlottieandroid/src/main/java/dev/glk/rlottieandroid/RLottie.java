package dev.glk.rlottieandroid;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public class RLottie {

    static final boolean isReady;

    static {
        boolean ready = false;
        try {
            System.loadLibrary("rlottieandroid");
            ready = true;
        } catch (Exception exception) {
            // ignore
        }
        isReady = ready;
    }

    public static RLottieDrawable fromJson(String json) {
        return new SyncRLottieDrawable(json);
    }

    static native long nativeCreateAnimation(@NonNull String json);

    static native void nativeRenderFrame(long obj, int frame, @NonNull Bitmap bitmap);

    static native void nativeDestroyAnimation(long obj);

    static native int nativeGetWidth(long obj);

    static native int nativeGetHeight(long obj);

    static native int nativeGetFrameRate(long obj);

    static native int nativeGetFramesCount(long obj);

}
