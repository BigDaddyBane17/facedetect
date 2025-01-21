package com.example.facedetect

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class Overlay<T : Overlay.Graphic>(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {
    private val mLock = Object()
    private var mPreviewWidth: Int = 0
    private var mWidthScaleFactor = 1.0f
    private var mPreviewHeight: Int = 0
    private var mHeightScaleFactor = 1.0f
    private val mGraphics = HashSet<T>()

    abstract class Graphic {
        abstract fun draw(canvas: Canvas)

    }

    fun clear() {
        synchronized(mLock) {
            mGraphics.clear()
        }
        postInvalidate()
    }

    fun add(graphic: Graphic) {
        synchronized(mLock) {
            mGraphics.add(graphic as T)
        }
        postInvalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        synchronized(mLock) {
            if (mPreviewWidth != 0 && mPreviewHeight != 0) {
                mWidthScaleFactor = width.toFloat() / mPreviewWidth.toFloat()
                mHeightScaleFactor = height.toFloat() / mPreviewHeight.toFloat()
            }

            for (graphic in mGraphics) {
                graphic.draw(canvas)
            }
        }
    }
}