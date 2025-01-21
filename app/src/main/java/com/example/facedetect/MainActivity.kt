package com.example.facedetect

import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.facedetect.camera.Camera
import com.example.facedetect.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private lateinit var manageCamera: Camera

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        manageCamera = Camera(this, binding.viewCamera, binding.customOverlay, this)
        askCameraPermission()


        binding.btnTurnCamera.setOnClickListener {
            manageCamera.changeCamera()
        }
    }

    private fun askCameraPermission() {
        if (arrayOf(android.Manifest.permission.CAMERA).all {
                ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
            }) {

            manageCamera.cameraStart()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0 && ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            manageCamera.cameraStart()
        } else {
            Toast.makeText(this, "CAMERA PERMISSION DENIED", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed({
                finish()
            }, 3000)
        }
    }


}