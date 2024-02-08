#include <jni.h>

extern "C" {

JNIEXPORT jstring JNICALL
Java_com_example_myapplication_domain_local_ApiKeyLocalStore_getSecretKey(
        JNIEnv *env, jobject) {
    return env->NewStringUTF("zcYSA3CrhG6yW1dc539o8rAVgj7ecwLUaYHTSe3s");
}

JNIEXPORT jstring JNICALL
Java_com_example_myapplication_domain_local_ApiKeyLocalStore_getApiKey(
        JNIEnv *env, jobject) {
    return env->NewStringUTF("8FvB92COL3loJkRHBozGPLOVKZTG4CgXal6Dou6EjsH5lj2SXB");
}

}