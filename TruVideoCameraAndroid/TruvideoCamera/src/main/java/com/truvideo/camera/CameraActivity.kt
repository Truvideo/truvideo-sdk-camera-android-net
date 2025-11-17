package com.truvideo.camera

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.truvideo.sdk.camera.TruvideoSdkCamera
import com.truvideo.sdk.camera.model.TruvideoSdkCameraConfiguration
import com.truvideo.sdk.camera.model.TruvideoSdkCameraEvent
import com.truvideo.sdk.camera.model.TruvideoSdkCameraEventType
import com.truvideo.sdk.camera.model.TruvideoSdkCameraFlashMode
import com.truvideo.sdk.camera.model.TruvideoSdkCameraLensFacing
import com.truvideo.sdk.camera.model.TruvideoSdkCameraMedia
import com.truvideo.sdk.camera.model.TruvideoSdkCameraMode
import com.truvideo.sdk.camera.model.TruvideoSdkCameraOrientation
import com.truvideo.sdk.camera.model.TruvideoSdkCameraResolution
import com.truvideo.sdk.camera.model.events.TruvideoSdkCameraEventRecordingStarted
import com.truvideo.sdk.camera.ui.activities.camera.TruvideoSdkCameraContract
import kotlinx.coroutines.launch

class CameraActivity : ComponentActivity() {
    var cameraScreenLauncher: ActivityResultLauncher<TruvideoSdkCameraConfiguration>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        lifecycleScope.launch {
            cameraScreenLauncher =
                registerForActivityResult(TruvideoSdkCameraContract()) { result: List<TruvideoSdkCameraMedia> ->
                    // Handle result
                    val gson = Gson()
                    val jsonResult = gson.toJson(result)
                    DotnetCameraTruvideo.mainCallback?.onSuccess(jsonResult)
                    finish();

                }
            openCamera(this@CameraActivity)
            getEvents(this@CameraActivity)
        }
    }


    fun openCamera(context: Context) {
        cameraScreenLauncher?.launch(DotnetCameraTruvideo.config!!)
    }

    fun getEvents(context: ComponentActivity) {
        TruvideoSdkCamera.events.observeForever { event: TruvideoSdkCameraEvent ->
            DotnetCameraTruvideo.listener?.onDataReceived("${event}")
        }

    }

}
