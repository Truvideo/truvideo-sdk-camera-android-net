package com.truvideo.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.startup.AppInitializer
import com.truvideo.sdk.camera.TruvideoSdkCamera
import com.truvideo.sdk.camera.TruvideoSdkCameraInitializer
import com.truvideo.sdk.camera.model.TruvideoSdkCameraConfiguration
import com.truvideo.sdk.camera.model.TruvideoSdkCameraFlashMode
import com.truvideo.sdk.camera.model.TruvideoSdkCameraLensFacing
import com.truvideo.sdk.camera.model.TruvideoSdkCameraMode
import com.truvideo.sdk.camera.model.TruvideoSdkCameraOrientation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DotnetCameraTruvideo {
    // Method to set the listener from .NET MAUI
    fun setDataListener(listener: CameraEventListener) {
        DotnetCameraTruvideo.listener = listener
    }

    companion object {
        private lateinit var mode: TruvideoSdkCameraMode
        var mainCallback: CameraCallback? = null
        var listener: CameraEventListener? = null
        var config: TruvideoSdkCameraConfiguration?= null

        @JvmStatic
        fun environment(callback: CameraCallback) {
            val environment = TruvideoSdkCamera.environment
            callback.onSuccess("" + environment)
        }

        @JvmStatic
        fun version(callback: CameraCallback) {
            val version = TruvideoSdkCamera.version
            callback.onSuccess("" + version)
        }

        @JvmStatic
        fun isAugmentedRealityInstalled(callback: CameraCallback) {
            val isAugmentedRealityInstalled = TruvideoSdkCamera.isAugmentedRealityInstalled
            callback.onSuccess("" + isAugmentedRealityInstalled)
        }

        @JvmStatic
        fun isAugmentedRealitySupported(callback: CameraCallback) {
            val isAugmentedRealitySupported = TruvideoSdkCamera.isAugmentedRealitySupported
            callback.onSuccess("" + isAugmentedRealitySupported)
        }

        @JvmStatic
        fun requestInstallAugmentedReality(context: Context, callback: CameraCallback) {
            if (context is Activity) {
                val requestInstallAugmentedReality =
                    TruvideoSdkCamera.requestInstallAugmentedReality(context)
                callback.onSuccess("" + requestInstallAugmentedReality)
            }
        }

        @JvmStatic
        fun getInformation(callback: CameraCallback) {
            val getInformation = TruvideoSdkCamera.getInformation()
            callback.onSuccess("" + getInformation)
        }

        @JvmStatic
        fun showCamera(
            context: Context,
            lensFacingValue: LensType?,
            flashModeValue: FlashMode?,
            orientationValue: OrientationMode?,
            outputPathValue: String,
            modeConfig: ModeTypeConfig,
            callback: CameraCallback
        ) {
            CoroutineScope(Dispatchers.Main).launch {
                AppInitializer.getInstance(context.applicationContext)
                    .initializeComponent(TruvideoSdkCameraInitializer::class.java)
                mainCallback = callback
            val lensType = LensFacingConverter.lensFacingType(lensFacingValue)
            val flashType = FlashModeConverter.flashModeType(flashModeValue)
            val orientationType = OrientationConverter.videoOrientationType(orientationValue)
            val modeType = ModeConverter.convertModeType(modeConfig)
            if (modeType is VideoAndPicture) {
                mode= TruvideoSdkCameraMode.videoAndImage(modeType.videoCount, modeType.pictureCount, modeType.videoDuration)
            } else if (modeType is SinglePicture) {
                mode=TruvideoSdkCameraMode.singleImage()
            } else if (modeType is SingleVideo) {
                mode=TruvideoSdkCameraMode.singleVideo(modeType.videoDuration)
            } else if (modeType is Picture) {
                mode=TruvideoSdkCameraMode.image(modeType.pictureCount)
            } else if (modeType is Video) {
                mode=TruvideoSdkCameraMode.video(modeType.videoCount,modeType.videoDuration)

            } else if (modeType is SingleVideoOrPicture) {
                mode=TruvideoSdkCameraMode.singleVideoOrImage(modeType.videoDuration)
            }

            val flashMode =
                if (flashType == TruVideoSdkCameraFlashMode.ON) {
                TruvideoSdkCameraFlashMode.ON
            } else {
                TruvideoSdkCameraFlashMode.OFF
            }

            val lensFacing =
                if (lensType == TruVideoSdkCameraLensFacing.FRONT) {
                    TruvideoSdkCameraLensFacing.FRONT
                } else {
                    TruvideoSdkCameraLensFacing.BACK
                }
            val orientation =
                if (orientationType == TruVideoSdkCameraOrientation.PORTRAIT) {
                    TruvideoSdkCameraOrientation.PORTRAIT
                } else if (orientationType == TruVideoSdkCameraOrientation.PORTRAIT_REVERSE) {
                    TruvideoSdkCameraOrientation.PORTRAIT_REVERSE
                } else if (orientationType == TruVideoSdkCameraOrientation.LANDSCAPE_LEFT) {
                    TruvideoSdkCameraOrientation.LANDSCAPE_LEFT
                } else if (orientationType == TruVideoSdkCameraOrientation.LANDSCAPE_RIGHT) {
                    TruvideoSdkCameraOrientation.LANDSCAPE_RIGHT
                } else {
                    null
                }
            val outputPath = context.filesDir.path + "/camera"
            val configuration = TruvideoSdkCameraConfiguration(
                lensFacing = lensFacing,
                flashMode = flashMode,
                orientation = orientation,
                outputPath = outputPath,
                frontResolutions = arrayListOf(),
                frontResolution = null,
                backResolutions = arrayListOf(),
                backResolution = null,
                mode = mode
            )
                config=configuration;
                val intent = Intent(context.applicationContext, CameraActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.applicationContext.startActivity(intent)

        }
    }
}}