package com.truvideo.camera

object ImageTypeConverter {

    @JvmStatic
    fun imageModeType(type: ImageType?): TruVideoSdkCameraImageType {
        return when (type) {
            ImageType.JPEG -> TruVideoSdkCameraImageType.JPEG
            ImageType.PNG -> TruVideoSdkCameraImageType.PNG
            else -> TruVideoSdkCameraImageType.JPEG // default case
        }
    }
}