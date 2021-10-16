package com.beetlestance.photo_paint_view.components

import android.graphics.PointF
import java.nio.ByteBuffer
import java.nio.ByteOrder

class RenderState {

    var baseWeight = 0f
    var spacing = 0f
    var alpha = 0f
    var angle = 0f
    var scale = 0f

    var remainder = 0.0

    private var count: Int = 0
    private var allocatedCount: Int = defaultStateSize
    private var buffer: ByteBuffer? = null

    fun getCount() = count

    fun prepare() {
        count = 0

        if (buffer != null)
            return

        allocatedCount = defaultStateSize
        buffer = ByteBuffer.allocateDirect(allocatedCount * 5 * 4).apply {
            order(ByteOrder.nativeOrder())
            position(0)
        }
    }

    fun read() = buffer?.float

    fun setPosition(position: Int) {
        if (buffer == null || position < 0 || position >= allocatedCount) {
            return
        }
        buffer?.position(position * 5 * 4)
    }

    fun appendValuesCount(count: Int) {
        val newTotal = this.count + count
        if (newTotal > allocatedCount || buffer == null) {
            resizeBuffer()
        }

        this.count = newTotal
    }

    fun resizeBuffer() {
        if (buffer != null)
            buffer = null

        allocatedCount = (allocatedCount * 2).coerceAtLeast(defaultStateSize)

        buffer = ByteBuffer.allocateDirect(allocatedCount * 5 * 4).apply {
            order(ByteOrder.nativeOrder())
            position(0)
        }
    }

    fun addPoint(point: PointF, size: Float, angle: Float, alpha: Float, index: Int): Boolean {
        if (index != -1 && index >= allocatedCount || buffer?.position() == buffer?.limit()) {
            resizeBuffer()
            return false
        }
        if (index != -1) {
            buffer?.position(index * 5 * 4)
        }
        buffer?.putFloat(point.x)
        buffer?.putFloat(point.y)
        buffer?.putFloat(size)
        buffer?.putFloat(angle)
        buffer?.putFloat(alpha)
        return true
    }

    fun reset() {
        count = 0
        remainder = 0.toDouble()
        if (buffer != null) {
            buffer?.position(0)
        }
    }

    companion object {
        const val defaultStateSize = 256
    }
}