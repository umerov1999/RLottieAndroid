#!/bin/bash

DIR_RLOTTIE_LIB="$(pwd)/rlottie"

function download_rlottie {
  DIR_RLOTTIE_SOURCES="$(pwd)/sources"

  if [[ ! -d $DIR_RLOTTIE_SOURCES ]]; then
    git clone https://github.com/Samsung/rlottie.git "$DIR_RLOTTIE_SOURCES"
  fi
  cd "$DIR_RLOTTIE_SOURCES" || exit
  git checkout v0.2

  mkdir "$DIR_RLOTTIE_LIB"
  cp -rf "inc/" "$DIR_RLOTTIE_LIB/inc"
  cp -rf "licenses/" "$DIR_RLOTTIE_LIB/licenses"
  cp -rf "src/" "$DIR_RLOTTIE_LIB/src"

  cd "$DIR_RLOTTIE_LIB" || exit

  # remove all meson.build files
  find . -name "meson.build" -type f -delete

  # remove redundant dirs and files
  rm "inc/rlottie_capi.h"
  rm -rf "src/binding"
  rm -rf "src/wasm"

  # remove redundant dirs from CMakeLists.txt
  echo -e "add_subdirectory(vector)\nadd_subdirectory(lottie)" > src/CMakeLists.txt

  # create CMakeLists.txt
  touch CMakeLists.txt
  echo -e "add_subdirectory(inc)\nadd_subdirectory(src)" > CMakeLists.txt

  # create config.h
  touch inc/config.h
  echo -e "#define LOTTIE_THREAD_SUPPORT\n#define LOTTIE_CACHE_SUPPORT" > inc/config.h

  rm -rf "$DIR_RLOTTIE_SOURCES"
}

if [[ ! -d $DIR_RLOTTIE_LIB ]]; then
  download_rlottie
fi




