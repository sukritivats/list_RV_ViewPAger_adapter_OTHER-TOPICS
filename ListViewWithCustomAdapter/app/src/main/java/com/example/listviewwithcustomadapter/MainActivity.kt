package com.example.listviewwithcustomadapter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import com.example.listviewwithcustomadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    var binding:ActivityMainBinding?=null

    private lateinit var adapter: CustomAdapter
    private lateinit var dataList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        dataList = ArrayList()
        dataList.add("Item 1")
        dataList.add("Item 2")
        dataList.add("Item 3")
        dataList.add("Item 4")
        dataList.add("Item 5")
        dataList.add("Item 6")
        dataList.add("Item 7")
        dataList.add("Item 8")
        dataList.add("Item 9")
        dataList.add("Item 10")
        dataList.add("Item 11")
        dataList.add("Item 12")
        dataList.add("Item 13")
        dataList.add("Item 14")
        dataList.add("Item 15")

        adapter = CustomAdapter(this, dataList)
        binding?.listView?.adapter = adapter
    }
}