package com.truvideo.camera

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.truvideo.sdk.camera.interfaces.TruvideoSdkCameraScannerValidation
import com.truvideo.sdk.camera.model.TruvideoSdkCameraScannerCode
import com.truvideo.sdk.camera.model.TruvideoSdkCameraScannerConfiguration
import com.truvideo.sdk.camera.model.TruvideoSdkCameraScannerValidationResult
import com.truvideo.sdk.camera.ui.activities.scanner.TruvideoSdkCameraScannerContract

class ScannerActivity : AppCompatActivity() {
    lateinit var scanner : ActivityResultLauncher<TruvideoSdkCameraScannerConfiguration>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_scanner)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        scanner = registerForActivityResult(TruvideoSdkCameraScannerContract()){

            DotnetCameraTruvideo.mainCallback?.onSuccess(it?.data ?: "")
            finish()
        }
        try{
            openScanner()
        }catch (e : Exception){
            DotnetCameraTruvideo.mainCallback?.onSuccess("Exception"+e.message)
            finish()
        }
    }
    fun openScanner(){
        val scannerConfiguration = TruvideoSdkCameraScannerConfiguration(
            validator = object : TruvideoSdkCameraScannerValidation {
                override fun validate(code: TruvideoSdkCameraScannerCode): TruvideoSdkCameraScannerValidationResult {
                    return TruvideoSdkCameraScannerValidationResult.success()
                }
            }
        )
        scanner.launch(scannerConfiguration)
    }
}