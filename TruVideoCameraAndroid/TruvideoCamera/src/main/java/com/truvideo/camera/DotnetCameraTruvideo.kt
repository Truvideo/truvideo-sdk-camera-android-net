package com.truvideo.camera

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.startup.AppInitializer
import com.truvideo.sdk.camera.TruvideoSdkCamera
import com.truvideo.sdk.camera.initializer.TruvideoSdkCameraInitializer
import com.truvideo.sdk.camera.model.TruvideoSdkArCameraConfiguration
import com.truvideo.sdk.camera.model.TruvideoSdkCameraFlashMode
import com.truvideo.sdk.camera.model.TruvideoSdkCameraImageFormat
import com.truvideo.sdk.camera.model.TruvideoSdkCameraLensFacing
import com.truvideo.sdk.camera.model.TruvideoSdkCameraOrientation
import com.truvideo.sdk.camera.model.external.TruvideoSdkCameraConfiguration
import com.truvideo.sdk.camera.model.external.TruvideoSdkCameraMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DotnetCameraTruvideo {
    fun setDataListener(listener: CameraEventListener) {
        DotnetCameraTruvideo.listener = listener
    }

    companion object {


        private lateinit var mode: TruvideoSdkCameraMode
        var mainCallback: CameraCallback? = null
        var listener: CameraEventListener? = null
        var config: TruvideoSdkCameraConfiguration?= null
        var ArConfig: TruvideoSdkArCameraConfiguration?= null

       /* @JvmStatic
        fun environment(callback: CameraCallback) {
            val environment = TruvideoSdkCamera.environment
            callback.onSuccess("" + environment)
        }

        @JvmStatic
        fun version(callback: CameraCallback) {
            val version = TruvideoSdkCamera.version
            callback.onSuccess("" + version)
        }*/

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
            imageTypeValue: ImageType?,
            frontResolutionType: ResolutionType?,
            backResolutionType: ResolutionType?,
            modeConfig: ModeTypeConfig,
            autoClose: Boolean,
            streamingUploadValue: Boolean,
            shouldMirrorFrontCameraValue: Boolean,
            noiseCancellationEnabledValue: Boolean,
            callback: CameraCallback
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                AppInitializer.getInstance(context.applicationContext)
                    .initializeComponent(TruvideoSdkCameraInitializer::class.java)
                mainCallback = callback
            val lensType = LensFacingConverter.lensFacingType(lensFacingValue)
            val flashType = FlashModeConverter.flashModeType(flashModeValue)
            val orientationType = OrientationConverter.videoOrientationType(orientationValue)
                val modeType = ModeConverter.convertModeType(modeConfig)
                val imageType = ImageTypeConverter.imageModeType(imageTypeValue)

                if (modeType is VideoAndPicture) {
                    mode = TruvideoSdkCameraMode.VideoAndImage(
                        limit = TruvideoSdkCameraMode.VideoAndImage.Limit.ByType(
                            maxVideoCount = modeType.videoCount,
                            maxImageCount = modeType.pictureCount
                        ),
                        videoDurationLimit = modeType.videoDuration
                    )
                } else if (modeType is VideoAndPictureCounted) {
                    mode = TruvideoSdkCameraMode.VideoAndImage(
                        limit = TruvideoSdkCameraMode.VideoAndImage.Limit.ByTotal(
                            maxMediaCount = modeType.mediaCount
                        ),
                        videoDurationLimit = modeType.videoDuration
                    )
                } else if (modeType is SinglePicture) {
                    mode = TruvideoSdkCameraMode.SingleImage()
                } else if (modeType is SingleVideo) {
                    mode=TruvideoSdkCameraMode.SingleVideo(autoClose, modeType.videoDuration)
                } else if (modeType is Picture) {
                    mode = TruvideoSdkCameraMode.Image(autoClose,modeType.pictureCount)
                } else if (modeType is Video) {
                    mode = TruvideoSdkCameraMode.Video(
                        autoClose,
                        modeType.videoCount,
                        modeType.videoDuration
                    )
                } else if (modeType is SingleVideoOrPicture) {
                    mode = TruvideoSdkCameraMode.SingleVideoOrImage(
                        autoClose,
                        modeType.videoDuration
                    )
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
                }
                else {
                    null
                }
                val imageTypes =
                    if (imageType == TruVideoSdkCameraImageType.JPEG) {
                        TruvideoSdkCameraImageFormat.JPEG
                    } else {
                        TruvideoSdkCameraImageFormat.PNG
                    }

                val frontResolution =
                    ResolutionConverter.getResolution(
                        frontResolutionType
                    )

                val backResolution =
                    ResolutionConverter.getResolution(
                        backResolutionType
                    )

            val outputPath = context.filesDir.path + "/camera"
            val configuration = TruvideoSdkCameraConfiguration(
                lensFacing = lensFacing,
                flashMode = flashMode,
                orientation = orientation,
                outputPath = outputPath,
                frontResolutions = frontResolution?.let { listOf(it) } ?: emptyList(),
                frontResolution = frontResolution,
                backResolutions = backResolution?.let { listOf(it) } ?: emptyList(),
                backResolution = backResolution,
                mode = mode,
                imageFormat = imageTypes,
                videoStabilizationEnabled = false,
                streamingUpload = streamingUploadValue,
                noiseCancellationEnabled = noiseCancellationEnabledValue,
                shouldMirrorFrontCamera = shouldMirrorFrontCameraValue
            )
                config=configuration;
                Log.e("Rahul",""+config)
                val intent = Intent(context.applicationContext, CameraActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.applicationContext.startActivity(intent)

        }
    }

        @JvmStatic
        fun initArScreen(
            context: Context,
            orientationValue: OrientationMode?,
            modeConfig: ModeTypeConfig,
            autoClose: Boolean,
            callback: CameraCallback
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                AppInitializer.getInstance(context.applicationContext)
                    .initializeComponent(TruvideoSdkCameraInitializer::class.java)
                mainCallback = callback
                val orientationType = OrientationConverter.videoOrientationType(orientationValue)
                val modeType = ModeConverter.convertModeType(modeConfig)
                if (modeType is VideoAndPicture) {
                    mode= TruvideoSdkCameraMode.VideoAndImage(
                        limit = TruvideoSdkCameraMode.VideoAndImage.Limit.ByType(
                            maxVideoCount = modeType.videoCount,
                            maxImageCount = modeType.pictureCount
                        ),
                        videoDurationLimit = modeType.videoDuration
                    )

                }
                else if (modeType is VideoAndPictureCounted) {
                    mode= TruvideoSdkCameraMode.VideoAndImage(
                        limit = TruvideoSdkCameraMode.VideoAndImage.Limit.ByTotal(
                            maxMediaCount = modeType.mediaCount
                        ),
                        videoDurationLimit = modeType.videoDuration
                    )
                }
                else if (modeType is SinglePicture) {
                    mode=TruvideoSdkCameraMode.SingleImage()
                } else if (modeType is SingleVideo) {
                    mode=TruvideoSdkCameraMode.SingleVideo(autoClose, modeType.videoDuration)
                } else if (modeType is Picture) {
                    mode=TruvideoSdkCameraMode.Image(autoClose,modeType.pictureCount)
                } else if (modeType is Video) {
                    mode=TruvideoSdkCameraMode.Video(autoClose,modeType.videoCount, modeType.videoDuration
                    )

                } else if (modeType is SingleVideoOrPicture) {
                    mode=TruvideoSdkCameraMode.SingleVideoOrImage(autoClose, modeType.videoDuration
                    )
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
                val configuration = TruvideoSdkArCameraConfiguration(
                    orientation = orientation,
                    outputPath = outputPath,
                    mode = mode
                )
                mainCallback = callback
                ArConfig=configuration;
                Log.e("Rahul",""+ArConfig)
                val intent = Intent(context.applicationContext, ArCameraActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.applicationContext.startActivity(intent)

            }
        }


        @JvmStatic
        fun initScannerScreen(
            context: Context,
            callback: CameraCallback
        ) {
            CoroutineScope(Dispatchers.IO).launch {
                AppInitializer.getInstance(context.applicationContext)
                    .initializeComponent(TruvideoSdkCameraInitializer::class.java)
                mainCallback = callback
                val intent = Intent(context.applicationContext, ScannerActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                context.applicationContext.startActivity(intent)

            }
        }
}}
