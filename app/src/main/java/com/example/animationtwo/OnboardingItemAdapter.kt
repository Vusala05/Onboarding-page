package com.example.animationtwo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.animationtwo.databinding.OnboardingItemContainerBinding

class OnboardingItemAdapter(
    private val onboardingItems: List<OnboardingItem>,
    private val viewPager: ViewPager2 // ViewPager2-ni burada qəbul edirik
) : RecyclerView.Adapter<OnboardingItemAdapter.OnboardingItemViewHolder>() {

    inner class OnboardingItemViewHolder(
        private val binding: OnboardingItemContainerBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(onboardingItem: OnboardingItem) {
            with(binding) {
                textTitle.text = onboardingItem.title
                textDescription.text = onboardingItem.description
                button.text = onboardingItem.buttonText

                // Button'a click listener əlavə edin
                button.setOnClickListener {
                    if (adapterPosition < onboardingItems.size - 1) {
                        viewPager.currentItem = adapterPosition + 1 // Növbəti səhifəyə keç
                    } else {
                        // Sonuncu səhifədə edilməsi lazım olanları burada təyin edin
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingItemViewHolder {
        val binding = OnboardingItemContainerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OnboardingItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return onboardingItems.size
    }

    override fun onBindViewHolder(holder: OnboardingItemViewHolder, position: Int) {
        holder.bind(onboardingItems[position])
    }
}
