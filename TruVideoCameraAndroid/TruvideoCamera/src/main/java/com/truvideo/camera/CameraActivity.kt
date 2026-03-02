package com.truvideo.camera

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import com.truvideo.sdk.camera.TruvideoSdkCamera
import com.truvideo.sdk.camera.interfaces.TruvideoSdkCamera
import com.truvideo.sdk.camera.model.TruvideoSdkCameraMedia
import com.truvideo.sdk.camera.model.external.TruvideoSdkCameraConfiguration
import com.truvideo.sdk.camera.model.external.TruvideoSdkCameraEvent
import com.truvideo.sdk.camera.ui.activities.camera.TruvideoSdkCameraContract
import kotlinx.coroutines.launch

class CameraActivity : ComponentActivity() {
    var cameraScreenLauncher: ActivityResultLauncher<TruvideoSdkCameraConfiguration>? = null
    private lateinit var camera: TruvideoSdkCamera
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

      //  lifecycleScope.launch {
            cameraScreenLauncher =
                registerForActivityResult(TruvideoSdkCameraContract()) { result: List<TruvideoSdkCameraMedia> ->
                    // Handle result
                    val gson = Gson()
                    val jsonResult = gson.toJson(result)
                    DotnetCameraTruvideo.mainCallback?.onSuccess(jsonResult)
                    finish();

                }
            openCamera(this@CameraActivity)
            //getEvents(this@CameraActivity)

            /*observeCameraEvents(
                activity = this@CameraActivity,
                camera = camera
            )*/


       // }

    }


    fun openCamera(context: Context) {
        //cameraScreenLauncher?.launch(DotnetCameraTruvideo.config!!)

        if (DotnetCameraTruvideo.config == null) {
            Log.e("CameraActivity", "Camera configuration is null")
            finish()
            return
        }

        cameraScreenLauncher?.launch(DotnetCameraTruvideo.config!!)
    }

    /*fun getEvents(context: ComponentActivity) {
        TruvideoSdkCamera.events.observeForever { event: TruvideoSdkCameraEvent ->
            DotnetCameraTruvideo.listener?.onDataReceived("${event}")
        }

    }*/

    fun observeCameraEvents(
        activity: ComponentActivity,
        camera: TruvideoSdkCamera
    ) {
        activity.lifecycleScope.launch {
            camera.events.collect { event ->
                DotnetCameraTruvideo.listener
                    ?.onDataReceived(event.toString())
            }
        }
    }

}
