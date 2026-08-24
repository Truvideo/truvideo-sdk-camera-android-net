package com.truvideo.camera

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.truvideo.sdk.camera.TruvideoSdkCamera
import com.truvideo.sdk.camera.model.TruvideoSdkArCameraConfiguration
import com.truvideo.sdk.camera.ui.activities.proposal.TruvideoSdkNewArCameraContract
import org.json.JSONArray
import org.json.JSONObject

class ArCameraActivity : AppCompatActivity() {
    lateinit var launcher : ActivityResultLauncher<TruvideoSdkArCameraConfiguration>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_camera)
//        val configuration = intent.getStringExtra("configuration")!!
        launcher = registerForActivityResult(TruvideoSdkNewArCameraContract()){
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
        //Log.e("Rahul222222",""+ DotnetCameraTruvideo.ArConfig)
        launcher.launch(DotnetCameraTruvideo.ArConfig!!)
    }
}
