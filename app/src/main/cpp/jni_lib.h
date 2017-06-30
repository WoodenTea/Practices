#ifndef PRACTICES_JNI_LIB_H
#define PRACTICES_JNI_LIB_H

#include "jni.h"

jstring stringFromJNI(JNIEnv *env, jobject);

jstring encode(JNIEnv *env, jobject, jstring src);

#endif
