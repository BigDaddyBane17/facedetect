package com.example.facedetect.facedrawing

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.example.facedetect.Utils
import com.google.mlkit.vision.face.Face

class RectangleOverlay(
    private val overlay: Overlay<*>,
    private val face: Face,
    private val rect: Rect
) : Overlay.Graphic() {
    private val boxPaint = Paint()

    init {
        boxPaint.color = Color.GREEN
        boxPaint.style = Paint.Style.STROKE
        boxPaint.strokeWidth = 4.0F
    }

    override fun draw(canvas: Canvas) {
        val rect = Utils.calculateRect(
            overlay,
            rect.height().toFloat(),
            rect.width().toFloat(),
            face.boundingBox
        )

        canvas.drawRect(rect, boxPaint)
    }
}