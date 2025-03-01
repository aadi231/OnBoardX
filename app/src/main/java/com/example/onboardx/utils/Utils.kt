package com.example.onboardx.utils

import android.animation.ObjectAnimator
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.View
import android.view.ViewGroup

object Utils {

    fun View.showWithAnimation() {
        this.apply {
            visibility = View.VISIBLE
            scaleX = 0f
            scaleY = 0f
            alpha = 0f
            animate().scaleX(1f).scaleY(1f).alpha(1f).setDuration(300).start()
        }
    }

    fun View.hideWithAnimation() {
        this.animate().scaleX(0f).scaleY(0f).alpha(0f).setDuration(300).withEndAction {
            this.visibility = View.GONE
        }.start()
    }

    fun View.fadeIn(duration: Long = 300) {
        this.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(duration).start()
        }
    }

    fun View.fadeOut(duration: Long = 300) {
        this.animate().alpha(0f).setDuration(duration).withEndAction {
            this.visibility = View.GONE
        }.start()
    }

    fun View.setGradientBackground(backgroundColor: String, endGradient: String, startGradient: String) {

        val parsedBackgroundColor = Color.parseColor("#$backgroundColor") // Convert to Android Color
        val parsedStartGradient = Color.parseColor(startGradient)
        val parsedEndGradient = Color.parseColor(endGradient)

        // Create a gradient drawable
        val gradientDrawable = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM, // 180-degree vertical gradient
            intArrayOf(parsedStartGradient, parsedEndGradient) // Gradient colors
        )

        // Set background color as a solid color (optional, if needed separately)
        val solidColorDrawable = ColorDrawable(parsedBackgroundColor)

        // Combine solid color and gradient
        val layerDrawable = LayerDrawable(arrayOf(solidColorDrawable, gradientDrawable))

        this.background = layerDrawable
    }

    fun View.addPadding(padding: Int) {
        setPadding(0, padding, 0, padding)
    }

    fun View.removePadding() {
        setPadding(0, 0, 0, 0)
    }

    fun View.setMargins(margin : Int) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        params.setMargins(0, margin, 0, margin)
        layoutParams = params
    }

    fun View.removeMargins() {
        setMargins(0)
    }


    fun View.getFadeOutAnimator(duration : Long = 1000): ObjectAnimator? {
        return ObjectAnimator
            .ofFloat(
                this,
                "alpha",
                1f, 0f
            ).apply {
                setDuration(duration)
            }
    }

    fun View.getFadeInAnimator(duration : Long = 1000): ObjectAnimator? {
        return ObjectAnimator
            .ofFloat(
                this,
                "alpha",
                0f, 1f
            ).apply {
                setDuration(duration)
            }
    }

    fun View.getScaleXUpAnimator(duration : Long = 1000): ObjectAnimator? {
        return ObjectAnimator
            .ofFloat(
                this,
                "scaleX",
                1f, 0f
            ).apply {
                setDuration(duration)
            }
    }

    fun View.getScaleUpYAnimator(duration : Long = 1000): ObjectAnimator? {
        return ObjectAnimator
            .ofFloat(
                this,
                "scaleY",
                1f, 0f
            ).apply {
                setDuration(duration)
            }
    }

    fun View.bottomToTopTranslationAnim(duration : Long = 1000): ObjectAnimator? {
        return ObjectAnimator
            .ofFloat(
                this,
                "translationY",
                1f, -height.toFloat()
            ).apply {
                setDuration(duration)
            }
    }
}