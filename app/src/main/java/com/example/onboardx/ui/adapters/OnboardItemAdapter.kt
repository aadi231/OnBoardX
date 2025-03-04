package com.example.onboardx.ui.adapters

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.transition.TransitionManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.onboardx.R
import com.example.onboardx.data.model.EducationCardData
import com.example.onboardx.databinding.OnboardItemLayoutBinding
import com.example.onboardx.utils.Utils.bottomToTopTranslationAnim
import com.example.onboardx.utils.Utils.fadeOut
import com.example.onboardx.utils.Utils.getFadeInAnimator
import com.example.onboardx.utils.Utils.getFadeOutAnimator
import com.example.onboardx.utils.Utils.getScaleUpYAnimator
import com.example.onboardx.utils.Utils.rotateAnimator

class OnboardItemAdapter(
    private val cardList : List<EducationCardData>
) : RecyclerView.Adapter<OnboardItemAdapter.OnboardItemViewHolder>() {

    lateinit var onItemAnimationStart: (String, String, String) -> Unit
    lateinit var onAdapterAnimationEnd: () -> Unit
    val screenHeight = Resources.getSystem().displayMetrics.heightPixels
    val screenWidth = Resources.getSystem().displayMetrics.widthPixels

    private var expandedPosition: Int = cardList.size - 1

    private var isItemClicked : Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardItemViewHolder {
        return OnboardItemViewHolder(
            OnboardItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: OnboardItemViewHolder, position: Int) {
        holder.populateViews(cardList[position])
    }



    inner class OnboardItemViewHolder(
        private val binding: OnboardItemLayoutBinding
    ) : ViewHolder(binding.root) {
        fun populateViews(educationCardData: EducationCardData) {
            if (adapterPosition == 0){
                binding.collapsedLayout.visibility = View.INVISIBLE
            }

            populateCollapsedLayout(educationCardData)
            populateExpendedLayout(educationCardData)

            applyAnimation(itemView, educationCardData)

            setupClickListener()
        }

        private fun setupClickListener() {
            binding.itemArrowCardInteraction.setOnClickListener {
                /*isItemClicked = true

                val prevExpandedPos = expandedPosition

                expandedPosition = if (expandedPosition == adapterPosition){
                    cardList.size - 1
                } else {
                    adapterPosition
                }

                notifyItemChanged(prevExpandedPos)
                notifyItemChanged(expandedPosition)*/
            }
        }

        private fun applyAnimation(
            itemView: View,
            educationCardData: EducationCardData
        ) {
            itemView.alpha = 0f
            itemView.translationY = screenHeight.toFloat()
            val delay = if (adapterPosition < cardList.size - 1) (adapterPosition + 1) * 2000L else adapterPosition * 1500L

            // Delay each item's animation
            itemView.postDelayed({
                itemView.animate()
                    .alpha(1f)
                    .translationY(0f)
                    .setInterpolator(DecelerateInterpolator())
                    .setDuration(500)
                    .setStartDelay(delay)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            onItemAnimationStart(educationCardData.backGroundColor, educationCardData.endGradient, educationCardData.startGradient)
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            if (adapterPosition < cardList.size - 1) {
                                animateFadeInCollapsed()
                                animateExpandedCardCollapse()
                            }

                            if (adapterPosition == cardList.size - 1){
                                onAdapterAnimationEnd()
                            }
                        }
                    })
                    .start()
            }, 0)
        }

        private fun animateExpandedCardCollapse() {
            binding.expendedLayout.apply {
                post {
                    pivotX = 0f
                    pivotY = 0f

                    val fadeOutAnimator = getFadeOutAnimator(1000)
                    val translationYAnim = getScaleUpYAnimator(1000)

                    val animatorSet = AnimatorSet()
                    animatorSet.playTogether(fadeOutAnimator, translationYAnim)
                    animatorSet.start()

                    animatorSet.doOnEnd {
                        visibility = View.GONE
                    }
                }
            }
        }

        private fun animateFadeInCollapsed(){
            binding.collapsedLayout.apply {
                post {
                    visibility = View.VISIBLE
                    when(adapterPosition % 2) {
                        0 -> {
                            binding.collapsedLayout.apply {
                                pivotX = screenWidth.toFloat()
                                pivotY = 0f
                                rotation = -3f
                            }
                        }

                        else -> {
                            binding.collapsedLayout.apply {
                                pivotX = 0f
                                pivotY = 0f
                                rotation = 3f
                            }
                        }
                    }

                    val fadeInAnimator = getFadeInAnimator(500)

                    val animatorSet = AnimatorSet()
                    animatorSet.playTogether(fadeInAnimator)
                    animatorSet.start()

                    animatorSet.doOnEnd {
                        animateCollapsedView()
                    }
                }
            }
        }

        private fun animateCollapsedView() {
            binding.collapsedLayout.apply {
                post {
                    visibility = View.VISIBLE
                    when(adapterPosition % 2) {
                        0 -> {
                            binding.collapsedLayout.apply {
                                pivotX = screenWidth.toFloat()
                                pivotY = 0f
                                rotation = -3f
                            }
                        }

                        else -> {
                            binding.collapsedLayout.apply {
                                pivotX = 0f
                                pivotY = 0f
                                rotation = 3f
                            }
                        }
                    }

                    val rotateAnimator =
                        when(adapterPosition % 2) {
                            0 -> {
                                rotateAnimator(1000, -3f, 0f)
                            }

                            else -> {
                                rotateAnimator(1000, 3f, 0f)
                            }
                        }

                    val animatorSet = AnimatorSet()
                    animatorSet.playTogether(rotateAnimator)
                    animatorSet.startDelay = 1000
                    animatorSet.start()
                }
            }
        }

        private fun populateExpendedLayout(educationCardData: EducationCardData) {
            //binding.expendedLayout.visibility = if (educationCardData.isExpanded == true) View.VISIBLE else View.GONE
            binding.expendedCardTextView.text = educationCardData.expandStateText

            Glide.with(itemView.context)
                .load(educationCardData.image)
                .placeholder(R.drawable.expanded_layout_dummy_image)
                .into(binding.expendedCardImageView)
        }

        private fun populateCollapsedLayout(educationCardData: EducationCardData) {
            //binding.collapsedLayout.visibility = if (educationCardData.isExpanded == false) View.VISIBLE else View.GONE
            binding.itemTextView.text = educationCardData.collapsedStateText

            Glide.with(itemView.context)
                .load(educationCardData.image)
                .placeholder(R.drawable.collapsed_image_placeholder)
                .centerCrop()
                .apply(RequestOptions.circleCropTransform())
                .into(binding.cardImageView)
        }
    }
}