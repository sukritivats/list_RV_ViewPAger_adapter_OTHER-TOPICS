package com.example.recyclerviewwithcustomadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewwithcustomadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding: ActivityMainBinding? = null

    private lateinit var adapter: CustomAdapter
    private lateinit var dataList: ArrayList<DataClass>
    lateinit var imageId: Array<Int>
    lateinit var aboutId: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        imageId = arrayOf(
            R.drawable.castle,
            R.drawable.castle,
            R.drawable.castle,
            R.drawable.castle,
            R.drawable.castle,
            R.drawable.castle,
            R.drawable.castle,
            R.drawable.castle,
            R.drawable.castle,
            R.drawable.castle
        )
        aboutId = arrayOf(
            "Sukriti Vats",
            "Lila Patel",
            "Kai Simmons",
            "Eva Chang",
            "Oscar Morales",
            "Amara Singh",
            "Nolan Carter",
            "Leila Khan",
            "Maxim Clarke",
            "Aria Gonzalez"
        )

        dataList = arrayListOf<DataClass>()
        adapter = CustomAdapter(this, dataList)
        binding?.recyclerView?.adapter = adapter
        binding?.recyclerView?.layoutManager = LinearLayoutManager(this)
        binding?.recyclerView?.setHasFixedSize(true)

        for (i in imageId.indices) {
            val news = DataClass(imageId[i], aboutId[i])
            dataList.add(news)
        }
        val adapter = CustomAdapter(this, dataList)
        binding?.recyclerView?.adapter = adapter

    }
}