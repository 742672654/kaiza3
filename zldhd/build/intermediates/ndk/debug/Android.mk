LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := H264Decoder-prebuilt
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\Android.mk \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\Application.mk \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libBaiduMapSDK_v3_1_0.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libH264Decoder.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libMediaConverter.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libMP4Recorder.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libmsc.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libRTSP.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libRTSP1.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libRTSP_bak_2014.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libtcpsdk.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libvztcpsdk_dynamic.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\libYITIJI.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\objs\YITIJI\tcpclient.o \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\objs\YITIJI\tcpclient.o.d \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\objs\YITIJI\yitiji.o \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\armeabi-v7a\objs\YITIJI\yitiji.o.d \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libBaiduMapSDK_v3_1_0.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libH264Decoder.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libMediaConverter.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libMP4Recorder.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libmsc.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libRTSP.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libRTSP1.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libRTSP_bak_2014.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libtcpsdk.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libvztcpsdk_dynamic.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\prebuilt\libYITIJI.so \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\tcpclient.c \
	E:\AndroidCLASS\kaiza\zldhd\src\main\jni\yitiji.c \

LOCAL_C_INCLUDES += E:\AndroidCLASS\kaiza\zldhd\src\main\jni
LOCAL_C_INCLUDES += E:\AndroidCLASS\kaiza\zldhd\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
