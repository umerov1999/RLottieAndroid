#include "lottieanimation.hpp"

static void filter(float& r, float& g, float& b) {
    swap(r, b);
}

LottieAnimation::LottieAnimation(const string& json) {
    animation = Animation::loadFromData(json, "", filter);
    animation->size(width, height);
}

bool LottieAnimation::isValid() {
    return animation != nullptr;
}

size_t LottieAnimation::getWidth() {
    return isValid() ? width : 0;
}

size_t LottieAnimation::getHeight() {
    return isValid() ? height : 0;
}

size_t LottieAnimation::getFrameRate() {
    return isValid() ? static_cast<size_t>(animation->frameRate()) : 0;
}

size_t LottieAnimation::getFramesCount() {
    return isValid() ? static_cast<size_t>(animation->totalFrame()) : 0;
}

void LottieAnimation::render(size_t frame, void* pixels) {
    auto surface = Surface((uint32_t *) pixels, width, height, width * 4);
    animation->renderSync(frame, surface);
}

