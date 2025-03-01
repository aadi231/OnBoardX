package com.example.onboardx.utils

import android.view.View
import android.widget.TextView

object AnimationUtils {

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
}