package com.truvideo.camera

interface TruvideoSdkCameraMediaMode

class VideoAndPicture(
    val videoCount: Int?,
    val pictureCount: Int?,
    val videoDuration: Int?
) : TruvideoSdkCameraMediaMode

class SingleVideo(
    val videoDuration: Int?
) : TruvideoSdkCameraMediaMode

class SinglePicture : TruvideoSdkCameraMediaMode

class SingleVideoOrPicture(
    val videoDuration: Int?
) : TruvideoSdkCameraMediaMode

class Video(
    val videoCount: Int?,
    val videoDuration: Int?
) : TruvideoSdkCameraMediaMode

class Picture(
    val pictureCount: Int?
) : TruvideoSdkCameraMediaMode

class VideoAndPictureCounted(
    val mediaCount: Int,
    val videoDuration: Int?
) : TruvideoSdkCameraMediaMode