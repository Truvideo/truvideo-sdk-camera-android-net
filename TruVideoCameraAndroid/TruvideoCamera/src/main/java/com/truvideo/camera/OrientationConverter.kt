package com.truvideo.camera

object OrientationConverter {

    @JvmStatic
    fun videoOrientationType(orientation: OrientationMode?): TruVideoSdkCameraOrientation {
        return when (orientation) {
            OrientationMode.PORTRAIT -> TruVideoSdkCameraOrientation.PORTRAIT
            OrientationMode.LANDSCAPE_LEFT -> TruVideoSdkCameraOrientation.LANDSCAPE_LEFT
            OrientationMode.LANDSCAPE_RIGHT -> TruVideoSdkCameraOrientation.LANDSCAPE_RIGHT
            OrientationMode.PORTRAIT_REVERSE -> TruVideoSdkCameraOrientation.PORTRAIT_REVERSE
            else -> TruVideoSdkCameraOrientation.PORTRAIT // default
        }
    }
}