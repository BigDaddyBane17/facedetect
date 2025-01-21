package com.example.facedetect

import android.graphics.Rect

import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

class CameraAnalyzer(
    private val overlay: Overlay<*>
) : AbstractCameraAnalyzer<List<Face>>() {

    override val graphicOverlay: Overlay<*>
        get() = overlay

    private val cameraOptions = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setMinFaceSize(0.15F)
        .enableTracking()
        .build()

    private val detector = FaceDetection.getClient(cameraOptions)


    override fun detectInImage(image: InputImage): Task<List<Face>> {
        return detector.process(image)
            .addOnSuccessListener { faces ->
                onSuccess(faces, overlay, Rect())
            }

    }


    override fun onSuccess(results: List<Face>, graphicOverlay: Overlay<*>, rect: Rect) {
        graphicOverlay.clear()
        results.forEach {
            val faceGraphic = RectangleOverlay(graphicOverlay, it, rect)
            graphicOverlay.add(faceGraphic)
        }
        graphicOverlay.postInvalidate()
    }

}

