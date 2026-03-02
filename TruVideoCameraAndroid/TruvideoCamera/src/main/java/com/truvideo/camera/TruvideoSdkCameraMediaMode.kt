package com.truvideo.camera

interface TruvideoSdkCameraMediaMode

class VideoAndPicture(
    val videoCount: Int?,
    val pictureCount: Int?,
    val videoDuration: Long?
) : TruvideoSdkCameraMediaMode

class SingleVideo(
    val videoDuration: Long?
) : TruvideoSdkCameraMediaMode

class SinglePicture : TruvideoSdkCameraMediaMode

class SingleVideoOrPicture(
    val videoDuration: Long?
) : TruvideoSdkCameraMediaMode

class Video(
    val videoCount: Int?,
    val videoDuration: Long?
) : TruvideoSdkCameraMediaMode

class Picture(
    val pictureCount: Int?
) : TruvideoSdkCameraMediaMode

class VideoAndPictureCounted(
    val mediaCount: Int,
    val videoDuration: Long?
) : TruvideoSdkCameraMediaMode