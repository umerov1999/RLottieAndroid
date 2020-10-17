#include "rlottieanimation.hpp"

static void filter(float& r, float& g, float& b) {
    swap(r, b);
}

RLottieAnimation::RLottieAnimation(const string& json) {
    animation = Animation::loadFromData(json, "", filter);
    animation->size(width, height);
}

bool RLottieAnimation::isValid() {
    return animation != nullptr;
}

size_t RLottieAnimation::getWidth() {
    return isValid() ? width : 0;
}

size_t RLottieAnimation::getHeight() {
    return isValid() ? height : 0;
}

size_t RLottieAnimation::getFrameRate() {
    return isValid() ? static_cast<size_t>(animation->frameRate()) : 0;
}

size_t RLottieAnimation::getFramesCount() {
    return isValid() ? static_cast<size_t>(animation->totalFrame()) : 0;
}

void RLottieAnimation::render(size_t frame, void* pixels) {
    auto surface = Surface((uint32_t *) pixels, width, height, width * 4);
    animation->renderSync(frame, surface);
}

