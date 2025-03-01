package com.example.onboardx.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.drawable.Drawable

class BackgroundWithRadialGradient(private val startGradient: Int, private val endGradient : Int) : Drawable() {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun draw(canvas: Canvas) {
        val width = bounds.width().toFloat()
        val height = bounds.height().toFloat()
        val radius = width.coerceAtMost(height) / 2

        paint.shader = LinearGradient(
            0f, 0f, 0f, height,
            intArrayOf(endGradient, startGradient),
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(0f, 0f, width, height, paint)

        paint.shader = RadialGradient(
            width / 2, height , radius,
            intArrayOf(Color.argb(127, 255, 219, 246), Color.argb(0, 255, 219, 246)), // rgba(255, 219, 246, 0.5) to rgba(255, 219, 246, 0)
            floatArrayOf(0f, 1f),
            Shader.TileMode.CLAMP
        )
        canvas.drawRect(0f, 0f, width, height, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
        invalidateSelf()
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
        invalidateSelf()
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT
}