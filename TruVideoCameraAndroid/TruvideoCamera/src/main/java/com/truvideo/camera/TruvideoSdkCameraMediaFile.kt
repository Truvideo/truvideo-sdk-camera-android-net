package com.truvideo.camera

data class TruvideoSdkCameraMediaFile(
    val id: String,
    val createdAt: Long,
    val filePath: String,
    val type: String,
    val lensFacing: String,
    val orientation: String,
    val resolution: Resolution,
    val duration: Int
)

data class Resolution(
    val width: Int,
    val height: Int
)