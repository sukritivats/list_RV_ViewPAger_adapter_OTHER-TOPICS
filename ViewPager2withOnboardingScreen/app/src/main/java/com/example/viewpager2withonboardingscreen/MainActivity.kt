package com.example.viewpager2withonboardingscreen

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.viewpager2withonboardingscreen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding:ActivityMainBinding?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.viewPager2?.adapter = OnboardingPagerAdapter(this)

        binding?.viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // Implement animations or transitions based on the current position
                val currentPage = binding?.viewPager2?.getChildAt(position)
                currentPage?.let {
                    it.rotation = 0f
                    it.visibility = View.VISIBLE
                    ObjectAnimator.ofFloat(it, "rotation", 0f, 360f).apply {
                        duration = 1000
                        start()
                    }

                }
            }
        })
    }
}