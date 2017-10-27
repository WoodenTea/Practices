#include "jni_lib.h"
#include <android/log.h>
#include <string>

#define JNI_CLASS "com/ymsfd/practices/algorithm/Signature"

JNINativeMethod nativeMethod[] = {
        {"stringFromJNI", "()Ljava/lang/String;",                   (void *) stringFromJNI},
        {"encode",        "(Ljava/lang/String;)Ljava/lang/String;", (void *) encode}
};

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK)
        return JNI_ERR;

    jclass jClass = env->FindClass(JNI_CLASS);
    env->RegisterNatives(jClass, nativeMethod, sizeof(nativeMethod) / sizeof(nativeMethod[0]));
    env->DeleteLocalRef(jClass);
    return JNI_VERSION_1_6;
}

void JNI_OnUnload(JavaVM *vm, void *reserved) {
    __android_log_write(ANDROID_LOG_DEBUG, "Native", "JNI_OnUnload");
    JNIEnv *env;
    vm->GetEnv((void **) &env, JNI_VERSION_1_6);
    jclass jClass = env->FindClass(JNI_CLASS);
    env->UnregisterNatives(jClass);
    env->DeleteLocalRef(jClass);
}

jstring stringFromJNI(JNIEnv *env, jobject cls) {
    return env->NewStringUTF("String From Jni With c++");
}

jstring encode(JNIEnv *env, jobject cls, jstring src) {
    std::string str = env->GetStringUTFChars(src, false);
    __android_log_write(ANDROID_LOG_DEBUG, "Native", str.c_str());

    return env->NewStringUTF(str.c_str());
}