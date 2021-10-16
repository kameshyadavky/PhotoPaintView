package com.beetlestance.photo_paint_view

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.view.TextureView

@SuppressLint("ViewConstructor")
class RenderView(
    context: Context,
    bitmap: Bitmap
) : TextureView(context) {

    init {
        isOpaque = false
    }
}