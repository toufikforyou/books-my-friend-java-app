//
// Created by toufik on 7/30/2023.
//

#include <jni.h>
#include <string>
#include <iostream>

// Define static variables to store the values
static std::string userToken;
static std::string userId;
static std::string fullName;
static std::string userDeviceId;

// Get User Token
extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Storage_ApiStorage_getUserTokenKey(JNIEnv *env,
                                                                                jclass) {
    return env->NewStringUTF(userToken.c_str());
}

// Get User Full name
extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Storage_ApiStorage_getFullName(JNIEnv *env,
                                                                                       jclass) {
    return env->NewStringUTF(fullName.c_str());
}

// Get User Device ID
extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Storage_ApiStorage_getUserDeviceId(JNIEnv *env,
                                                                                   jclass) {
    return env->NewStringUTF(userDeviceId.c_str());
}

// Set User Token
extern "C" JNIEXPORT void JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Sessions_LoginSession_setUserToken(JNIEnv *env, jclass,
                                                                             jstring value) {
    const char *token = env->GetStringUTFChars(value, nullptr);
    if (token) {
        userToken = token;
        env->ReleaseStringUTFChars(value, token);
    }
}

// Get User Token
extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Storage_ApiStorage_getUserId(JNIEnv *env, jclass) {
    return env->NewStringUTF(userId.c_str());
}

// Set User ID
extern "C" JNIEXPORT void JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Sessions_LoginSession_setUserId(JNIEnv *env, jclass,
                                                                          jstring uid) {
    const char *user_id = env->GetStringUTFChars(uid, nullptr);
    if (user_id) {
        userId = user_id;
        env->ReleaseStringUTFChars(uid, user_id);
    }
}

// Set User Full Name
extern "C" JNIEXPORT void JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Sessions_LoginSession_setFullName(JNIEnv *env, jclass,
                                                                             jstring name) {
    const char *userName = env->GetStringUTFChars(name, nullptr);
    if (userName) {
        fullName = userName;
        env->ReleaseStringUTFChars(name, userName);
    }
}


// Set User Unique Device id

extern "C" JNIEXPORT void JNICALL
Java_com_sopnolikhi_booksmyfriend_Design_Ui_Activities_IntroActivity_setDeviceId(JNIEnv *env, jclass,
                                                                             jstring deviceId) {
    const char *uDeviceId = env->GetStringUTFChars(deviceId, nullptr);
    if (uDeviceId) {
        userDeviceId = uDeviceId;
        env->ReleaseStringUTFChars(deviceId, uDeviceId);
    }
}


