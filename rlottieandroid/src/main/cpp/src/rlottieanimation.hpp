#ifndef __RLottieAndroid_lottie_animation_hpp__
#define __RLottieAndroid_lottie_animation_hpp__

#include <rlottie.h>

using namespace rlottie;
using namespace std;

class RLottieAnimation {
public:
    explicit RLottieAnimation(const string& json);

    bool isValid();

    size_t getWidth();

    size_t getHeight();

    size_t getFrameRate();

    size_t getFramesCount();

    void render(size_t frame, void* pixels);

private:

    unique_ptr<Animation> animation;
    size_t width = 0;
    size_t height = 0;
};

#endif // __RLottieAndroid_lottie_animation_hpp__
