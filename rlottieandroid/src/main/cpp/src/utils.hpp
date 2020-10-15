#ifndef __RLottieAndroid_utils_hpp__
#define __RLottieAndroid_utils_hpp__

#include <jni.h>
#include <string>

namespace jni {

    std::string toString(JNIEnv* env, const jstring str);
}

#endif // __RLottieAndroid_utils_hpp__