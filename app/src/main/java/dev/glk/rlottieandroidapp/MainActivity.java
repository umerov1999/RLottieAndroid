package dev.glk.rlottieandroidapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import dev.glk.rlottieandroid.RLottie;
import dev.glk.rlottieandroid.RLottieDrawable;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.main_image);
        String json = readAsset("a_mountain.json");
        RLottieDrawable drawable = RLottie.fromJson(json);
//        drawable.setStatic(true);
//        drawable.setCurrentFrame(70);
        imageView.setImageDrawable(drawable);
    }

    private String readAsset(@NonNull String filename) {
        StringBuilder stringBuilder = new StringBuilder();

        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            inputStream = getAssets().open(filename);
            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            String str;
            while ((str = bufferedReader.readLine()) != null) {
                stringBuilder.append(str);
            }
        } catch (Exception e) {
            Log.e(TAG, null, e);
        } finally {
            Utils.close(bufferedReader);
            Utils.close(inputStreamReader);
            Utils.close(inputStream);
        }

        return stringBuilder.toString();
    }
}