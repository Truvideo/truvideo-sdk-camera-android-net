package com.truvideo.camera

interface TruvideoSdkCameraMediaMode

class VideoAndPicture(
    val videoCount: Int?,
    val pictureCount: Int?,
    val videoDuration: Double?
) : TruvideoSdkCameraMediaMode

class SingleVideo(
    val videoDuration: Double?
) : TruvideoSdkCameraMediaMode

class SinglePicture : TruvideoSdkCameraMediaMode

class SingleVideoOrPicture(
    val videoDuration: Double?
) : TruvideoSdkCameraMediaMode

class Video(
    val videoCount: Int?,
    val videoDuration: Double?
) : TruvideoSdkCameraMediaMode

class Picture(
    val pictureCount: Int?
) : TruvideoSdkCameraMediaMode

class VideoAndPictureCounted(
    val mediaCount: Int,
    val videoDuration: Double?
) : TruvideoSdkCameraMediaMode