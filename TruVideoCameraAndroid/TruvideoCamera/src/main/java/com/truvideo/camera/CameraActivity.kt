package com.truvideo.camera

import android.content.Context
import android.os.Bundle
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
            getCameraInformation(this@CameraActivity)
            getEvents(this@CameraActivity)
        }
    }


    fun getCameraInformation(context: Context) {
        if (cameraScreenLauncher == null) return;
        val cameraInfo = TruvideoSdkCamera.getInformation()
        val lensFacingString = intent.getStringExtra("lensFacing")
        val flashModeString = intent.getStringExtra("flashMode")
        val orientationString = intent.getStringExtra("orientation")
        val modeString = intent.getStringExtra("mode")
        val lensFacing = if (lensFacingString.equals("Front", true)) {
            TruvideoSdkCameraLensFacing.FRONT
        } else {
            TruvideoSdkCameraLensFacing.BACK
        }
        val flashMode = if (flashModeString.equals("On", true)) {
            TruvideoSdkCameraFlashMode.ON
        } else {
            TruvideoSdkCameraFlashMode.OFF
        }
        val orientation: TruvideoSdkCameraOrientation? =
            if (orientationString.equals("PORTRAIT", true)) {
                TruvideoSdkCameraOrientation.PORTRAIT
            } else if (orientationString.equals("PORTRAIT_REVERSE", true)) {
                TruvideoSdkCameraOrientation.PORTRAIT_REVERSE
            } else if (orientationString.equals("LANDSCAPE_LEFT", true)) {
                TruvideoSdkCameraOrientation.LANDSCAPE_LEFT
            } else if (orientationString.equals("LANDSCAPE_RIGHT", true)) {
                TruvideoSdkCameraOrientation.LANDSCAPE_RIGHT
            } else {
                null
            }
        // You can choose where the files will be saved
        val outputPath = context.filesDir.path + "/camera"

        var frontResolutions: List<TruvideoSdkCameraResolution> = ArrayList()
        if (cameraInfo.frontCamera != null) {
            // if you don't want to decide the list of allowed resolutions, you can send all the resolutions or an empty list
            frontResolutions = cameraInfo.frontCamera!!.resolutions
        }

        // You can decide the default resolution for the front camera
        var frontResolution: TruvideoSdkCameraResolution? = null
        if (cameraInfo.frontCamera != null) {
            // Example of how tho pick the first resolution as the default one
            val resolutions = cameraInfo.frontCamera!!.resolutions
            if (resolutions.isNotEmpty()) {
                frontResolution = resolutions[0]
            }
        }

        val backResolutions: List<TruvideoSdkCameraResolution> = ArrayList()
        val backResolution: TruvideoSdkCameraResolution? = null
        // You can decide the mode of the camera
        // Options: video and picture, video, picture
        //val mode = TruvideoSdkCameraMode.videoAndPicture()
        val mode = if (modeString.equals("videoAndPicture", true)) {
            TruvideoSdkCameraMode.videoAndPicture()
        } else if (modeString.equals("picture", true)) {
            TruvideoSdkCameraMode.picture()
        } else {
            TruvideoSdkCameraMode.video()
        }

        val pauseButtonVisible = true
        val configuration = TruvideoSdkCameraConfiguration(
            lensFacing = lensFacing,
            flashMode = flashMode,
            orientation = orientation,
            outputPath = outputPath,
            frontResolutions = frontResolutions,
            frontResolution = frontResolution,
            backResolutions = backResolutions,
            backResolution = backResolution,
            mode = mode
        )
        cameraScreenLauncher?.launch(configuration)

    }

    fun getEvents(context: ComponentActivity) {
        TruvideoSdkCamera.events.observeForever { event: TruvideoSdkCameraEvent ->
            DotnetCameraTruvideo.listener?.onDataReceived("${event}")
        }

    }
}
