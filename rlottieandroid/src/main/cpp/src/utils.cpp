#include "utils.hpp"

std::string jni::toString(JNIEnv *env, const jstring str) {
    if (!str || !env) {
        return std::string();
    }

    std::string output;
    const char* utfChars = env->GetStringUTFChars(str, nullptr);
    if (utfChars) {
        output = utfChars;
        env->ReleaseStringUTFChars(str, utfChars);
    }
    return output;
}