#include <android/log.h>

#define LOG "capture"
#define LOGE(...) __android_log_print(ANDROID_LOG_ERROR,LOG,__VA_ARGS__)
