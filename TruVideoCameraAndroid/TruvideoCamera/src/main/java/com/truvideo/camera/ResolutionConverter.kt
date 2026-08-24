package com.truvideo.camera



import com.truvideo.sdk.camera.model.TruvideoSdkCameraResolution

object ResolutionConverter {

    @JvmStatic
    fun getResolution(
        resolution: ResolutionType?
    ): TruvideoSdkCameraResolution? {

        return when (resolution) {

            ResolutionType.SD ->
                TruvideoSdkCameraResolution(
                    width = 720,
                    height = 480
                )

            ResolutionType.HD ->
                TruvideoSdkCameraResolution(
                    width = 1280,
                    height = 720
                )

            ResolutionType.FHD ->
                TruvideoSdkCameraResolution(
                    width = 1920,
                    height = 1080
                )

            null ->
                null
        }
    }

    @JvmStatic
    fun getResolutionLabel(
        resolution: ResolutionType?
    ): String {

        return when (resolution) {

            ResolutionType.SD ->
                "640x480"

            ResolutionType.HD ->
                "1280x720"

            ResolutionType.FHD ->
                "1920x1080"

            null ->
                ""
        }
    }

    @JvmStatic
    fun fromString(
        value: String?
    ): ResolutionType? {

        return when (
            value?.trim()?.uppercase()
        ) {

            "SD" ->
                ResolutionType.SD

            "HD" ->
                ResolutionType.HD

            "FHD" ->
                ResolutionType.FHD

            else ->
                null
        }
    }

    @JvmStatic
    fun getResolutionList(
        resolution: ResolutionType?
    ): List<TruvideoSdkCameraResolution> {

        return getResolution(
            resolution
        )?.let {

            listOf(it)

        } ?: emptyList()
    }
}