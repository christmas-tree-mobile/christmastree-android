#include <QtAndroidExtras/QAndroidJniObject>

#include "androidgw.h"

#define JAVA_NATIVE_METHOD_NAME(class_name, method_name) Java_com_derevenetz_oleg_christmastree_ ## class_name ## _ ## method_name

AndroidGW::AndroidGW(QObject *parent) :
    QObject(parent)
{
}

AndroidGW &AndroidGW::GetInstance()
{
    static AndroidGW instance;

    return instance;
}

extern "C" JNIEXPORT void JNICALL JAVA_NATIVE_METHOD_NAME(TreeActivity, interstitialActiveUpdated)(JNIEnv *, jclass, jboolean active)
{
    emit AndroidGW::GetInstance().interstitialActiveUpdated(active);
}

extern "C" JNIEXPORT void JNICALL JAVA_NATIVE_METHOD_NAME(TreeActivity, bannerViewHeightUpdated)(JNIEnv *, jclass, jint height)
{
    emit AndroidGW::GetInstance().bannerViewHeightUpdated(height);
}

extern "C" JNIEXPORT void JNICALL JAVA_NATIVE_METHOD_NAME(TreeActivity, shareImageCompleted)(JNIEnv *, jclass)
{
    emit AndroidGW::GetInstance().shareImageCompleted();
}
