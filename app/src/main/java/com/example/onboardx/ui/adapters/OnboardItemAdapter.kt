package com.example.onboardx.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.onboardx.R
import com.example.onboardx.data.model.EducationCardData
import com.example.onboardx.databinding.OnboardItemLayoutBinding

class OnboardItemAdapter(
    private val cardList : List<EducationCardData>
) : RecyclerView.Adapter<OnboardItemAdapter.OnboardItemViewHolder>() {

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