package com.example.facedetect.camera

import android.graphics.Rect
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.facedetect.facedrawing.Overlay
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face

abstract class AbstractCameraAnalyzer<T : List<Face>> : ImageAnalysis.Analyzer {

    abstract val graphicOverlay: Overlay<*>

    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        mediaImage?.let { image ->
            detectInImage(InputImage.fromMediaImage(image, imageProxy.imageInfo.rotationDegrees))
                .addOnSuccessListener { results ->
                    onSuccess(results, graphicOverlay, image.cropRect)
                    imageProxy.close()
                }
                .addOnFailureListener {
                    imageProxy.close()
                }
        }
    }

    protected abstract fun detectInImage(image: InputImage): Task<T>


    protected abstract fun onSuccess(
        results: List<Face>,
        graphicOverlay: Overlay<*>,
        rect: Rect
    )

}