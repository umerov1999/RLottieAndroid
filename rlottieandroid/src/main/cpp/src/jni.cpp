#include <android/bitmap.h>
#include <jni.h>
#include <utils.hpp>

#include "lottieanimation.hpp"

static jlong createAnimation(JNIEnv* env, jobject obj, jstring jsonString) {
    auto json = jni::toString(env, jsonString);
    auto animation = new LottieAnimation(json);
    return static_cast<jlong>(reinterpret_cast<intptr_t>(animation));
}

static void renderFrame(JNIEnv* env, jobject obj, jlong ptr, jint frame, jobject bitmap) {
    if (ptr == 0 || bitmap == nullptr) {
        return;
    }

    void* pixels = nullptr;
    int ret = AndroidBitmap_lockPixels(env, bitmap, &pixels);
    if (ret >= 0) {
        auto animation = reinterpret_cast<LottieAnimation*>(ptr);
        animation->render(static_cast<size_t>(frame), pixels);
        AndroidBitmap_unlockPixels(env, bitmap);
    }
}

static void destroyAnimation(JNIEnv* env, jobject obj, jlong ptr) {
    if (ptr == 0) {
        return;
    }
    auto animation = reinterpret_cast<LottieAnimation*>(ptr);
    delete animation;
}

static jint getWidth(JNIEnv* env, jobject obj, jlong ptr) {
    if (ptr == 0) {
        return 0;
    }
    auto animation = reinterpret_cast<LottieAnimation*>(ptr);
    return animation->getWidth();
}

static jint getHeight(JNIEnv* env, jobject obj, jlong ptr) {
    if (ptr == 0) {
        return 0;
    }
    auto animation = reinterpret_cast<LottieAnimation*>(ptr);
    return animation->getHeight();
}

static jint getFrameRate(JNIEnv* env, jobject obj, jlong ptr) {
    if (ptr == 0) {
        return 0;
    }
    auto animation = reinterpret_cast<LottieAnimation*>(ptr);
    return animation->getFrameRate();
}

static jint getFramesCount(JNIEnv* env, jobject obj, jlong ptr) {
    if (ptr == 0) {
        return 0;
    }
    auto animation = reinterpret_cast<LottieAnimation*>(ptr);
    return animation->getFramesCount();
}


static const JNINativeMethod methods[] = {
        { "nativeCreateAnimation", "(Ljava/lang/String;)J", reinterpret_cast<void*>(createAnimation) },
        { "nativeRenderFrame", "(JILandroid/graphics/Bitmap;)V", reinterpret_cast<void*>(renderFrame) },
        { "nativeDestroyAnimation", "(J)V", reinterpret_cast<void*>(destroyAnimation) },
        { "nativeGetWidth", "(J)I", reinterpret_cast<void*>(getWidth) },
        { "nativeGetHeight", "(J)I", reinterpret_cast<void*>(getHeight) },
        { "nativeGetFrameRate", "(J)I", reinterpret_cast<void*>(getFrameRate) },
        { "nativeGetFramesCount", "(J)I", reinterpret_cast<void*>(getFramesCount) }
};

JNIEXPORT jint JNI_OnLoad(JavaVM* vm, void* reserved) {
    JNIEnv* env;
    int result = vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6);
    if (result != JNI_OK) {
        return JNI_ERR;
    }

    jclass c = env->FindClass("dev/glk/rlottieandroid/RLottieNative");
    if (c == nullptr) {
        return JNI_ERR;
    }

    result = env->RegisterNatives(c, methods, sizeof(methods)/sizeof(JNINativeMethod));
    if (result != JNI_OK) {
        return result;
    }

    return JNI_VERSION_1_6;
}