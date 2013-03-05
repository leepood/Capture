#include <stdio.h>
#include "com_leepood_capture_CaptureUtil.h"
#include "gsnap.h"
#include <android/log.h>
#define LOG "captureutil"

char* jstringTostring(JNIEnv* env, jstring jstr) {
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env,"java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env,"utf-8");
	jmethodID mid = (*env)->GetMethodID(env,clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env,jstr, mid, strencode);
	jsize alen = (*env)->GetArrayLength(env,barr);
	jbyte* ba = (*env)->GetByteArrayElements(env,barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1);
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env,barr, ba, 0);
	return rtn;
}

JNIEXPORT void JNICALL Java_com_leepood_capture_CaptureUtil_capture
(JNIEnv *env, jclass jthiz, jstring path)
{
	const char* filepath = jstringTostring(env,path);
	__android_log_print(ANDROID_LOG_ERROR,LOG,filepath);
	FBInfo fb;
	memset(&fb,0x00,sizeof(fb));
	int fd;
	if ((fd = fb_open(&fb, "/dev/graphics/fb0")) == 0)
	{
		__android_log_print(ANDROID_LOG_ERROR,LOG,"open framebuffer OK");
		if(strstr(filepath, ".png") != NULL)
		{
			//snap2png(filepath, 100, &fb);
		}
		else
		{
			snap2jpg(filepath, 100, &fb);
		}
		fb_close(&fb);
	}
	else
	{
		__android_log_print(ANDROID_LOG_ERROR,LOG,"open framebuffer failed,%d",fd);
	}

}
