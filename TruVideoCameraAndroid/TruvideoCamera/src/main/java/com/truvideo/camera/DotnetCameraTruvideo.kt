package com.truvideo.camera

import android.content.Context
import android.content.Intent
import androidx.startup.AppInitializer
import com.truvideo.sdk.camera.TruvideoSdkCameraInitializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DotnetCameraTruvideo {
    companion object {
        var mainCallback: CameraCallback? = null

        @JvmStatic
        fun showCameraIn(
            context: Context,
            lensFacing: String,
            flashMode: String,
            orientation: String,
            mode: String,
            callback: CameraCallback
        ) {
            CoroutineScope(Dispatchers.Main).launch {
                AppInitializer.getInstance(context.applicationContext)
                    .initializeComponent(TruvideoSdkCameraInitializer::class.java)
                mainCallback = callback
                val intent = Intent(context.applicationContext, CameraActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.applicationContext.startActivity(
                    intent
                        .putExtra("lensFacing", lensFacing)
                        .putExtra("flashMode", flashMode)
                        .putExtra("orientation", orientation)
                        .putExtra("mode", mode)
                )
            }
        }
    }
}