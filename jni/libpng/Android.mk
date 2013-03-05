LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_SRC_FILES := \
		png.c \
		pngwio.c \
		pngset.c \
		pngrio.c \
		pngmem.c \
		pngget.c \
		pngtest.c \
		pngread.c \
		pngwutil.c \
		pngwtran.c \
		pngwrite.c \
		pngtrans.c \
		pngrutil.c \
		pngrtran.c \
		pngpread.c \
		pngerror.c 
		
LOCAL_STATIC_LIBRARIES := zlib


LOCAL_MODULE := png

include $(BUILD_SHARED_LIBRARY)

		

