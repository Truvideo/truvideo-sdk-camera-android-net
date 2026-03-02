package com.truvideo.camera

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.truvideo.sdk.camera.TruvideoSdkCamera
import com.truvideo.sdk.camera.model.TruvideoSdkArCameraConfiguration
import com.truvideo.sdk.camera.ui.activities.arcamera.TruvideoSdkArCameraContract
import org.json.JSONArray
import org.json.JSONObject

class ArCameraActivity : AppCompatActivity() {
    lateinit var launcher : ActivityResultLauncher<TruvideoSdkArCameraConfiguration>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_camera)
       /* ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
//        val configuration = intent.getStringExtra("configuration")!!
        launcher = registerForActivityResult(TruvideoSdkArCameraContract()){
            //val jsonArray = Json.encodeToString(ListSerializer(TruvideoSdkCameraMedia.serializer()),it)
            val jsonArray = JSONArray()
            it.forEach { media ->
                val resolutionObj = JSONObject().apply {
                    put("width", media.resolution.width)
                    put("height", media.resolution.height)
                }
                val obj = JSONObject().apply {
                    put("id", media.id)
                    put("createdAt", (media.createdAt/1000))
                    put("filePath", media.filePath)
                    put("type", media.type.name)          // enum as string
                    put("lensFacing", media.lensFacing.name)
                    put("orientation",media.orientation.name)
                    put("resolution", resolutionObj)
                    put("duration", (media.duration/1000))
                }
                jsonArray.put(obj)
            }
            DotnetCameraTruvideo.mainCallback?.onSuccess(jsonArray.toString())
            finish()
        }
        try {
            if(TruvideoSdkCamera.isAugmentedRealityInstalled && TruvideoSdkCamera.isAugmentedRealitySupported){
                openArCamera(this@ArCameraActivity)
            }else if(TruvideoSdkCamera.isAugmentedRealitySupported){
                DotnetCameraTruvideo.mainCallback?.onSuccess("Exception :"+"Ar Not Supported in Device")
            }else{
                DotnetCameraTruvideo.mainCallback?.onSuccess("Exception: "+"Ar Core App not Installed")
            }
        }catch (e : Exception){
            DotnetCameraTruvideo.mainCallback?.onSuccess("Exception :"+e.message)
            finish()
        }

    }

    fun openArCamera(context: Context){
        /*val jsonConfiguration = JSONObject(configuration)
        var mode = TruvideoSdkCameraMode.videoAndImage()
        var outputPath = filesDir.path + "/camera"
        if(jsonConfiguration.has("outputPath")){
            val newOutputPath = jsonConfiguration.getString("outputPath")
            if(newOutputPath.isNotEmpty()){
                outputPath = newOutputPath
            }
        }
        if(jsonConfiguration.has("orientation")) {

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


            when(jsonConfiguration.getString("orientation")){
                "PORTRAIT" -> orientation = TruvideoSdkCameraOrientation.PORTRAIT
                "LANDSCAPE_LEFT" -> orientation = TruvideoSdkCameraOrientation.LANDSCAPE_LEFT
                "LANDSCAPE_RIGHT" -> orientation = TruvideoSdkCameraOrientation.LANDSCAPE_RIGHT
                "PORTRAIT_REVERSE" -> orientation = TruvideoSdkCameraOrientation.PORTRAIT_REVERSE
            }
        }
        if(jsonConfiguration.has("mode")){
            val jsonMode = JSONObject(jsonConfiguration.getString("mode"))
            val videoDurationLimit : String? = if(jsonMode.getString("videoDurationLimit") != "" ) jsonMode.getString("videoDurationLimit") else null
            val mediaLimit : String? = if(jsonMode.getString("mediaLimit") != "" ) jsonMode.getString("mediaLimit") else null
            val videoLimit : String? = if(jsonMode.getString("videoLimit") != "" ) jsonMode.getString("videoLimit") else null
            val imageLimit : String? = if(jsonMode.getString("imageLimit") != "" ) jsonMode.getString("imageLimit") else null
            when(jsonMode.getString("mode")) {
                "videoAndImage" -> {
                    if(imageLimit != null || videoLimit != null){
                        mode = TruvideoSdkCameraMode.videoAndImage(
                            imageMaxCount = imageLimit?.toInt(),
                            videoMaxCount = videoLimit?.toInt(),
                            durationLimit = videoDurationLimit?.toDouble()
                        )
                    }else if(mediaLimit != null){
                        mode = TruvideoSdkCameraMode.videoAndImage(
                            maxCount = mediaLimit.toInt(),
                            durationLimit = videoDurationLimit?.toDouble()
                        )
                    }else {
                        mode = TruvideoSdkCameraMode.videoAndImage()
                    }
                }
                "video" -> {
                    mode = TruvideoSdkCameraMode.video(
                        maxCount = videoLimit?.toInt(),
                        durationLimit = videoDurationLimit?.toDouble()
                    )
                }
                "image" -> {
                    mode = TruvideoSdkCameraMode.image(
                        maxCount = imageLimit?.toInt()
                    )
                }
                "singleImage" ->{
                    mode = TruvideoSdkCameraMode.singleImage()
                }
                "singleVideo" ->{
                    mode = TruvideoSdkCameraMode.singleVideo(
                        durationLimit = videoDurationLimit?.toDouble()
                    )
                }
                "singleVideoOrImage" -> {
                    mode = TruvideoSdkCameraMode.singleVideoOrImage(
                        durationLimit = videoDurationLimit?.toDouble()
                    )
                }
            }
        }
        val configuration = TruvideoSdkArCameraConfiguration(
            orientation = orientation,
            outputPath = outputPath,
            mode = mode
        )*/

        Log.e("Rahul222222",""+ DotnetCameraTruvideo.ArConfig)
        launcher.launch(DotnetCameraTruvideo.ArConfig!!)
    }
}
