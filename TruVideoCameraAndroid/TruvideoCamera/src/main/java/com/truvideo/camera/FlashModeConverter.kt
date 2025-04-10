package com.truvideo.camera

object FlashModeConverter {

    @JvmStatic
    fun flashModeType(mode: FlashMode?): TruVideoSdkCameraFlashMode {
        return when (mode) {
            FlashMode.ON -> TruVideoSdkCameraFlashMode.ON
            FlashMode.OFF -> TruVideoSdkCameraFlashMode.OFF
            else -> TruVideoSdkCameraFlashMode.OFF // default case
        }
    }
}