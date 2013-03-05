LOCAL_PATH := $(call my-dir)

#prebuilt libjpeg
include $(CLEAR_VARS)
LOCAL_MODULE := mjpeg
LOCAL_SRC_FILES := lib/libjpeg.a
include $(PREBUILT_STATIC_LIBRARY)

#prebuilt libpng
#include $(CLEAR_VARS)
#LOCAL_MODULE := mpng
#LOCAL_SRC_FILES := lib/libpng.so
#include $(PREBUILT_SHARED_LIBRARY)

#built gsnap.c
include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
				captureutil.c \
				gsnap.c \

LOCAL_C_INCLUDES += $(LOCAL_PATH)/include
		
LOCAL_MODULE := capture

LOCAL_STATIC_LIBRARIES := mjpeg

#LOCAL_SHARED_LIBRARIES := mpng 

LOCAL_LDLIBS :=-llog
include $(BUILD_SHARED_LIBRARY)

include $(call all-makefiles-under,$(LOCAL_PATH))  
		

