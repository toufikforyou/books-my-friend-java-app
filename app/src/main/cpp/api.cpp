//
// Created by toufik on 7/30/2023.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Storage_ApiStorage_getApiUrl(JNIEnv *env, jclass clazz) {
    // TODO: implement getApiUrl()
    std::string encrypted_key = "https://api.sopnolikhi.com";
    return env->NewStringUTF(encrypted_key.c_str());
}