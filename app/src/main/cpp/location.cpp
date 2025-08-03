//
// Created by toufik on 11/12/2023.
//

#include <jni.h>
#include <string>
#include <iostream>

static std::string mLatitude;
static std::string mLongitude;
static std::string mAddress;
static std::string mLocationCountryCode;

// TODO:: GET VALUE;
extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Information_Location_getLatitude(JNIEnv *env,
                                                                                     jclass clazz) {
    // TODO: implement getLatitude()
    return env->NewStringUTF(mLatitude.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Information_Location_getLongitude(JNIEnv *env,
                                                                                      jclass clazz) {
    // TODO: implement getLongitude()
    return env->NewStringUTF(mLongitude.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Information_Location_getAddress(JNIEnv *env,
                                                                                    jclass clazz) {
    // TODO: implement getAddress()
    return env->NewStringUTF(mAddress.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_sopnolikhi_booksmyfriend_Services_Includes_Information_Location_getLocationCountryCode(JNIEnv *env,
                                                                                    jclass clazz) {
    // TODO: implement getAddress()
    return env->NewStringUTF(mLocationCountryCode.c_str());
}


// TODO:: SETUP VALUE;

extern "C" JNIEXPORT void JNICALL
Java_com_sopnolikhi_booksmyfriend_Design_Ui_Activities_IntroActivity_setLatitude(JNIEnv *env,
                                                                                 jclass,
                                                                                 jstring latitude) {
    const char *uLatitude = env->GetStringUTFChars(latitude, nullptr);
    if (uLatitude) {
        mLatitude = uLatitude;
        env->ReleaseStringUTFChars(latitude, uLatitude);
    }
}
extern "C" JNIEXPORT void JNICALL
Java_com_sopnolikhi_booksmyfriend_Design_Ui_Activities_IntroActivity_setLongitude(JNIEnv *env,
                                                                                  jclass clazz,
                                                                                  jstring longitude) {
    // TODO: implement setLongitude()
    const char *uLongitude = env->GetStringUTFChars(longitude, nullptr);
    if (uLongitude) {
        mLongitude = uLongitude;
        env->ReleaseStringUTFChars(longitude, uLongitude);
    }
}
extern "C" JNIEXPORT void JNICALL
Java_com_sopnolikhi_booksmyfriend_Design_Ui_Activities_IntroActivity_setLocationAddress(JNIEnv *env,
                                                                                        jclass clazz,
                                                                                        jstring location_address) {
    // TODO: implement setLocationAddress()
    const char *uAddress = env->GetStringUTFChars(location_address, nullptr);
    if (uAddress) {
        mAddress = uAddress;
        env->ReleaseStringUTFChars(location_address, uAddress);
    }
}
extern "C"
JNIEXPORT void JNICALL
Java_com_sopnolikhi_booksmyfriend_Design_Ui_Activities_IntroActivity_setLocationCountryCode(JNIEnv *env,
                                                                                        jclass clazz,
                                                                                        jstring location_country) {
    // TODO: implement setLocationCountryCode()
    const char *uLocationCountryCode = env->GetStringUTFChars(location_country, nullptr);
    if (uLocationCountryCode) {
        mLocationCountryCode = uLocationCountryCode;
        env->ReleaseStringUTFChars(location_country, uLocationCountryCode);
    }
}