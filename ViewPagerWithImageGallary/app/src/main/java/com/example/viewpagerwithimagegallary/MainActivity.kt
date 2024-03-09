package com.example.viewpagerwithimagegallary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.viewpagerwithimagegallary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var binding:ActivityMainBinding?=null

    private val imageUrls = listOf( "https://picsum.photos/200/300",
        "https://picsum.photos/400/600",
        "https://placekitten.com/200/300",
        "https://placekitten.com/400/600")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.viewPager?.adapter = ImagePagerAdapter(this, imageUrls)
    }
}