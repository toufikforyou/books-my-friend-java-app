//
// Created by toufik on 7/30/2023.
//
#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Storage_ApiStorage_getAuthApiKey(JNIEnv *env,
                                                                            jobject instance) {
    // TODO: implement getAuthApiKey()
    return env->NewStringUTF("api_key_1");
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Storage_ApiStorage_getAuthApiToken(JNIEnv *env,
                                                                              jclass clazz) {
    // TODO: implement getAuthApiToken()
    return env->NewStringUTF("api_token_1");
}