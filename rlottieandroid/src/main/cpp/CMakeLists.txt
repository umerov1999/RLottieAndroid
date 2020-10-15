cmake_minimum_required(VERSION 3.10)
project(rlottieandroid)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_STANDARD_REQUIRED True)

add_library(rlottie SHARED)
add_subdirectory(rlottie)
list(APPEND LIBS rlottie)


include_directories(
        rlottie/inc/
        src/
)

set(SOURCES
        src/utils.cpp
        src/lottieanimation.cpp
        src/jni.cpp)

list(APPEND LIBS jnigraphics)

add_library(${PROJECT_NAME} SHARED ${SOURCES})
target_link_libraries(${PROJECT_NAME} PUBLIC ${LIBS})