package com.example.viewpager2withonboardingscreen

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class OnboardingPagerAdapter(private val context: Context) : RecyclerView.Adapter<OnboardingPagerAdapter.OnboardingViewHolder>() {

    private val onboardingScreens = listOf(
        R.layout.onboarding_screen_1,
        R.layout.onboarding_screen_2,
        R.layout.onboarding_screen_3
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return OnboardingViewHolder(view)
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        // Bind data or set up animations for each onboarding screen
    }

    override fun getItemCount(): Int {
        return onboardingScreens.size
    }

    override fun getItemViewType(position: Int): Int {
        return onboardingScreens[position]
    }

    inner class OnboardingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}

