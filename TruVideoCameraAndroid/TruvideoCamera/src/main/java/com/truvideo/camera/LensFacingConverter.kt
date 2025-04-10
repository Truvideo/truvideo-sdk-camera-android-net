package com.truvideo.camera


object LensFacingConverter {

    @JvmStatic
    fun lensFacingType(type: LensType?): TruVideoSdkCameraLensFacing {
        return when (type) {
            LensType.FRONT -> TruVideoSdkCameraLensFacing.FRONT
            LensType.BACK -> TruVideoSdkCameraLensFacing.BACK
            else -> TruVideoSdkCameraLensFacing.BACK // default fallback
        }
    }
}