package dev.glk.rlottieandroidapp;

import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Closeable;
import java.io.IOException;

class Utils {

    private static final String TAG = "Utils";

    public static void close(@Nullable Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            Log.e(TAG, null, e);
        }
    }
}
