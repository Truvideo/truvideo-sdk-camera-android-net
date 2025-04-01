package com.truvideo.camera

interface CameraCallback {
    fun onSuccess(result: String?)
    fun onFailure(error: String?)
}