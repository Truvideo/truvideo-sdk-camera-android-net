package com.truvideo.camera


object ModeConverter {

    @JvmStatic
    fun convertModeType(config: ModeTypeConfig): TruvideoSdkCameraMediaMode? {
        return when (config.rawType) {
            ModeType.VIDEO_AND_PICTURE -> VideoAndPicture(
                config.videoCount,
                config.pictureCount,
                config.videoDuration
            )

            ModeType.SINGLE_VIDEO -> SingleVideo(config.videoDuration)

            ModeType.SINGLE_PICTURE -> SinglePicture()

            ModeType.SINGLE_VIDEO_OR_PICTURE -> SingleVideoOrPicture(config.videoDuration)

            ModeType.VIDEO -> Video(config.videoCount, config.videoDuration)

            ModeType.PICTURE -> Picture(config.pictureCount)

            ModeType.VIDEO_AND_PICTURE_COUNTED -> {
                val count = config.mediaCount ?: 0
                VideoAndPictureCounted(count, config.videoDuration)
            }

            else -> null
        }
    }
}
