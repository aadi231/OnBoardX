package com.example.onboardx.animation

import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemAnimator : DefaultItemAnimator() {
    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        holder?.itemView?.let { view ->
            view.alpha = 0f
            view.translationY = 1000f

            view.animate()
                .alpha(1f)
                .translationY(0f)
                .setInterpolator(DecelerateInterpolator())
                .setDuration(3000)
                .start()
        }
        return super.animateAdd(holder)
    }
}
