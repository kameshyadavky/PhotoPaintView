package com.beetlestance.photo_paint_view.components

import android.graphics.Bitmap
import com.beetlestance.photo_paint_view.RenderView
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Painting(sz: Size) {

    private val size: Size = sz
    private val renderState: RenderState = RenderState()
    private val projection = GLMatrix.LoadOrtho(0f, size.width, 0f, size.height, -1f, 1f)

    private var renderView: RenderView? = null

    private val dataBuffer: ByteBuffer = ByteBuffer.allocateDirect(
        size.width.toInt() * size.height.toInt() * 4
    )
    private val vertexBuffer = ByteBuffer.allocateDirect(8 * 4).apply {
        order(ByteOrder.nativeOrder())
        putFloat(0.0f)
        putFloat(0.0f)
        putFloat(size.width)
        putFloat(0.0f)
        putFloat(0.0f)
        putFloat(size.height)
        putFloat(size.width)
        putFloat(size.height)
        rewind()
    }

    private val textureBuffer = ByteBuffer.allocateDirect(8 * 4).apply {
        order(ByteOrder.nativeOrder());
        putFloat(0.0f)
        putFloat(0.0f)
        putFloat(1.0f)
        putFloat(0.0f)
        putFloat(0.0f)
        putFloat(1.0f)
        putFloat(1.0f)
        putFloat(1.0f)
        rewind()
    }

    fun setDelegate() {}

    fun setRenderView(view: RenderView) {
        renderView = view
    }

    data class PaintingData(
        val bitmap: Bitmap,
        val data: ByteBuffer
    )
}