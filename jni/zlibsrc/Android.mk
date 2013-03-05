LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS) 

LOCAL_SRC_FILES := \
			adler32.c \
			compress.c \
			crc32.c \
			deflate.c \
			gzclose.c \
			gzlib.c \
			gzread.c \
			gzwrite.c \
			infback.c \
			inffast.c \
			inflate.c \
			inftrees.c \
			trees.c \
			uncompr.c \
			zutil.c 
			
LOCAL_MODULE := zlib

include $(BUILD_STATIC_LIBRARY)
